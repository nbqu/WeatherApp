package api_data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class timework {
    //static method는 굳이 timework를 객체화안해도 사용할수 있게 해줌.
    //timework.currDate()
    public static Calendar currDate() {
        SimpleDateFormat currDate = new SimpleDateFormat("yyyyMMdd");
        Calendar ret = Calendar.getInstance();
        Date curr = new Date(System.currentTimeMillis());
        ret.setTime(curr);

        return ret;

    }

}
