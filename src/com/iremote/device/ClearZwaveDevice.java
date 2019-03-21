package com.iremote.device;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.iremote.domain.*;
import com.iremote.service.*;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.action.helper.TimerHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;

public class ClearZwaveDevice
{
	private ZWaveDevice zwavedevice;

	public ClearZwaveDevice(ZWaveDevice zd)
	{
		this.zwavedevice = zd;
	}

	public void clear()
	{
		if ( this.zwavedevice == null )
			return ;

		removeDevicefromGateway();
		unalarmdevice();
		clearRoomAppliance();
		clearRoomDevice();
		clearSceneCommand();
		clearAssociation();
		clearDeviceGroupAppliance();

		clearUserShare();
		clearDeviceShare();

		clearBlueToothPassword();
	}

	private void clearBlueToothPassword() {
		new BlueToothPasswordService().deleteByZwaveDeviceId(zwavedevice.getZwavedeviceid());
	}

	public void clearServerData()
	{
		if ( this.zwavedevice == null )
			return ;

		unalarmdevice();
		clearRoomAppliance();
		clearSceneCommand();
		clearAssociation();
		clearDeviceGroupAppliance();

		clearUserShare();
		clearDeviceShare();
	}

	public void removeDevicefromGateway()
	{
		CommandTlv ct = new CommandTlv(105 , 7);
		ct.addUnit(new TlvIntUnit(TagDefine.TAG_NUID,zwavedevice.getNuid() , 4));

		SynchronizeRequestHelper.asynchronizeRequest(zwavedevice.getDeviceid(), ct, 1);

		DeviceIndentifyInfoService svr = new DeviceIndentifyInfoService();
		List<DeviceIndentifyInfo> lst = svr.querybyZwavedeviceid(zwavedevice.getZwavedeviceid());

		if ( lst != null )
		{
			for ( DeviceIndentifyInfo di : lst )
				di.setZwavedeviceid(null);
		}
	}

	public void clearDeviceGroupAppliance()
	{
		DeviceGroupDetailService dgds = new DeviceGroupDetailService();

		List<DeviceGroupDetail> lst = dgds.querybyZwavedeviceid(zwavedevice.getZwavedeviceid());
		if ( lst == null || lst.size() == 0 )
			return ;

		DeviceGroupService dgs = new DeviceGroupService();
		for ( DeviceGroupDetail dgd : lst )
		{
			dgd.getDevicegroup().getZwavedevices().remove(dgd);
			if ( dgd.getDevicegroup().getZwavedevices().size() == 0 )
				dgs.delete(dgd.getDevicegroup());
		}
	}

	public void clearRoomAppliance()
	{
		RoomApplianceService ras = new RoomApplianceService();
//		List<RoomAppliance> lst = ras.query(zwavedevice.getDeviceid(), zwavedevice.getApplianceid());
		List<RoomAppliance> lst = ras.querybyZwavedeviceid(zwavedevice.getZwavedeviceid());

		if ( lst != null )
		{
			for ( RoomAppliance ra : lst )
				ras.delete(ra);
		}
	}

	public void clearRoomDevice() {
		RoomDeviceService rds = new RoomDeviceService();
		List<RoomDevice> roomDevices = rds.querybydeviceid(zwavedevice.getZwavedeviceid());
		rds.deleteAll(roomDevices);
	}

	public void unalarmdevice()
	{
		if ( zwavedevice.getWarningstatuses() != null && zwavedevice.getWarningstatuses().length() > 0 )
		{
			JSONArray ja = JSON.parseArray(zwavedevice.getWarningstatuses());
			for ( int i = 0 ; i < ja.size() ; i ++ )
			{
				String message = Utils.unalarmmessage(zwavedevice.getDevicetype(), ja.getIntValue(i));
				if ( StringUtils.isBlank(message))
					continue;
				JMSUtil.sendmessage(message, new ZWaveDeviceEvent(zwavedevice.getZwavedeviceid() , zwavedevice.getDeviceid() , zwavedevice.getNuid() ,message, new Date(),0 ));
			}
		}
	}

	public void clearAssociation()
	{
		AssociationsceneService ass = new AssociationsceneService();
		List<Associationscene> lst = ass.querybyZwaveDeviceid(Arrays.asList(new Integer[]{zwavedevice.getZwavedeviceid()}));

		if ( lst == null || lst.size() == 0 )
			return ;

		SceneService ss = new SceneService();

		for ( Associationscene as : lst  )
		{
			if ( IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION == as.getScene().getScenetype())
				ss.delete(as.getScene());
			else
				as.getScene().getAssociationscenelist().remove(as);
			this.zwavedevice.getAssociationscenelist().remove(as);
		}
	}

	public void clearSceneCommand()
	{
		TimerHelper.cancelTimer(this.zwavedevice.getTimer());
		this.zwavedevice.getTimer().clear();

		ConditionsService conditionsService = new ConditionsService();
		List<Conditions> conditionsList = conditionsService.querybyZwavedeviceid(zwavedevice.getZwavedeviceid());
		for (Conditions conditions : conditionsList) {
			conditions.getScene().getConditionlist().remove(conditions);
		}

		SceneService ss = new SceneService();
		CommandService cs = new CommandService();
		List<Command> clst = cs.querybyZwavedeviceid(zwavedevice.getZwavedeviceid());
		for ( Command c : clst )
		{
			c.getScene().getCommandlist().remove(c);
//			if ( c.getAssociationscene() != null && c.getAssociationscene().getCommandlist() != null )
//				c.getAssociationscene().getCommandlist().remove(c);
			if ( c.getScene().getCommandlist().size() == 0 )
				ss.delete(c.getScene());
		}
	}

	public void clearUserShare()
	{
		UserShareService uss = new UserShareService();
		UserShareDeviceService usds = new UserShareDeviceService();
		List<UserShareDevice> userShareDevices = usds.queryByZwavedeviceid(zwavedevice.getZwavedeviceid());
		if(userShareDevices != null)
			for(UserShareDevice usd : userShareDevices)
			{
				usd.getUserShare().getUserShareDevices().remove(usd);
				if ( usd.getUserShare().getUserShareDevices().size() == 0 )
					uss.delete(usd.getUserShare());
			}
	}

	public void clearDeviceShare()
	{
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();

		List<ZWaveDeviceShare> lst = zdss.querybyZwaveDeviceid(zwavedevice.getZwavedeviceid());
		zdss.delete(lst);
	}
}
