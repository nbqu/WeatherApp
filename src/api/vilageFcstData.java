package api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class vilageFcstData {
    private final String fcstDate;
    private final String fcstTime;
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

    public HashMap<String, Double> getData() {
        return data;
    }

    public void addDataList(String category, double value) {
        data.put(category, value);
    }





}
