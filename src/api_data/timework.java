package api_data;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLFault;

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
