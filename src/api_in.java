import java.util.Date;
import java.text.SimpleDateFormat;

public class api_in {
    private final String dataType = "json";
    private String baseDate;
    private String baseTime;

    public api_in() {
        setBaseToday();
    }

    private void setBaseToday() {
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat currDate = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat currTime = new SimpleDateFormat("HHmm");
        baseDate = currDate.format(today);
        baseTime = currTime.format(today);
    }

    public void getBaseToday() {
        System.out.println(baseDate+baseTime);
    }
    public static void VilageFcstInfoService() {

    }


}
