package api_data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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
    private String code = "";
    private String x = "60";
    private String y = "127";


    public RetrievingCoordinate() {

    }

    public String getX() {
        return x;
    }
    public String getY() {
        return y;
    }

    public JSONArray getParsedData(String step) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://www.kma.go.kr/DFSROOT/POINT/DATA/");
        urlBuilder.append(URLEncoder.encode(step, "UTF-8") + ".");
        if (code != "")
            urlBuilder.append(URLEncoder.encode(code, "UTF-8") + ".");
        urlBuilder.append(URLEncoder.encode("json.txt", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        URLConnection conn = url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String result = br.readLine();
        br.close();
        JSONParser parser = new JSONParser();
        JSONArray jArr = (JSONArray) parser.parse(result);

        return jArr;

    }

    public ArrayList<String> getLocationList(JSONArray parsedData) {
        ArrayList<String> ret = new ArrayList<>();
        for (int i = 0; i < parsedData.size(); i++) {
            JSONObject jobj = (JSONObject) parsedData.get(i);
            ret.add(jobj.get("value").toString());
        }

        return ret;
    }

    public void setCode(JSONArray parsedData, String value) {
        for (int i = 0; i < parsedData.size(); i++) {
            JSONObject jobj = (JSONObject) parsedData.get(i);
            if (jobj.get("value").equals(value)) {
                code = (String) jobj.get("code");
                return;
            }
        }
    }

    public void setXY(JSONArray parsedData, String value) {
        for (int i = 0; i < parsedData.size(); i++) {
                JSONObject jobj = (JSONObject) parsedData.get(i);
                if (jobj.get("value").equals(value)) {
                    x = (String) jobj.get("x");
                    y = (String) jobj.get("y");
                }
            }
    }

    public String getCode() {
        return code;
    }

    public void initCode() {
        code = "";
    }

}
