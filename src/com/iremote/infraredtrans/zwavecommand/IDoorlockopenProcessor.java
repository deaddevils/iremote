package com.iremote.infraredtrans.zwavecommand;

import java.util.Date;

public interface IDoorlockopenProcessor extends IZwaveReportProcessor 
{
	int getNuid();
	boolean isOpenreport();
	int getUsercode();
	int getUsertype();
	int getIntReporttime();
	Date getReporttime();
	String getOperator();
	void setOperator(String operator);
	int getPriority();
}
