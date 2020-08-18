package api_data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class timework {
    public static Calendar currDateTime() {
        SimpleDateFormat currDate = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat currTime = new SimpleDateFormat("HHmm");
        Calendar ret = Calendar.getInstance();

        Date curr = new Date(System.currentTimeMillis());
        ret.setTime(curr);

        return ret;

    }


}
