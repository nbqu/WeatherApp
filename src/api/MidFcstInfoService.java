package api;
import api_data.timework;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;

public class MidFcstInfoService {
    //1. 시간 instance
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HHmm");
    private String baseTime;
    private String baseDate;
    //2. api instance
    private String serviceKey = "7RT79QkWFz0ebm7OOeX1yfoMFfivYjjLNtzb084pv6J9WmgpwDyEzU8nD2FWt%2BcMv58gYrNUYaftZS68w23nkg%3D%3D";
    private String pageNo = "1";
    private String numOfRows = "10";
    private String dataType = "JSON";
    private String coordinate = "11B00000";
    private String time;


    //constructor
    public MidFcstInfoService() throws IOException, ParseException {
        gettingBaseTime(timework.currDate());
        this.time = baseDate + "" + baseTime;
        System.out.println(time);
        run();

    }

    //1) time conversion
    private void changingBaseTime(Calendar curr) {
        int firstHour = 6;
        int secondHour = 18;
        int currMin = curr.get(Calendar.MINUTE);
        boolean flag = false;

        //1) Minute conversion
        curr.add(Calendar.MINUTE, -currMin);
        //2) Hour conversion
        int currHour = curr.get(Calendar.HOUR_OF_DAY);

        if (currHour < firstHour)
            flag = true;

        if (flag)
            curr.add(Calendar.HOUR_OF_DAY, -(firstHour + currHour));
        else
            curr.add(Calendar.HOUR_OF_DAY, -(currHour - secondHour));
    }

    private void gettingBaseTime(Calendar curr) {
        changingBaseTime(curr);
        baseTime = timeFormat.format(curr.getTime());
        baseDate = dateFormat.format(curr.getTime());
    }

    public String getBaseTime() {
        System.out.println(baseTime);
        return baseTime;
    }

    public String getBaseDate() {
        System.out.println(baseDate);
        return baseDate;
    }



    //중기육상예보 api => return 강수확률, state
    private void run() throws IOException, ParseException {
        URL url = new URL(gettingURL().toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        String toBeParsed = sb.toString();
        System.out.println(toBeParsed);

        //Parsing by JSON
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(toBeParsed);
        // response 키를 가지고 데이터를 파싱
        JSONObject parse_response = (JSONObject) obj.get("response");
        // response 로 부터 body 찾기
        JSONObject parse_body = (JSONObject) parse_response.get("body");
        // body 로 부터 items 찾기
        JSONObject parse_items = (JSONObject) parse_body.get("items");

        // items로 부터 itemlist 를 받기
        JSONArray parse_item = (JSONArray) parse_items.get("item");
        String category;
        JSONObject weather; // parse_item은 배열형태이기 때문에 하나씩 데이터를 하나씩 가져올때 사용
        System.out.println(parse_item);

        ArrayList<Object> wf = new ArrayList<Object> ();
        ArrayList<Object> rnST = new ArrayList<Object> ();

        // TODO : parse시 안의 값들이 random하게 섞임. 뭔가 sort시킬 방법이 필요함
        // TODO : weather을 이용해서 parse시키는데 시간대로별로 다달라서 시간대별로 parse시킬수있는 방법이 필요함
        // TODO : 생각해낸 방법은 저 jsonobject를 hash에 넣어서 wf 와 rnst를 나뉘고 그이후 hash를 sort
        // TODO : 문제는 jsonOjbect 를 hash화 시키는걸 모르겠음.

        weather = (JSONObject) parse_item.get(0);

//        for (String i: weather) {
//
//        }

    }

    //return url
    private StringBuilder gettingURL () throws UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8")); /*요청자료형식(XML/JSON)Default: JSON*/
        urlBuilder.append("&" + URLEncoder.encode("regId", "UTF-8") + "=" + URLEncoder.encode(coordinate, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("tmFc","UTF-8") + "=" + URLEncoder.encode(time, "UTF-8") + "&");
        return urlBuilder;
    }



    //중기기온조회 api => return 최저, 최고 기온
    private void run2() {

    }







    //2. API run 하는 코드
    //input: 예보 구역 코드, 발표 시각
    //output: 2d array 3일,4일,5일 기준 강수확률, state, 최저기온, 최고 기온 return




}




class MidFcstData {



}



