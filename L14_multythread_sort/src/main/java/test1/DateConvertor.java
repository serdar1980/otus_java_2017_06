package test1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Date;

/**
 * Created by Serdar on 19.09.2017.
 */
public class DateConvertor {
    //TimeZone.getTimeZone("UTC")
    public static final String STRING_FORMAT="yyyy-MM-dd'T'HH:mm:ss.SSSZZ";
    public static String convertTimeZoneString(Date date, TimeZone from) {
        String converted_date = "";
        try {

            SimpleDateFormat formatFrom = new SimpleDateFormat(STRING_FORMAT);
            formatFrom.setTimeZone(from);

            converted_date =formatFrom.format(date);

        }catch (Exception e){ e.printStackTrace();}

        return converted_date;
    }

    public static Date convertTimeZoneDate(String strDate, TimeZone from, TimeZone to) {
        String str_date = "";
        Date converted_date=null;
        try {

            DateFormat formatFrom = new SimpleDateFormat(STRING_FORMAT);
            formatFrom.setTimeZone(from);

            Date date = formatFrom.parse(strDate);

            SimpleDateFormat formatTo = new SimpleDateFormat(STRING_FORMAT);
            formatTo.setTimeZone(to);

            str_date =  formatTo.format(date);
            converted_date = formatTo.parse(str_date);
        }catch (Exception e){ e.printStackTrace();}

        return converted_date;
    }


    //get the current time zone

    public static  TimeZone getCurrentTimeZone(){
        TimeZone tz = Calendar.getInstance().getTimeZone();
        System.out.println(tz.getDisplayName());
        return TimeZone.getTimeZone(tz.getID());
    }
}
