package com.iremote.event.device;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.Associationscene;
import com.iremote.domain.Command;
import com.iremote.domain.UserShareDevice;
import com.iremote.service.AssociationsceneService;
import com.iremote.service.CommandService;
import com.iremote.service.SceneService;
import com.iremote.service.UserShareDeviceService;
import com.iremote.service.UserShareService;

public class OnDeleteZWaveDevice extends ZWaveDeviceEvent implements ITextMessageProcessor{

	@Override
	public void run() 
	{
//		unalarmdevice();
//		clearSceneCommand();
//		clearAssociation();
//		
//		clearUserShare();
	}
	
//	private void unalarmdevice()
//	{
//		if ( super.getWarningstatuses() != null && super.getWarningstatuses().length() > 0 )
//		{
//			JSONArray ja = JSON.parseArray(super.getWarningstatuses());
//			for ( int i = 0 ; i < ja.size() ; i ++ )
//			{
//				String message = Utils.unalarmmessage(getDevicetype(), ja.getIntValue(i));
//				if ( StringUtils.isBlank(message))
//					continue;
//				JMSUtil.sendmessage(message, new ZWaveDeviceEvent(getZwavedeviceid() , getDeviceid() , getNuid() ,message, this.getEventtime(),0 ));
//			}
//		}
//	}
//	
//	private void clearAssociation()
//	{
//		AssociationsceneService ass = new AssociationsceneService();
//		List<Associationscene> lst = ass.querybyZwaveDeviceid(Arrays.asList(new Integer[]{super.getZwavedeviceid()}));
//
//		if ( lst == null || lst.size() == 0 )
//			return ;
//		
//		SceneService ss = new SceneService();
//		
//		for ( Associationscene as : lst  )
//		{
//			if ( IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION == as.getScene().getScenetype())
//				ss.delete(as.getScene());
//			else 
//				as.getScene().getAssociationscenelist().remove(as);
//		}
//	}
//	
//	private void clearSceneCommand()
//	{
//		SceneService ss = new SceneService();
//		CommandService cs = new CommandService();
//		List<Command> clst = cs.querybyZwavedeviceid(super.getZwavedeviceid());
//		for ( Command c : clst )
//		{
//			c.getScene().getCommandlist().remove(c);
//			if ( c.getScene().getCommandlist().size() == 0 )
//				ss.delete(c.getScene());
//		}
//	}
//
//	private void clearUserShare()
//	{
//		UserShareService uss = new UserShareService();
//		UserShareDeviceService usds = new UserShareDeviceService();
//		List<UserShareDevice> userShareDevices = usds.queryByZwavedeviceid(super.getZwavedeviceid());
//		if(userShareDevices != null)
//			for(UserShareDevice usd : userShareDevices)
//			{
//				usd.getUserShare().getUserShareDevices().remove(usd);
//				if ( usd.getUserShare().getUserShareDevices().size() == 0 )
//					uss.delete(usd.getUserShare());
//			}
//	}
	

	@Override
	public String getTaskKey() {
		return getDeviceid();
	}

}
