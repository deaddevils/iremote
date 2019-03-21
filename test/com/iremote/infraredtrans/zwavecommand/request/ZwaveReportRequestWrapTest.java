package com.iremote.infraredtrans.zwavecommand.request;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredtrans.serianet.SeriaNetReportBean;
import com.iremote.infraredtrans.serianet.dsc.DSCHelper;
import com.iremote.infraredtrans.serianet.dsc.DSCReportNotifyProcessor;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class ZwaveReportRequestWrapTest 
{
	public static void main(String arg[])
	{
		ZwaveReportRequestWrap rw = new ZwaveReportRequestWrap("iRemote2005000000704" , IRemoteConstantDefine.DEVICE_NUID_DSC , new CommandTlv(107 , 1),DSCHelper.getArmCommandResponseKeyforPartion1() , 10 , 0);
		
		SeriaNetReportBean zrb = new SeriaNetReportBean();
		zrb.setCmd(new byte[]{107,3,0,13,0,93,0,9,54,53,50,49,48,70,69,13,10,5});
		zrb.setDeviceid("iRemote2005000000704");

		DSCReportNotifyProcessor pro = new DSCReportNotifyProcessor();
		pro.setReport(zrb);
		
		pro.run();
		
		rw.getResponse();
		
	}
}
