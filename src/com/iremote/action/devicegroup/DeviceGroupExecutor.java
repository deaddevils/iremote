package com.iremote.action.devicegroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.dataprivilege.PhoneUserDataPrivilegeCheckor;
import com.iremote.device.operate.IOperationTranslator;
import com.iremote.device.operate.OperationTranslatorStore;
import com.iremote.domain.DeviceGroup;
import com.iremote.domain.DeviceGroupDetail;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.task.devicecommand.CommandHelper;
import com.iremote.task.devicecommand.ExecuteDeviceCommands;

public class DeviceGroupExecutor implements Runnable
{
	private static Log log = LogFactory.getLog(DeviceGroupExecutor.class);
	private static ExecutorService executeservice = Executors.newCachedThreadPool();

	private PhoneUser phoneuser ;
	private DeviceGroup devicegroup;
	private String commandjson ;
	
	private List<DeviceGroupDetail> detail = new ArrayList<DeviceGroupDetail>() ;
	private Map<String , List<CommandTlv>> command = new HashMap<String , List<CommandTlv>>();

	public DeviceGroupExecutor(PhoneUser phoneuser, DeviceGroup devicegroup, String commandjson)
	{
		super();
		this.phoneuser = phoneuser;
		this.devicegroup = devicegroup;
		this.commandjson = commandjson;
	}

	@Override
	public void run()
	{
		privilegefilter();
		if ( detail.size() == 0 )
			return ;
		
		createCommand();
		
		for ( String key : command.keySet())
		{
			if ( !ConnectionManager.contants(key))
			{
				log.info("gateway offline");
				continue;
			}
			List<CommandTlv> lst = CommandHelper.mergeCommand(key , command.get(key) , true);
			if ( lst == null )
				continue;
			executeservice.execute(new ExecuteDeviceCommands(key, lst));
		}
		
	}
	

	private void createCommand()
	{
		for ( DeviceGroupDetail dgd : detail )
		{
			ZWaveDeviceService zds = new ZWaveDeviceService();
			ZWaveDevice zd = zds.query(dgd.getZwavedeviceid());
			if ( zd == null )
				continue;;
			List<CommandTlv> lst = createCommand(dgd , zd);
			
			getCommandList(zd.getDeviceid()).addAll(lst);
		}
	}
	
	private List<CommandTlv> getCommandList(String deviceid)
	{
		if ( !command.containsKey(deviceid))
			command.put(deviceid, new ArrayList<CommandTlv>());
		
		return command.get(deviceid);
	}
	
	private List<CommandTlv> createCommand(DeviceGroupDetail dgd , ZWaveDevice zd )
	{
		List<CommandTlv> lst = new ArrayList<CommandTlv>();
		
		
		if ( dgd.getChannelids() == null || dgd.getChannelids().length == 0 )
		{
			IOperationTranslator ot = OperationTranslatorStore.getInstance().getOperationTranslator(zd.getMajortype(), zd.getDevicetype());
			ot.setZWavedevice(zd);
			ot.setCommandjson(commandjson);
			ot.setOperationType(IRemoteConstantDefine.OPERATOR_TYPE_DEVICE_GROUP );
			List<CommandTlv> l = ot.getCommandTlv();
			if ( l != null )
				lst.addAll(l);
		}
		else 
		{
			for ( int i = 0 ; i < dgd.getChannelids().length ; i ++ )
			{
				IOperationTranslator ot = OperationTranslatorStore.getInstance().getOperationTranslator(zd.getMajortype(), zd.getDevicetype());
				ot.setZWavedevice(zd);
				ot.setCommandjson(commandjson);
				ot.setOperationType(IRemoteConstantDefine.OPERATOR_TYPE_DEVICE_GROUP );

				JSONObject json = JSONObject.parseObject(commandjson);
				json.remove(IRemoteConstantDefine.CHANNELID);
				json.put(IRemoteConstantDefine.CHANNELID, dgd.getChannelids()[i]);
				
				ot.setCommandjson(json.toString());
				
				List<CommandTlv> l = ot.getCommandTlv();
				if ( l != null )
					lst.addAll(l);
			}
		}
		
		for ( CommandTlv ct : lst )
			ct.addUnit(new TlvByteUnit(TagDefine.TAG_OPERATOR , phoneuser.getPhonenumber().getBytes()));
		
		return lst ;
	}
	
	
	private void privilegefilter()
	{
		if ( devicegroup == null || devicegroup.getZwavedevices() == null || devicegroup.getZwavedevices().size() == 0)
			return ;
		
		PhoneUserDataPrivilegeCheckor dp = new PhoneUserDataPrivilegeCheckor(phoneuser);
		for ( DeviceGroupDetail dgd : devicegroup.getZwavedevices() )
		{
			if ( dp.checkZWaveDeviceOperationPrivilege(dgd.getZwavedeviceid()) == true )
				detail.add(dgd);
		}
	}
	
	
}
