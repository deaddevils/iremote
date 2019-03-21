package com.iremote.action.helper;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.iremote.action.camera.lechange.LeChangeRequestManagerStore;
import com.iremote.common.GatewayUtils;
import com.iremote.common.ServerRuntime;
import com.iremote.common.constant.OemProductorAttributeCode;
import com.iremote.common.push.PushMessageThread;
import com.iremote.common.sms.SMSInterface;
import com.iremote.domain.OemProductor;
import com.iremote.domain.OemProductorAttribute;
import com.iremote.service.OemProductorService;

public class OemProductorHelper 
{
	public static void initOemProducotr()
	{
		OemProductorService  ops = new OemProductorService();
		List<OemProductor> lst = ops.query();
		
		for ( OemProductor op : lst)
		{
			PushMessageThread.initPushClient(op.getPlatform(), op.getPushmasterkey(), op.getPushappkey());
			SMSInterface.initSmsSender(op.getPlatform(), op.getSmssign());
            if (StringUtils.isBlank(op.getAbroadlechangeappid())) {
                LeChangeRequestManagerStore.getInstance().initLeChangeInterface(op.getPlatform(), op.getLechangeappid(), op.getLechangeappSecret());
            } else {
                LeChangeRequestManagerStore
                        .getInstance()
                        .initLeChangeInterface(op.getPlatform(), op.getLechangeappid(), op.getLechangeappSecret(), op.getAbroadlechangeappid(), op.getAbroadlechangeappSecret());
            }
			GatewayUtils.initRemotePlatformMap(op.getPlatform(), op.getDeviceprefix());
			ServerRuntime.getInstance().getOemproductormap().put(op.getPlatform(), op);
			op.getOemproductorattributelist().size();
		}
		PushMessageThread.appendiSurpassPushClient();
	}
	
	public static boolean hasArmFunction(int platform)
	{
		OemProductor op = ServerRuntime.getInstance().getOemproductormap().get(platform);
		if ( op == null )
			return false ;
		
		OemProductorAttribute opa = queryOemProductorAttribute(op , OemProductorAttributeCode.hasArmFunction.getCode());
		if ( opa == null || StringUtils.isBlank(opa.getValue() ))
			return false ;
		
		return Boolean.parseBoolean(opa.getValue());
	}
	
	public static OemProductorAttribute queryOemProductorAttribute(OemProductor op , String code)
	{
		if (StringUtils.isBlank(code) || op == null || op.getOemproductorattributelist() == null )
			return null ;
		for ( OemProductorAttribute opa : op.getOemproductorattributelist())
			if ( code.equals(opa.getCode()))
				return opa ;
		return null ;
	}

	public static boolean hasAbroadSmsPermission(OemProductor oemProductor) {
		OemProductorAttribute attribute = queryOemProductorAttribute(oemProductor, OemProductorAttributeCode.hasAbroadSmsPermission.getCode());
		return attribute != null && Boolean.parseBoolean(attribute.getValue());
	}
}
