package api;

import java.util.HashMap;

public class MidFcstData {
    private final String fcstDate;
    private final String fcstTime;
    private HashMap<String, Double> data = new HashMap<String, Double>();

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

    public HashMap<String, Double> getData() {
        return data;
    }


    public void updateData (String category, Double value) {
        data.put(category, value);
    }




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
