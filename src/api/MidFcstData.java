package api;

import java.util.HashMap;

public class MidFcstData {
    private final String fcstDate;
    private final String fcstTime;
    private HashMap<String, Object> data = new HashMap<String, Object>();

    public MidFcstData (String date, String time) {
        fcstDate = date;
        fcstTime = time;
    }

    public String getFcstDate() {
        return fcstDate;
    }

    public String getFcstTime() {
        return fcstTime;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void updateData (String category, Object value) {
        data.put(category, value);
    }


    //1) rnSt = 강수 확률
    //2) wf = 날씨
    //3) taMin = 최저 기온
    //4) taMax = 최고 기온



    //좌료 구조 형태
    //arraylist index가 날짜를 의미할것
    //index 0 -> 3일뒤 am
    //index 1 -> 3일뒤 pm
    //~~~ index k -> 8일 뒤

    //hashmap을 이용해서 좌료구조 형태 구현
    //한개로 구현해도 충분
    //<강수확률, 70>
    //<최저, 15>





}
