package com.iremote.common;

import java.util.Calendar;

public class TimerUtils {
    public static long getEndTimeOfToday() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        calendar.set(year, month, date + 1, 0, 0, 0);

        return calendar.getTimeInMillis();
    }
}
