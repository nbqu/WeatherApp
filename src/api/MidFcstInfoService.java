package api;
import api_data.timework;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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

    public MidFcstInfoService () throws IOException, ParseException {
        gettingBaseTime(timework.currDate());

    }

    private void changingBaseTime (Calendar curr) {
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
            curr.add(Calendar.HOUR_OF_DAY, -(firstHour+currHour));
        else
            curr.add(Calendar.HOUR_OF_DAY, -(currHour-secondHour));
    }

    private void gettingBaseTime (Calendar curr) {
        changingBaseTime(curr);
        baseTime = timeFormat.format(curr.getTime());
        baseDate = dateFormat.format(curr.getTime());
    }












    //2. API run 하는 코드
    //input: 예보 구역 코드, 발표 시각
    //output: 2d array 3일,4일,5일 기준 강수확률, state, 최저기온, 최고 기온 return




}



class MidFcstData {



}
