package com.iremote.action.device;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.helper.SceneHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.device.operate.ACCommandStore;
import com.iremote.device.operate.IDeviceOperateCommand;
import com.iremote.device.operate.ZwaveDeviceCommandStore;
import com.iremote.device.operate.infrareddevice.TvStbOperateCommandBase;
import com.iremote.device.operate.infrareddevice.ac.ACOperateCommandBase;
import com.iremote.device.operate.zwavedevice.ZwaveDeviceOperateCommandBase;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Scene;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.scene.SceneExecutor;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.SceneService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZWaveDeviceShareService;
import com.iremote.service.ZWaveSubDeviceService;
import com.opensymphony.xwork2.Action;

public class OperateDevicebyGowildAction {
	
	private static Log log = LogFactory.getLog(OperateDevicebyGowildAction.class);

	private static Map<String , String> operationtypemap = new HashMap<String , String>();
	private static Map<String , Integer> tvstboperationindexmap = new HashMap<String , Integer>();
	
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String command ;
	protected String operator;
	protected PhoneUser phoneuser ;

	public String execute()
	{
		if ( command == null || command.length() == 0 )
			return Action.SUCCESS;
		
		if ( operator == null || operator.length() == 0 )
			operator = phoneuser.getPhonenumber();
		
		JSONObject json = (JSONObject)JSON.parse(command);
		
		if ( json.containsKey("scene") && json.getString("scene").length() > 0 )
			triggerScene(json);
		else 
			operateDevice(json);
		
		return Action.SUCCESS;
	}
	
	private void triggerScene(JSONObject json)
	{
		String sname = json.getString("scene");
		
		if ( sname == null || sname.length() == 0 ) 
			return ;
		
		SceneService ss = new SceneService();

		List<Scene> lst = ss.queryScenebyPhoneuserid(PhoneUserHelper.querybySharetoPhoneuserid(phoneuser.getPhoneuserid()));
		
		
		for ( Scene s : lst )
		{
			if ( sname.equals(s.getName()))
			{
				SceneExecutor se = new SceneExecutor(s.getScenedbid(), phoneuser, null, operator,
						IRemoteConstantDefine.OPERATOR_TYPE_SCENE,
						IRemoteConstantDefine.SCENE_EXECUTE_TYPE_USER_TRIGGER);
				se.run();
			}
		}
	}
	

	private void operateDevice(JSONObject json)
	{
		String dname = json.getString("name");

		if ( StringUtils.isEmpty(dname)) 
			return ;

		Object obj = queryZwavedvice(dname);
		if(obj != null){
			operateZwavedevice(obj , json);
		}else{
			InfraredDevice id = queryInfrareddevice(dname);
			log.info(JSON.toJSONString(id));
			if ( id != null )
				operateInfraredDevice(id , json);
			else 
				resultCode = ErrorCodeDefine.TARGET_NOT_EXSIT;
		}
	}

	
	private void operateZwavedevice(Object obj, JSONObject json)
	{
		ZWaveDevice zd = null;
		ZWaveSubDevice zsd = null;
		if(obj instanceof ZWaveDevice){
			zd = (ZWaveDevice) obj;
		}else if(obj instanceof ZWaveSubDevice){
			zsd = (ZWaveSubDevice) obj;
			zd = zsd.getZwavedevice();
		}
		String state = json.getString("state");
		if ( state != null && state.length() > 0  )
		{
			String op = operationtypemap.get(String.format("%s_%s", zd.getDevicetype() , state));
			if ( op == null )
				return ;
			ZwaveDeviceOperateCommandBase zdc = ZwaveDeviceCommandStore.getInstance().getProcessor(zd.getDevicetype(), op);
			if ( zdc == null )
			{
				resultCode = ErrorCodeDefine.NOT_SUPPORT;
				return ;
			}
			
			zdc.setZwavedevice(zd);
			if(zsd != null){
				zdc.setChannel((byte)zsd.getChannelid());
			}
			CommandTlv ct = zdc.createCommand();
			ct.addUnit(new TlvByteUnit(12 , operator.getBytes()));
			ct.addUnit(new TlvIntUnit(79 ,IRemoteConstantDefine.OPERATOR_TYPE_APP_USER , 1));
			
			SynchronizeRequestHelper.synchronizeRequest(zd.getDeviceid(), ct , IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND);

			return ;
		}
	}

