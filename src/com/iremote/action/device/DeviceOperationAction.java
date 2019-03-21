package com.iremote.action.device;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.device.doorlock.DoorlockCommandCache;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.asycresponse.IAsyncResponse;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.device.operate.IOperationTranslator;
import com.iremote.device.operate.OperationTranslatorStore;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ThirdPart;
import com.iremote.domain.ZWaveDevice;
import com.iremote.event.gateway.GatewayEventConsumerStore;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "device", parameter = "zwavedeviceid")
public class DeviceOperationAction 
{
	private Integer resultCode = ErrorCodeDefine.SUCCESS ;
	private int zwavedeviceid ;
	private String command ;
	private Integer channel;
	private int operationtype = IRemoteConstantDefine.OPERATOR_TYPE_APP_USER ;
	private PhoneUser phoneuser ;
	private ThirdPart thirdpart ;
	
	public String execute()
	{
		if ( StringUtils.isBlank(command) || this.zwavedeviceid == 0 )
		{
			this.resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zd = zds.query(zwavedeviceid);
		
		if ( zd == null )
		{
			this.resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		String commandjson = command ;
		if ( this.channel != null && this.channel != 0 )
		{
			JSONObject json = JSON.parseObject(command);
			json.put(IRemoteConstantDefine.CHANNELID, this.channel);
			commandjson = json.toJSONString();
		}
		
		List<CommandTlv> lst = createCommandTlv(zd,commandjson);
		if ( lst == null || lst.size() == 0 )
		{
			this.resultCode = ErrorCodeDefine.NOT_SUPPORT;
			return Action.SUCCESS;
		}
		
		appendOperator(lst);
		
		if ( !ConnectionManager.contants(zd.getDeviceid()))
		{
			if ( IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(zd.getDevicetype()) && !DeviceHelper.isZwavedevice(zd.getNuid()))
			{
				cacheCommand(zd.getDeviceid(),lst);
				resultCode = ErrorCodeDefine.WAKEUP_DEVICE;
	   			return Action.SUCCESS;
			}
			else 
			{
				resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
	   			return Action.SUCCESS;
			}
		}
		
		List<IAsyncResponse> arlst = new ArrayList<IAsyncResponse>();
		for ( CommandTlv ct : lst )
			arlst.add(SynchronizeRequestHelper.asynchronizeRequest(zd.getDeviceid(), ct, 0));
		
		for ( IAsyncResponse ar : arlst )
		{
			byte[] rst = (byte[])ar.getAckResponse(IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND);
			
			if ( rst == null  )
			{
				if ( IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(zd.getDevicetype()) &&  !DeviceHelper.isZwavedevice(zd.getNuid()) )
				{
					cacheCommand(zd.getDeviceid(),lst);
					resultCode = ErrorCodeDefine.WAKEUP_DEVICE;
		   			return Action.SUCCESS;
				}
				else 
				{
					resultCode = ErrorCodeDefine.TIME_OUT;
		   			return Action.SUCCESS;
				}
			}
		   	else 
		   	{
		   		resultCode = TlvWrap.readInteter(rst , 1 , TlvWrap.TAGLENGTH_LENGTH);
		   		if ( resultCode == null )
		   		{
		   			resultCode = ErrorCodeDefine.TIME_OUT;
		   			return Action.SUCCESS;
		   		}
		   		else if ( resultCode != ErrorCodeDefine.SUCCESS )
		   			return Action.SUCCESS;
		   	}
		}
		
		return Action.SUCCESS;
	}
	
	private void appendOperator(List<CommandTlv> lst)
	{
		if ( this.phoneuser == null && this.thirdpart == null )
			return ;
		
		Integer tokenid = PhoneUserHelper.getTokenid();
		for ( CommandTlv ct : lst)
		{
			if ( this.phoneuser != null )
				ct.addOrReplaceUnit(new TlvByteUnit(TagDefine.TAG_OPERATOR , phoneuser.getPhonenumber().getBytes()));
			else if ( this.thirdpart != null )
				ct.addOrReplaceUnit(new TlvByteUnit(TagDefine.TAG_OPERATOR , this.thirdpart.getName().getBytes()));
			if ( tokenid != null )
				ct.addUnit(new TlvIntUnit(TagDefine.TAG_APP_USER_TOKEN_ID , tokenid , 4));
		}
	}
	
	private void cacheCommand(String deviceid , List<CommandTlv> ctl)
	{
		for ( CommandTlv ct : ctl)
			GatewayEventConsumerStore.getInstance().put(deviceid,new DoorlockCommandCache(deviceid , ct));
	}
	
	private List<CommandTlv> createCommandTlv(ZWaveDevice zd , String commandjson )
	{
		IOperationTranslator ot = OperationTranslatorStore.getInstance().getOperationTranslator(zd.getMajortype(), zd.getDevicetype());
		if ( ot == null )
			return null ;
		ot.setZWavedevice(zd);
		ot.setCommandjson(commandjson);
		ot.setOperationType(operationtype);
		
		return ot.getCommandTlv();
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}
	public void setOperationtype(int operationtype)
	{
		this.operationtype = operationtype;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public void setThirdpart(ThirdPart thirdpart)
	{
		this.thirdpart = thirdpart;
	}
	
}
