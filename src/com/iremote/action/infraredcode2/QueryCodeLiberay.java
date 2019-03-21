package com.iremote.action.infraredcode2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.domain.InfreredCodeLiberayModel;
import com.iremote.domain.InfreredDeviceProductor;
import com.iremote.service.InfreredCodeLiberayModelService;
import com.iremote.service.InfreredDeviceProductorService;
import com.iremote.vo.RemoteControlVO;
import com.opensymphony.xwork2.Action;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class QueryCodeLiberay {
    private static Log log = LogFactory.getLog(QueryProductor.class);
    private String productor;
    private String devicetype;
    private int resultCode;
    private List<Integer> codeids = new ArrayList<Integer>();
    private List<RemoteControlVO> remotecontrol = new ArrayList<RemoteControlVO>();

    public String execute() 
    {
    	InfreredDeviceProductorService svr = new InfreredDeviceProductorService();
    	List<InfreredDeviceProductor> lst = svr.queryByDeviceTypeandProductor(devicetype , productor);
    	if ( lst == null || lst.size() == 0 )
    		return Action.SUCCESS;
    	
    	InfreredDeviceProductor idp = lst.get(0);
    	String cids = idp.getCodeids();
    	if ( StringUtils.isBlank(cids))
    		return Action.SUCCESS;
    	
    	JSONArray ja = JSON.parseArray(cids);
    	Integer[] ia = ja.toArray(new Integer[0]);
    	for ( int i = 0 ; i < ia.length ; i ++ )
    		codeids.add(ia[i]);
    	
    	InfreredCodeLiberayModelService icms = new InfreredCodeLiberayModelService();
    	List<InfreredCodeLiberayModel> lm = icms.queryByDevicetypeandProductor(devicetype, productor);
    	
    	if ( lm == null || lm.size() == 0 )
    		return Action.SUCCESS;
    	
    	for ( InfreredCodeLiberayModel iclm : lm )
    	{
    		 RemoteControlVO rcvo = new RemoteControlVO();
             rcvo.setCodeid(iclm.getCodeid());
             rcvo.setCtrlcode(iclm.getModel());
             remotecontrol.add(rcvo);
    	}
        return Action.SUCCESS;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public void setDevicetype(String devicetype) {

        this.devicetype = devicetype;
    }

    public int getResultCode() {
        return resultCode;
    }

    public List<Integer> getCodeids() {
        return codeids;
    }

    public List<RemoteControlVO> getRemotecontrol() {
        return remotecontrol;
    }

}
