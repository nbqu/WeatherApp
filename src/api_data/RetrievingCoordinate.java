package api_data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.StringTokenizer;


//arraylist 세개 만들어서 리턴으로 하는식으로
//시 -> 동 -> 구

public class RetrievingCoordinate {
    private final String areaTop;
    private final String areaMdl;
    private final String areaLeaf;
    private String result;
    private String code = "";
    private String x;
    private String y;
    private URL url;
    private BufferedReader br;
    private URLConnection conn;
    private JSONParser parser;
    private JSONArray jArr;
    private JSONObject jobj;
    private final ArrayList<Object> ar;
    private final ArrayList<Object> ar2;
    private final ArrayList<Object> ar3;

    public RetrievingCoordinate(String areaTop, String areaMdl, String areaLeaf) throws IOException, ParseException {
        this.areaTop = areaTop;
        this.areaMdl = areaMdl;
        this.areaLeaf = areaLeaf;
        this.ar = new ArrayList<Object> ();
        this.ar2 = new ArrayList<Object> ();
        this.ar3 = new ArrayList<Object> ();
        getCoordinate();
    }

    private void getCoordinate() throws IOException, ParseException {
        //시 검색
        url = new URL("http://www.kma.go.kr/DFSROOT/POINT/DATA/top.json.txt");
        conn = url.openConnection();
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        result = br.readLine();
        br.close();
        parser = new JSONParser();
        jArr = (JSONArray) parser.parse(result);

        for (int i = 0; i < jArr.size(); i++) {
            jobj = (JSONObject) jArr.get(i);
            ar.add(jobj.get("value"));

            if (jobj.get("value").equals(areaTop)) {
                code = (String) jobj.get("code");
            }
        }

        //구 검색
        url = new URL("http://www.kma.go.kr/DFSROOT/POINT/DATA/mdl." + code + ".json.txt");
        conn = url.openConnection();
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        result = br.readLine();
        br.close();
        parser = new JSONParser();
        jArr = (JSONArray) parser.parse(result);

        for (int i = 0; i < jArr.size(); i++) {
            jobj = (JSONObject) jArr.get(i);
            ar2.add(jobj.get("value"));
            if (jobj.get("value").equals(areaMdl)) {
                code = (String) jobj.get("code");
            }
        }

        //동 검색
        url = new URL("http://www.kma.go.kr/DFSROOT/POINT/DATA/leaf." + code + ".json.txt");
        conn = url.openConnection();
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        result = br.readLine();
        br.close();

        parser = new JSONParser();
        jArr = (JSONArray) parser.parse(result);

        if (areaMdl.equals("종로구")) {
            for (int i = 0; i < jArr.size(); i++) {
                jobj = (JSONObject) jArr.get(i);
                ar3.add(jobj.get("value"));

                String leaf1 = areaLeaf.substring(0, areaLeaf.length() - 3);
                String leaf2 = areaLeaf.substring(areaLeaf.length() - 3, areaLeaf.length() - 2);
                String leaf3 = areaLeaf.substring(areaLeaf.length() - 2);

                Pattern pattern = Pattern.compile(leaf1 + "[1-9.]{0,8}" + leaf2 + "[1-9.]{0,8}" + leaf3);
                Matcher matcher = pattern.matcher((String) jobj.get("value"));
                if (matcher.find()) {
                    x = (String) jobj.get("x");
                    y = (String) jobj.get("y");
                }
            }
        } else {
            for (int i = 0; i < jArr.size(); i++) {
                jobj = (JSONObject) jArr.get(i);
                ar3.add(jobj.get("value"));
                if (jobj.get("value").equals(areaLeaf)) {
                    x = (String) jobj.get("x");
                    y = (String) jobj.get("y");
                }
            }
        }
    }

    public String getX() {
        return x;
    }
    public String getY() {
        return y;
    }
    public ArrayList<Object> getAr() {return ar;}
    public ArrayList<Object> getAr2() {return ar2;}
    public ArrayList<Object> getAr3() {return ar3;}
}
