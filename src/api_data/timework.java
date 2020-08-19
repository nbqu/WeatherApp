package api_data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class timework {
    public static Calendar currDate() {
        SimpleDateFormat currDate = new SimpleDateFormat("yyyyMMdd");
        Calendar ret = Calendar.getInstance();

        Date curr = new Date(System.currentTimeMillis());
        ret.setTime(curr);

        return ret;

    }




}
