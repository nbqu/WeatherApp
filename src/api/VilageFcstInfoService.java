package api;
import api_data.CoordinateXY;
import api_data.timework;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VilageFcstInfoService {
    private final String dataType = "json";
    private final String numOfRows = "304"; //시간대별로 최대 14개 데이터 * 8번 발표 * 이틀
    private final int fcstInterval = 3;
    private final int start_time = 2;

    // TODO : 시간대별로 리턴되는 자료의 양이 다르다.(강수량은 6시간마다 리턴, 최저/최고기온 리턴 시간이 정해져있음, ...)
    // TODO : 최적화해서 48시간치 예보를 가져올 수 있는 방법...
    private String baseDate;
    private String baseTime; // HHmm에서 mm은 버리고 HH만 쓰는 것 같다. 0800 ~ 0859 사이의 시간은 0800으로 인식하여 응답하지만, 0900~1059의 시간으로 조회하면 응답하지 않는다.
    private String serviceKey;
    private StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst"); /*URL*/
    private CoordinateXY coordinate;


    public VilageFcstInfoService(String key) throws IOException{
        setBaseDateTime_realtime(timework.currDate());
        serviceKey = key;
        runAPI();
    }

    private void setBaseDateTime_realtime(Calendar curr) {
        SimpleDateFormat currDate = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat currTime = new SimpleDateFormat("HHmm");

        int h = (curr.get(Calendar.HOUR_OF_DAY) % fcstInterval) - start_time;

        if (curr.get(Calendar.MINUTE) <= 10) // HH:00 발표는 HH:10 이후에 제공한다.
            curr.add(Calendar.HOUR_OF_DAY, -fcstInterval);

        curr.add(Calendar.HOUR_OF_DAY, -h);

        baseDate = currDate.format(curr.getTime());
        baseTime = currTime.format(curr.getTime());
    }

    public void getbaseDateTime() {
        System.out.println(baseDate);
        System.out.println(baseTime);
    }

    private void runAPI() throws IOException {
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("XML", "UTF-8")); /*요청자료형식(XML/JSON)Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /*15년 12월 1일발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /*05시 발표*/
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("89", "UTF-8")); /*예보지점 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("90", "UTF-8")); /*예보지점의 Y 좌표값*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
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
        System.out.println(sb.toString());
    }


}
