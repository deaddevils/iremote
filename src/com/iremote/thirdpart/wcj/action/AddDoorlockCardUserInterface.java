package com.iremote.thirdpart.wcj.action;

import com.iremote.domain.ZWaveDevice;

public interface AddDoorlockCardUserInterface {
	
	int init();
	
	boolean sendcommand();
	
	void setMainValue(String username,int usercode,String cardinfo, int cardtype, String validfrom, String validthrough, int zwavedeviceid,
			ZWaveDevice lock,int weekday ,String starttime,String endtime);
	
	int getResultCode();
	
	int getUsercode();

}
