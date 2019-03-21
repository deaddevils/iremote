package com.iremote.action.camera.lechange;

import java.util.HashSet;
import java.util.Set;

import com.iremote.common.IRemoteConstantDefine;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.camera.AddCameraAction;
import com.iremote.action.camera.CameraHelper;
import com.iremote.action.camera.lechange.LeChangeRequestManagerStore;
import com.iremote.action.camera.lechange.LeChangeInterface;
import com.iremote.action.camera.lechange.LeChangeUserTokenManager;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.constant.CameraProductor;
import com.iremote.domain.Camera;
import com.iremote.service.CameraService;

public class AddLeChangeCameraAction extends AddCameraAction
{
	private static  Logger logdahua = Logger.getLogger("dahualechange");
	private static Set<String> DAHUALECHANGE_ERROR_MSG = new HashSet<String>();
	
	private int status;
	private String camerastatus ;
	private String lechangecode;
	private String lechangemsg;
	private String token ;
	private LeChangeInterface lcr;

	public AddLeChangeCameraAction() {
		devicetype = IRemoteConstantDefine.CAMERA_DEVICE_TYPE_DOMESTIC;
	}
	
	@Override
	protected boolean addcamera()
	{
		if ( !CameraProductor.dahualechange.getProductor().equals(productorid) )
		{
			this.resultCode = ErrorCodeDefine.NOT_SUPPORT;
			return false ;
		}
		
		if ( init() == false )
			return false ;
		
//		if ( checkCameraStatus() == false )  //can't query a camera status before bind it .
//			return false ;
		
		return adddahualechangecamera();
		
	}
	
	private boolean init()
	{
		LeChangeUserTokenManager tm = new LeChangeUserTokenManager();
		tm.setDevicetype(devicetype);
		token = tm.getToken(phoneuser);

		if ( token == null )
		{
			this.resultCode = tm.getResultCode();
			this.lechangecode = tm.getLechangecode();
			this.lechangemsg = tm.getLechangemsg();
			return false;
		}
		
		lcr = LeChangeRequestManagerStore.getInstance().getProcessor(phoneuser.getPlatform());
		
		if ( lcr == null )
		{
			resultCode = ErrorCodeDefine.NOT_SUPPORT;
			return false;
		}
		
		return true;
	}

	private boolean checkCameraStatus()
	{
		JSONObject rst = lcr.queryDeviceInfo(applianceuuid, token);
		String s = lcr.getData(rst, "status");
		if ( StringUtils.isNotBlank(s) && Integer.valueOf(s) == 1 ) //1 online
			return true ; 
		this.resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
		this.lechangecode = "DV1007"; // device offline
		return false ;
	}
	
	protected boolean adddahualechangecamera()
	{
		JSONObject rst = lcr.bindDevice(applianceuuid, code, token);
		if ( rst == null )
		{
			resultCode = ErrorCodeDefine.THIRDPART_NETWORK_FAILED;
			return false ;
		}
		
		lechangecode = translateLechangCode(lcr.getResultCode(rst));
		lechangemsg = lcr.getResultMsg(rst);
		
		if ( !ErrorCodeDefine.SUCCESS_STR.equals(lcr.getResultCode(rst))
				&& !"DV1003".equals(lcr.getResultCode(rst)))
		{
			resultCode = ErrorCodeDefine.THIRDPART_CALL_FAILED;
			return false ;
		}
		
		logdahua.info(String.format("%s,%s(%d),bind,result=%s,%s", applianceuuid,phoneuser.getPhonenumber(),phoneuser.getPhoneuserid() , lechangecode,lechangemsg));
		
		rst = lcr.queryDeviceInfo(applianceuuid,  token);
		
		camerastatus = lcr.getData(rst, "status");
		if ( StringUtils.isNotBlank(camerastatus))
			status = Integer.valueOf(camerastatus);

		devicetype = CameraProductor.dahualechange.getDevicetype();
		
		return true;
	}
	
	@Override
    protected void setCameraInfo(Camera camera)
    {

		if ( StringUtils.isBlank(this.camerastatus) || camera == null)
			return ;
		
		int s = Integer.valueOf(this.camerastatus);
		switch(s)
		{
		case 0:
			camera.setStatus(IRemoteConstantDefine.DEVICE_STATUS_MALFUNCTION);
			break;
		case 1:
		case 3:
			camera.setStatus(IRemoteConstantDefine.DEVICE_STATUS_NORMAL);
			break;
		}

    }
	
	private String translateLechangCode(String code)
	{
		if ( StringUtils.isBlank(code))
			return null;
		
		if ( !DAHUALECHANGE_ERROR_MSG.contains(code))
			return "DV1002";
		
//		if ( "OP1001".equals(code))
//			return "DV1002";
//		if ( StringUtils.isNumeric(code))
//			return "DV1002";
		return code ;
	}

	@Override
	protected String getDevicetype()
	{
		return CameraProductor.dahualechange.getDevicetype();
	}
	
	public int getStatus()
	{
		return status;
	}

	public String getLechangecode()
	{
		return lechangecode;
	}

	public String getLechangemsg()
	{
		return lechangemsg;
	}
	
	static 
	{
		DAHUALECHANGE_ERROR_MSG.add("DV1001");
		DAHUALECHANGE_ERROR_MSG.add("DV1002");
		DAHUALECHANGE_ERROR_MSG.add("DV1003");
		DAHUALECHANGE_ERROR_MSG.add("DV1005");
		DAHUALECHANGE_ERROR_MSG.add("DV1007");
		DAHUALECHANGE_ERROR_MSG.add("DV1013");
		DAHUALECHANGE_ERROR_MSG.add("DV1014");
		DAHUALECHANGE_ERROR_MSG.add("DV1015");
	}
}
