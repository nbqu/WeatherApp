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
//static 붙이면 class 변수가 된다.

public class RetrievingCoordinate {
    //주간하고 일일 api가 있기때문에 시,동,구를 하나의 객체로 받을것임
//    public class Node {
//        private String top;
//        private String mid;
//        private String low;
//        private URL urlT;
//        private URL urlM;
//        private URL urlL;
//
//        public Node(String t, String m, String l) throws MalformedURLException {
//            this.top = t;
//            this.mid = m;
//            this.low = l;
//            this.urlT = new URL("http://www.kma.go.kr/DFSROOT/POINT/DATA/top.json.txt");
//            this.urlM = new
//        }
//        public String getTop() {
//            return top;
//        }
//        public String getMid() {
//            return mid;
//        }
//        public String getLow() {
//            return low;
//        }
//    }



    private String areaTop;
    private String areaMdl;
    private String areaLeaf;
    private String result;
//    private String code = "";
    private String state;
    private String x;
    private String y;
    private URL url;
    private BufferedReader br;
    private URLConnection conn;
    private JSONParser parser;
    private JSONArray jArr;
    private JSONObject jobj;
    private ArrayList<ArrayList<Object>> ar;

    public RetrievingCoordinate(String areaTop, String areaMdl, String areaLeaf) throws IOException, ParseException {
        this.areaTop = areaTop;
        this.areaMdl = areaMdl;
        this.areaLeaf = areaLeaf;
        this.ar = new ArrayList<ArrayList<Object>> (3);
        for (int i=0;i<3;i++) {
            ar.add(new ArrayList<Object> ());
        }
        getCoordinate();
    }

    //areatop 과 url을 객체로 받는다.
    //함수 작성
    private String getURL(String state, String code) {
        if (code.equals(""))
            return "http://www.kma.go.kr/DFSROOT/POINT/DATA/" + state + ".json.txt";
        else
            return "http://www.kma.go.kr/DFSROOT/POINT/DATA/"+state+"." + code + ".json.txt";

    }

    private void getCoordinate() throws IOException, ParseException {
        String [] address = new String [3];
        String [] diff = new String[3];
        address[0] = areaTop;
        address[1] = areaMdl;
        address[2] = areaLeaf;
        diff[0] = "top";
        diff[1] = "mdl";
        diff[2] = "leaf";
        String code = "";

        for (int i=0;i<3;i++) {
            System.out.println(code);
            state = diff[i];
            url = new URL (getURL(state,code));
            System.out.println(url);
            conn = url.openConnection();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            result = br.readLine();
            br.close();
            parser = new JSONParser();
            jArr = (JSONArray) parser.parse(result);

            for (int j = 0; j < jArr.size(); j++) {
                jobj = (JSONObject) jArr.get(i);
                ar.get(i).add(jobj.get("value"));
                System.out.println(address[i]);
                //code값이 안바뀌어서 동에 대한 url못얻는중 
                if (jobj.get("value").equals(address[i])) {
                    code = (String) jobj.get("code");
                    if (i == 2) {
                        x = (String) jobj.get("x");
                        y = (String) jobj.get("y");
                    }

                }
            }

        }


        //시 검색
//        url = new URL("http://www.kma.go.kr/DFSROOT/POINT/DATA/" + state + ".json.txt");
//        System.out.println("--ang--");
//        System.out.println(url);
//        conn = url.openConnection();
//        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        result = br.readLine();
//        br.close();
//        parser = new JSONParser();
//        jArr = (JSONArray) parser.parse(result);
//
//        for (int i = 0; i < jArr.size(); i++) {
//            jobj = (JSONObject) jArr.get(i);
//            ar.add(jobj.get("value"));
//
//            if (jobj.get("value").equals(areaTop)) {
//                code = (String) jobj.get("code");
//            }
//        }
//
//        state = "mdl";
//        //구 검색
//        url = new URL("http://www.kma.go.kr/DFSROOT/POINT/DATA/"+state+"." + code + ".json.txt");
//        System.out.println("---b---");
//        System.out.println(url);
//        conn = url.openConnection();
//        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        result = br.readLine();
//        br.close();
//        parser = new JSONParser();
//        jArr = (JSONArray) parser.parse(result);
//
//        for (int i = 0; i < jArr.size(); i++) {
//            jobj = (JSONObject) jArr.get(i);
//            ar2.add(jobj.get("value"));
//            if (jobj.get("value").equals(areaMdl)) {
//                code = (String) jobj.get("code");
//            }
//        }
//
//        //동 검색
//        url = new URL("http://www.kma.go.kr/DFSROOT/POINT/DATA/leaf." + code + ".json.txt");
//        conn = url.openConnection();
//        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        result = br.readLine();
//        br.close();
//
//        parser = new JSONParser();
//        jArr = (JSONArray) parser.parse(result);
//        //if else는 없애도됨.
//
//        for (int i = 0; i < jArr.size(); i++) {
//            jobj = (JSONObject) jArr.get(i);
//            ar3.add(jobj.get("value"));
//            if (jobj.get("value").equals(areaLeaf)) {
//                x = (String) jobj.get("x");
//                y = (String) jobj.get("y");
//            }
//        }
    }

    public String getX() {
        return x;
    }
    public String getY() {
        return y;
    }
    public ArrayList<ArrayList<Object>> getAr() {return ar;}

}
