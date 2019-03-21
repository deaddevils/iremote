package com.iremote.common.jms.vo;

import java.util.Date;

public interface IApplianceEvent
{
	String getDeviceid();
	String getEventtype();
	String getMajortype();
	String getName();
	Integer getStatus();
	Integer getPhoneuserid();
	Date getEventtime();
	Integer getWarningstatus();
	String getWarningstatuses();
	Integer getApptokenid();
}
