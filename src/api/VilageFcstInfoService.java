package api;
import api_data.timework;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VilageFcstInfoService {
    private final String dataType = "json";
    private final String numOfRows = "304";
    // TODO : 시간대별로 리턴되는 자료의 양이 다르다.(강수량은 6시간마다 리턴, 최저/최고기온 리턴 시간이 정해져있음, ...)
    // TODO : 최적화해서 48시간치 예보를 가져올 수 있는 방법...
    private String baseDate;
    private String baseTime;


    public VilageFcstInfoService(String serviceKey) {
        adjustBaseDateTime(timework.currDate());
    }

    private void adjustBaseDateTime(Calendar curr) {
        SimpleDateFormat currDate = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat currTime = new SimpleDateFormat("HHmm");

        curr.add(Calendar.HOUR, -3); // 현재 시각의 날씨 출력하기 위해 3시간 전으로 되돌아간다.
        baseDate = currDate.format(curr.getTime());
        baseTime = currTime.format(curr.getTime());
    }

    public void getbaseDateTime() {
        System.out.println(baseDate);
        System.out.println(baseTime);
    }




}
