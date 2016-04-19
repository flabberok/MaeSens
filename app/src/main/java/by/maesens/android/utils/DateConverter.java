package by.maesens.android.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Виктор on 10.03.2016.
 */
public class DateConverter {

    public static Date convertStringToDate(String aDate, String aFormat) {
        if(aDate == null) return null;
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        try {
            return simpledateformat.parse(aDate);
        } catch(ParseException e){
            return null;
        }
    }

    public static String convertDateToString(Date aDate, String aFormat) {
        if(aDate == null) return "";
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        return simpledateformat.format(aDate);
    }
    public static String convertDateForComments(String date) {

        if (date.equals("")) {
            return "";
        }

        Date dateSource = new Date();
        SimpleDateFormat simpledateformat = new SimpleDateFormat();
        try {
            dateSource = simpledateformat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMMM HH:mm");
        return simpleDateFormat.format(dateSource);
    }
}
