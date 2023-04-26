package com.example.homework5.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateConverter {

    private static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public static String dateToLong(LocalDate date){
        return dateFormat.format(date);
    }

    public static LocalDate stringToDate(String dateStr) throws ParseException {
        Date date = dateFormat.parse(dateStr);
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

}
