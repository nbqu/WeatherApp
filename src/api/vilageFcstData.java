package api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//설명점
//예보시간에 3,6에따라 정보가 달라짐.
//중기에서는 단위가 오전 오후
//단위는 날짜가 되야하지않음?
//3 -> ㅇㅇㄹㅇㄹㅇㄹ 4-> ㅇㄹㅇㄹㅇㄹㅇㄹ
//3 -> 오전 오후 나누면안됨?

34567 오전 오후
8910 오전 오후 X



public class vilageFcstData {
    private final String fcstDate;
    private final String fcstTime; //이게 index역활
    private final HashMap<String, Double> data = new HashMap<>();



    public vilageFcstData(String date, String time) {
        fcstDate = date;
        fcstTime = time;
    }

    public String getFcstDate() {
        return fcstDate;
    }

    public String getFcstTime() {
        return fcstTime;
    }
    //좌료 종류 key
    //값을 value
    public HashMap<String, Double> getData() {
        return data;
    }

    public void addDataList(String category, double value) {
        data.put(category, value);
    }


    //3일 오전
    //hash <강수확률, 30>
    // <최저, 10>
    // <최고, 20>


    3일 오후
            ~~
    ~~
    ~~


}
