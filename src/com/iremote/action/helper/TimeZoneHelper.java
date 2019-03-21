package com.iremote.action.helper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.PhoneUser;

public class TimeZoneHelper 
{
	public static String createtimezonelabel(TimeZone tz , PhoneUser phoneuser)
    {
		long offset = tz.getRawOffset();
        double decimals = offset % 3600000;
        double decimalsZone = (decimals / 3600000) * 60 / 100;

        decimalsZone = decimalsZone < 0 ? -decimalsZone : decimalsZone;
        String sDecimalsZone = decimalsZone + "";
        sDecimalsZone = sDecimalsZone.substring(2);
        if (sDecimalsZone.length() == 1)
        {
            sDecimalsZone = sDecimalsZone + '0';
        }
        else if (sDecimalsZone.length() >= 3)
        {
            sDecimalsZone = sDecimalsZone.substring(0, 2);
        }
        
		
        StringBuffer sb = new StringBuffer();
        sb.append("GMT").append(createTimezoneOffsetHour(tz)).append(":").append(sDecimalsZone);
        sb.append(" ").append(createTimezoneDisplayname(tz , phoneuser)).append("(").append(tz.getID()).append(")");
        
        return sb.toString();
    }
	
	public static String createTimezoneDisplayname(TimeZone tz , PhoneUser phoneuser)
	{
		Locale l = Locale.US;
		if ( IRemoteConstantDefine.DEFAULT_LANGUAGE.equals(phoneuser.getLanguage()))
			l = Locale.CHINA;
		return tz.getDisplayName(l);
	}
	
	public static String createTimezoneOffsetHour(TimeZone tz)
	{
		long offset = tz.getRawOffset();
        long hour = Long.valueOf((offset / 3600000));
        String sAdd = "";
        if (hour >= 0)
        {
            sAdd = "+";
        }
        else
        {
            sAdd = "-";
        }
        hour = hour > 0 ? hour : -hour;
        String sHour = hour + "";
        if (sHour.length() == 1)
        {
            sHour = '0' + sHour;
        }
        return sAdd + sHour;
	}
	
	public static Date timezoneTranslate(Date d , TimeZone fromtimezone , TimeZone ttz)
	{
		Calendar c = createCalendar(d , fromtimezone);
		timezoneTranslate(c , ttz);
		return c.getTime();
	}
	
	public static Date timezoneTranslate(Date d , String fromtimezonid, String tozoneid)
	{
		return timezoneTranslate(d , TimeZone.getTimeZone(fromtimezonid) , TimeZone.getTimeZone(tozoneid));
	}
	
	public static void timezoneTranslate(Calendar c , String tozoneid)
	{
		TimeZone ttz = TimeZone.getTimeZone(tozoneid);
		timezoneTranslate(c , ttz);
	}
	
	public static void timezoneTranslate(Calendar c , TimeZone ttz)
	{
		c.add(Calendar.MILLISECOND, -1 * c.get(Calendar.ZONE_OFFSET) - c.get(Calendar.DST_OFFSET));
		 
		c.setTimeZone(ttz);
		
		c.add(Calendar.MILLISECOND, c.get(Calendar.ZONE_OFFSET)) ;
		c.add(Calendar.MILLISECOND, c.get(Calendar.DST_OFFSET));
	}
	
	public static Calendar createCalendar(Date date , TimeZone tz)
	{
		Calendar c = Calendar.getInstance();
		c.setTimeZone(tz);
		c.setTime(date);
		return c;
	}

	public static LocalDateTime dateToLocalDateTime(Date date) {
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone);
	}
}
