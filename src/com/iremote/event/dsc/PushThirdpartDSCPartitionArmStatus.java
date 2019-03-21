package com.iremote.event.dsc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.DSCEvent;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.domain.Partition;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.service.PartitionService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZWaveSubDeviceService;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

import net.sf.json.JSONObject;

public class PushThirdpartDSCPartitionArmStatus extends DSCEvent implements ITextMessageProcessor {
    @Override
    public String getTaskKey() 
    {
        return super.getDeviceid();
    }

    @Override
    public void run() 
    {
    	List<Integer> tidlst = ThirdPartHelper.queryThirdpartid(super.getDeviceid());
		
    	if ( tidlst == null || tidlst.size() == 0)
    		return ;
    	
    	PartitionService pdao = new PartitionService();
    	Partition p = pdao.query(super.getPartitionid());
    	if ( p == null )
    		return ;
    	ZWaveSubDeviceService zwsd = new ZWaveSubDeviceService();
    	List<ZWaveSubDevice> lst = zwsd.queryByParitionid(super.getPartitionid());
    	
    	List<Integer> lstchannelid = new ArrayList<Integer>();
    	if ( lst != null )
    		for ( ZWaveSubDevice zsd : lst )
    			lstchannelid.add(zsd.getChannelid());
    	
    	ZWaveDeviceService zds = new ZWaveDeviceService();
    	List<ZWaveDevice> lzd = zds.querybypartitionid(super.getPartitionid());
    	List<Integer> lstzid = new ArrayList<Integer>();
    	if ( lzd != null )
    		for ( ZWaveDevice zd : lzd )
    			lstzid.add(zd.getZwavedeviceid());
    	
    	JSONObject json = new JSONObject();
    	json.put("armstatus", super.getArmstatus());
    	json.put("dscpartitionid", p.getDscpartitionid());
    	json.put("channelid", lstchannelid);
    	json.put("zwavedeivceid", lstzid);
    	
		for ( Integer tpid : tidlst )
		{
			if ( tpid == null || tpid == 0 )
				continue ;
			
			EventtoThirdpart etd = new EventtoThirdpart();
			
			etd.setThirdpartid(tpid);
			etd.setType(IRemoteConstantDefine.EVENT_DSC_PARTITION_ARM_STATUS);
			etd.setDeviceid(getUserDeviceid(lzd , lst));
			etd.setEventtime(getEventtime());
			etd.setIntparam(super.getArmstatus());
			if ( p.getZwavedevice() != null )
				etd.setZwavedeviceid(p.getZwavedevice().getZwavedeviceid());
			etd.setObjparam(json.toString());
			
			EventtoThirdpartService svr = new EventtoThirdpartService();
			svr.save(etd);
		}
    }
    
    private String getUserDeviceid(List<ZWaveDevice> lzd , List<ZWaveSubDevice> lst)
    {
    	if ( StringUtils.isNotBlank(getDeviceid()))
    		return this.getDeviceid();
    	else if ( lzd != null && lzd.size() > 0 )
    		return lzd.get(0).getDeviceid();
    	else if ( lst != null && lst.size() > 0 )
    		return lst.get(0).getZwavedevice().getDeviceid();
    	return null ;
    }
}
