package com.urise.webapp;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class MainDate {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date.getTime());
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.getTime());
        LocalDate ld = LocalDate.now();
        SimpleDateFormat sdf = new SimpleDateFormat("YY/MM/dd");
        System.out.println(sdf.format(cal.getTime()));

    }
}
