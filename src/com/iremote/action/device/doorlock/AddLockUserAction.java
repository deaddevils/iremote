package com.iremote.action.device.doorlock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.iremote.domain.DeviceCapability;
import com.iremote.service.DeviceCapabilityService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.GatewayHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.helper.TimeZoneHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid" , "deviceid" , "nuid"})
public class AddLockUserAction {

	private static Log log = LogFactory.getLog(AddLockUserAction.class);
			
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String deviceid ;
	private int nuid ;
	private int usertype ;
	private String username ;
	private String password;
	private String validfrom;
	private String validthrough;
	private List<String> alarmphone;
	private List<String> countrycode;
    private int doorlockuserid;	
    private int zwavedeviceid ;
    private ZWaveDevice zwavedevice;
	private String localtime;
	private Date dlocaltime;
	private PhoneUser phoneuser ;
	
	public String execute()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		
		if ( zwavedeviceid == 0 )	
			zwavedevice = zds.querybydeviceid(deviceid, nuid);
		else 
			zwavedevice = zds.query(zwavedeviceid);
		
		if ( zwavedevice == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.ERROR;
		}

		zwavedeviceid = zwavedevice.getZwavedeviceid();

		if ( PhoneUserHelper.checkPrivilege(phoneuser, zwavedevice) == false )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.ERROR;
		}
		
		if ( DeviceHelper.isZwavedevice(zwavedevice)
				&& !GatewayHelper.isOnline(zwavedevice.getDeviceid()))
			return "gatewayoffline";
		
		IDoorlockOperationProcessor setter = (IDoorlockOperationProcessor)DoorlockOperationStore.getInstance().get(String.valueOf(zwavedeviceid));
		
		if ( setter != null && !setter.isFinished() )
		{
			if ( log.isInfoEnabled())
				log.info(JSON.toJSONString(setter));
			resultCode = ErrorCodeDefine.NO_PRIVILEGE_3;
			return "devicebussing";
		}
		
		dlocaltime = Utils.parseTime(localtime);
		String tzid = GatewayHelper.getRemoteTimezoneId( zwavedevice.getDeviceid());
		
		if ( StringUtils.isNotBlank(tzid))
			dlocaltime = TimeZoneHelper.timezoneTranslate(new Date(), TimeZone.getDefault(),TimeZone.getTimeZone(tzid));
		
		if ( usertype == IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_PASSWORD ){
			if (isStandardZwaveDevice()) {
				setter = createStandardPassworddSetter(zwavedevice);
			} else {
				setter = createPasswordSetter(zwavedevice);
			}
		}
		else if ( usertype == IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_FINGERPRINT ){
			setter = createfingerprintSetter(zwavedevice);
		}
		else if ( usertype == IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_CARD){
			setter = createcardSetter(zwavedevice);
		}

		setter.init();
				
		if ( setter.getStatus() == DoorlockPasswordUserSetter.STATUS_OFFLINE)
			return setter.getMessage();
		else 
			setter.sendcommand();

		return setter.getMessage();
	}

	private ZwaveDoorlockPasswordSetter createStandardPassworddSetter(ZWaveDevice zd) {
		ZwaveDoorlockPasswordSetter setter = new ZwaveDoorlockPasswordSetter();
		setter.setDeviceid(zd.getDeviceid());
		setter.setNuid(zd.getNuid());
		setter.setPassword(password);
		setter.setUsername(username);
		setter.setZwavedeviceid(zd.getZwavedeviceid());
		return setter;
	}

	private boolean isStandardZwaveDevice() {
		DeviceCapabilityService dcs = new DeviceCapabilityService();
		DeviceCapability dc = dcs.query(zwavedevice, 13);
		return dc != null;
	}

	private DoorlockCardUserSetter createcardSetter(ZWaveDevice zd)
	{
		DoorlockCardUserSetter setter = new DoorlockCardUserSetter();
		setter.setDeviceid(zd.getDeviceid());
		setter.setNuid(zd.getNuid());
		setter.setUsername(username);
		setter.setZwavedeviceid(zd.getZwavedeviceid());
		setter.setUsertype(usertype);
		setter.setValidfrom(validfrom);
		setter.setValidthrough(validthrough);
		setter.setAlarmphone(alarmphone);
		setter.setCountrycode(countrycode);
		setter.setLocaltime(dlocaltime);
		return setter ;
	}
	
	private DoorlockFingerprintUserSetter createfingerprintSetter(ZWaveDevice zd)
	{
		DoorlockFingerprintUserSetter setter = new DoorlockFingerprintUserSetter();
		setter.setDeviceid(zd.getDeviceid());
		setter.setNuid(zd.getNuid());
		setter.setUsername(username);
		setter.setZwavedeviceid(zd.getZwavedeviceid());
		setter.setUsertype(usertype);
		setter.setValidfrom(validfrom);
		setter.setValidthrough(validthrough);
		setter.setAlarmphone(alarmphone);
		setter.setCountrycode(countrycode);
		setter.setLocaltime(dlocaltime);
		return setter ;
	}

	private DoorlockPasswordUserSetter createPasswordSetter(ZWaveDevice zd)
	{
		DoorlockPasswordUserSetter setter = new DoorlockPasswordUserSetter();
		setter.setDeviceid(zd.getDeviceid());
		setter.setNuid(zd.getNuid());
		setter.setPassword(password);
		setter.setUsername(username);
		setter.setZwavedeviceid(zd.getZwavedeviceid());
		setter.setUsertype(usertype);
		setter.setValidfrom(validfrom);
		setter.setValidthrough(validthrough);
		setter.setAlarmphone(alarmphone);
		setter.setCountrycode(countrycode);
		setter.setLocaltime(dlocaltime);
		return setter ;
	}
	
	public int getResultCode() {
		return resultCode;
	}

	public int getDoorlockuserid() {
		return doorlockuserid;
	}	

    public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public void setNuid(int nuid) {
		this.nuid = nuid;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public int getZwavedeviceid()
	{
		return zwavedeviceid;
	}

	public void setValidfrom(String validfrom) {
		this.validfrom = validfrom;
	}

	public void setValidthrough(String validthrough) {
		this.validthrough = validthrough;
	}

	public void setAlarmphone(List<String> alarmphone) {
		this.alarmphone = alarmphone;
	}

	public void setCountrycode(List<String> countrycode) {
		this.countrycode = countrycode;
	}

	public List<String> getAlarmphone() {
		List<String> list = new ArrayList<String>();
		if(alarmphone != null && alarmphone.size() > 0){
			for(int i = 0; i < alarmphone.size(); i++){
				if(countrycode.get(i) != null && countrycode.get(i).length() > 0)
					list.add(countrycode.get(i) + "-" + alarmphone.get(i));
			}
		}
		return alarmphone;
	}
	
	public boolean isZwavedevice()
	{
		return DeviceHelper.isZwavedevice(zwavedevice);
	}

	public void setLocaltime(String localtime)
	{
		this.localtime = localtime;
	}
}
