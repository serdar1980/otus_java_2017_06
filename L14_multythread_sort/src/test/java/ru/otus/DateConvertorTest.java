package ru.otus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.junit.Assert;
import org.junit.Test;
import test1.DateConvertor;

/**
 * Created by Serdar on 19.09.2017.
 */
public class DateConvertorTest {

    @Test
    public void getUTCStringReturnDateLocal(){
        String strFromJson ="2011-10-05T14:48:00.000Z";
        Date date = DateConvertor.convertTimeZoneDate(strFromJson, TimeZone.getTimeZone("UTC"), DateConvertor.getCurrentTimeZone());
        SimpleDateFormat formatFrom = new SimpleDateFormat(DateConvertor.STRING_FORMAT);
        System.out.println(date);
        String fromConvert = DateConvertor.convertTimeZoneString(date, TimeZone.getTimeZone("UTC"))+"Z";
        Assert.assertEquals(strFromJson, fromConvert);
    }
}
