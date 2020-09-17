package api;
import api_data.timework;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private final String serviceKey = "7RT79QkWFz0ebm7OOeX1yfoMFfivYjjLNtzb084pv6J9WmgpwDyEzU8nD2FWt%2BcMv58gYrNUYaftZS68w23nkg%3D%3D";
    private final StringBuilder urlBuilder1 = new StringBuilder("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst"); /*URL*/
    private final StringBuilder urlBuilder2 = new StringBuilder("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa");
    private final String pageNo = "1";
    private final String numOfRows = "10";
    private final String dataType = "JSON";
    private String coordinate = "11B10101";
    private String time;
    private int type;



    //constructor
    public MidFcstInfoService() throws IOException, ParseException {
        gettingBaseTime(timework.currDate());
        this.time = baseDate + "" + baseTime;
        run();
        run2();

    }

    //1) time conversion
    private void changingBaseTime(Calendar curr) {
        int firstHour = 6;
        int secondHour = 18;
        int currMin = curr.get(Calendar.MINUTE);

        //1) Minute conversion
        curr.add(Calendar.MINUTE, -currMin);
        //2) Hour conversion
        int currHour = curr.get(Calendar.HOUR_OF_DAY);
        int hour = -1;

        if (currHour < firstHour) hour = 1;
        else if (currHour < secondHour) hour = 2;
        else hour = 3;

        switch (hour) {
            case 1:
                curr.add(Calendar.HOUR_OF_DAY, -(currHour + firstHour)); //0시부터 6시 사이 이전날 18시값 return
                break;
            case 2:
                curr.add(Calendar.HOUR_OF_DAY, -(currHour - firstHour)); //6시부터 18시사이
                break;
            case 3:
                curr.add(Calendar.HOUR_OF_DAY, -(currHour - secondHour)); //18시부터 24시사이
                break;
        }
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
        type = 1;
        URL url = new URL(gettingURL(type).toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(toBeParsed);
        JSONObject parse_response = (JSONObject) obj.get("response");
        JSONObject parse_body = (JSONObject) parse_response.get("body");
        JSONObject parse_items = (JSONObject) parse_body.get("items");
        JSONArray parse_item = (JSONArray) parse_items.get("item");
        JSONObject weather; // parse_item은 배열형태이기 때문에 하나씩 데이터를 하나씩 가져올때 사용

        weather = (JSONObject) parse_item.get(0);

        TreeMap<Object, Object> state = new TreeMap<>();
        TreeMap<Object, Object> prob = new TreeMap<>();

        //iterating over weather
        for (Object keyStr : weather.keySet()) {
            Object value = weather.get(keyStr);
            if (keyStr.toString().contains("wf"))
                state.put(keyStr, value);

            if (keyStr.toString().contains("rnSt"))
                prob.put(keyStr, value);

        }

//        System.out.println(state);
//        System.out.println(prob);
    }

    //중기기온조회 api => return 최저, 최고 기온
    //하한 상한은 필요없음
    private void run2() throws IOException, ParseException {
        //initialization
        ArrayList<MidFcstData> ret = new ArrayList<>(16);
        type = 2;
        URL url = new URL(gettingURL(type).toString());

        //URL work
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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

        //parsing
        String toBeParsed = sb.toString();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(toBeParsed);
        JSONObject parse_response = (JSONObject) obj.get("response");
        JSONObject parse_body = (JSONObject) parse_response.get("body");
        JSONObject parse_items = (JSONObject) parse_body.get("items");
        JSONArray parse_item = (JSONArray) parse_items.get("item");
        JSONObject weather; // parse_item은 배열형태이기 때문에 하나씩 데이터를 하나씩 가져올때 사용

        weather = (JSONObject) parse_item.get(0);

        ArrayList<Node> temp = new ArrayList<>();
        TreeMap<Node, Object> tree = new TreeMap<>(new SortByDay());


        //음.... tree에 모든값이 들어가 있지가 않아용... 해결해야해용
        for (Object keyStr: weather.keySet()) {
            Object value = weather.get(keyStr);
            String key = keyStr.toString();
            if (key.contains("High") || key.contains("Low") || key.contains("regId")) continue;

            Node node = new Node (key);
            temp.add(node);
            tree.put(node, value);

        }

        for (Map.Entry<Node, Object> entry : tree.entrySet()) {
            System.out.println(entry.getKey().getState());
            System.out.println(entry.getKey().getDay());
            System.out.println(entry.getValue());
            System.out.println("--------");
        }



//        for (Map.Entry<Object, Object> entry : tempMax.entrySet()) {
//            //현재 date +3 + 4 +%
//            String category = entry.getKey().toString();
//            Long value = (Long) entry.getValue();
//
//            if (ret.isEmpty()) {
//                ret.add(new MidFcstData())
//                ret.get(0).updateData(category,value);
//            }


    }



    //return url
    private StringBuilder gettingURL (int type) throws UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder();
        if (type == 1)
            urlBuilder.append(urlBuilder1);
        else
            urlBuilder.append(urlBuilder2);

        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8")); /*요청자료형식(XML/JSON)Default: JSON*/
        urlBuilder.append("&" + URLEncoder.encode("regId", "UTF-8") + "=" + URLEncoder.encode(coordinate, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("tmFc","UTF-8") + "=" + URLEncoder.encode(time, "UTF-8") + "&");

        return urlBuilder;
    }



    public class Node {
        private String state;
        private Integer day = -1;

        public Node (String key) {
            state = key;
            differentiate(state);
        }

        public void differentiate (String state){
            String diff = state;
            diff = diff.replaceAll("[^\\d]", " ").trim();
            day = Integer.parseInt(diff);
        }

        public String getState() { return state;}
        public Integer getDay() { return day;}

    }

    public class SortByDay implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.getDay() > o2.getDay()) return 1;
            else if (o1.getDay() < o2.getDay()) return -1;
            else return 0;
        }


    }


}