	private void operateInfraredDevice(InfraredDevice id , JSONObject json)
	{
		IDeviceOperateCommand doc = null ;
		if ( IRemoteConstantDefine.DEVICE_TYPE_STB.equals(id.getDevicetype()) 
				|| IRemoteConstantDefine.DEVICE_TYPE_TV.equals(id.getDevicetype()) )
		{
			Integer index = getCommandIndex(id , json);
			log.info(index);
			if ( index != null )
			{
				TvStbOperateCommandBase toc = new TvStbOperateCommandBase();
				toc.setIndex(index);
				toc.setCodeid(id.getCodeid());
				toc.setDevicetype(id.getDevicetype());
				doc = toc ;
				
			}
			else 
			{
				String av = json.getString("attributeValue");
				if ( av.length() > 1 && StringUtils.isNotEmpty(av) )
				{
					for ( int i = 0 ; i < av.length() ; i ++ )
					{
						String sv = av.substring(i , i + 1 );
						json.put("attributeValue", sv);
						log.info(json.toJSONString());
						operateInfraredDevice(id , json);
					}
					return ;
				}
			}
		}
		else if ( IRemoteConstantDefine.DEVICE_TYPE_AC.equals(id.getDevicetype()) )
		{
			ACOperateCommandBase acb = ACCommandStore.getInstance().getProcessor(json);
			if ( acb == null)
			{
				String av = json.getString("attributeValue");
				if ( StringUtils.isNumeric(av))
				{
					json.remove("attributeValue");
					acb = ACCommandStore.getInstance().getProcessor(json);
					if ( acb != null )
						acb.setValue(Byte.valueOf(av));
				}
			}
			if ( acb != null )
				acb.setInfrareddevice(id);
			
			doc = acb ;
		}
		
		if ( doc == null )
		{
			resultCode = ErrorCodeDefine.NOT_SUPPORT;
			return ;
		}
		CommandTlv ct = doc.createCommand();
		Utils.print("", ct.getByte());
		SynchronizeRequestHelper.synchronizeRequest(id.getDeviceid(), ct , IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND);
	}
	
	private Integer getCommandIndex(InfraredDevice id ,JSONObject json)
	{
		return tvstboperationindexmap.get(makeKey(id.getDevicetype() , json));	
	}
	

	private static String makeKey(String devicetype , JSONObject json)
	{
		return String.format("%s_%s_%s_%s_%s_%s", devicetype,
													Utils.getJsonStringValue(json, "state", ""),
													Utils.getJsonStringValue(json, "action", ""),
													Utils.getJsonStringValue(json, "attribute", ""),
													Utils.getJsonStringValue(json, "attributeValue", ""),
													Utils.getJsonStringValue(json, "mode", ""));
	}
	
	
	
	private Object queryZwavedvice(String name)
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		
		List<String> lst = PhoneUserHelper.queryDeviceidbySharetoPhoneuserid(phoneuser.getPhoneuserid());
		List<ZWaveDevice> dlst = zds.querybydeviceidandName(lst , name);
		if ( dlst != null && dlst.size() > 0 )
			return dlst.get(0);
		
		ZWaveSubDeviceService zsds = new ZWaveSubDeviceService();
		List<ZWaveSubDevice> zsdls = zsds.querybydeviceidandName(lst, name);
		if(zsdls != null && zsdls.size() > 0){
			return zsdls.get(0);
		}
		
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		List<Integer> zidl = zdss.querySharedZwavedeviceid(phoneuser.getPhoneuserid());
		
		if ( zidl == null || zidl.size() == 0 )
			return null ;
		
		dlst = zds.querybyIdandName(zidl , name);
		if ( dlst != null && dlst.size() > 0 )
			return dlst.get(0);
		
		return null ;
	}
	
	private InfraredDevice queryInfrareddevice(String name)
	{
		List<String> lst = PhoneUserHelper.queryDeviceidbySharetoPhoneuserid(phoneuser.getPhoneuserid());
		
		InfraredDeviceService ids = new InfraredDeviceService();
		List<InfraredDevice> dlst = ids.querybydeviceidandName(lst , name);
		
		if ( dlst != null && dlst.size() > 0 )
			return dlst.get(0);
		
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		List<Integer> iidl = zdss.querySharedInfrareddeviceid(phoneuser.getPhoneuserid());
		
		if ( iidl == null || iidl.size() == 0 )
			return null ;
		
		dlst = ids.querybyidandName(iidl , name);
		if ( dlst != null && dlst.size() > 0 )
			return dlst.get(0);
		
		return null ;
	}
	
	public int getResultCode() {
		return resultCode;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}
	
	static 
	{
		operationtypemap.put(String.format("%s_%s", IRemoteConstantDefine.DEVICE_TYPE_BINARY_SWITCH , "STATE_ON"), 	IRemoteConstantDefine.DEVICE_OPERATION_ON);
		operationtypemap.put(String.format("%s_%s", IRemoteConstantDefine.DEVICE_TYPE_BINARY_SWITCH , "STATE_OFF"), IRemoteConstantDefine.DEVICE_OPERATION_OFF);

		operationtypemap.put(String.format("%s_%s", IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_BINARY_SWITCH , "STATE_ON"), 	IRemoteConstantDefine.DEVICE_OPERATION_ON);
		operationtypemap.put(String.format("%s_%s", IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_BINARY_SWITCH , "STATE_OFF"), IRemoteConstantDefine.DEVICE_OPERATION_OFF);

		
		operationtypemap.put(String.format("%s_%s", IRemoteConstantDefine.DEVICE_TYPE_3_CHANNEL_BINARY_SWITCH , "STATE_ON"), 	IRemoteConstantDefine.DEVICE_OPERATION_ON);
		operationtypemap.put(String.format("%s_%s", IRemoteConstantDefine.DEVICE_TYPE_3_CHANNEL_BINARY_SWITCH , "STATE_OFF"), IRemoteConstantDefine.DEVICE_OPERATION_OFF);

		operationtypemap.put(String.format("%s_%s", IRemoteConstantDefine.DEVICE_TYPE_OUTLET , "STATE_ON"), 	IRemoteConstantDefine.DEVICE_OPERATION_ON);
		operationtypemap.put(String.format("%s_%s", IRemoteConstantDefine.DEVICE_TYPE_OUTLET , "STATE_OFF"), IRemoteConstantDefine.DEVICE_OPERATION_OFF);

		operationtypemap.put(String.format("%s_%s", IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK , "STATE_ON"), 	IRemoteConstantDefine.DEVICE_OPERATION_OPEN);
		operationtypemap.put(String.format("%s_%s", IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK , "STATE_OFF"), IRemoteConstantDefine.DEVICE_OPERATION_CLOSE);

		operationtypemap.put(String.format("%s_%s", IRemoteConstantDefine.DEVICE_TYPE_WALLREADER , "STATE_ON"), 	IRemoteConstantDefine.DEVICE_OPERATION_OPEN);
		operationtypemap.put(String.format("%s_%s", IRemoteConstantDefine.DEVICE_TYPE_WALLREADER , "STATE_OFF"), IRemoteConstantDefine.DEVICE_OPERATION_CLOSE);

		operationtypemap.put(String.format("%s_%s", IRemoteConstantDefine.DEVICE_TYPE_CURTAIN , "STATE_ON"), 	IRemoteConstantDefine.DEVICE_OPERATION_OPEN);
		operationtypemap.put(String.format("%s_%s", IRemoteConstantDefine.DEVICE_TYPE_CURTAIN , "STATE_OFF"), IRemoteConstantDefine.DEVICE_OPERATION_CLOSE);
		operationtypemap.put(String.format("%s_%s", IRemoteConstantDefine.DEVICE_TYPE_CURTAIN , "STATE_STOP"), IRemoteConstantDefine.DEVICE_OPERATION_STOP);

		operationtypemap.put(String.format("%s_%s", IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER , "STATE_ON"), 	IRemoteConstantDefine.DEVICE_OPERATION_OPEN);
		operationtypemap.put(String.format("%s_%s", IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER , "STATE_OFF"), IRemoteConstantDefine.DEVICE_OPERATION_CLOSE);

		try
		{
			InputStream io = OperateDevicebyGowildAction.class.getClassLoader().getResourceAsStream("resource/gowildtvstbcommand.properties");
			
			byte[] b = new byte[io.available()];
			io.read(b);
			String str = new String(b);
			JSONArray ja = JSON.parseArray(str);
			
			for ( int i = 0 ; i < ja.size() ; i ++ )
			{
				JSONObject json = ja.getJSONObject(i);
				tvstboperationindexmap.put(makeKey(json.getString("devicetype") , json), json.getInteger("opindex"));
			}
		}
		catch(Throwable t)
		{
			log.error(t.getMessage() , t);
		}
	}
	
}
