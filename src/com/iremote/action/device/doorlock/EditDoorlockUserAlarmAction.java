package com.iremote.action.device.doorlock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.DoorlockHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.encrypt.AES;
import com.iremote.domain.DoorlockAssociation;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.Doorlockalarmphone;
import com.iremote.domain.Notification;
import com.iremote.domain.ZWaveDevice;
import com.iremote.event.gateway.GatewayEventConsumerStore;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.service.DoorlockAssociationService;
import com.iremote.service.DoorlockUserService;
import com.iremote.service.NotificationService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class EditDoorlockUserAlarmAction implements IDoorlockOperationProcessor
{
	private static Log log = LogFactory.getLog(EditDoorlockUserAlarmAction.class);

	public static String[] STATUS = new String[]{"" , "init", "gatewayoffline","sendingpassword" , "editfailed" , "editsuccess" , "devicebussing"};
	public static int STATUS_INIT = 1 ;
	public static int STATUS_OFFLINE = 2 ;
	public static int STATUS_SENDING = 3 ;
	public static int STATUS_FAILED = 4 ;
	public static int STATUS_SUCCESS = 5 ;
	public static int STATUS_DEVICE_BUSSING = 6 ;
	
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int doorlockuserid;
	private int zwavedeviceid;  // for page jump in case doorlockuser not exists .
	private Integer notificationid ;
	private String username;
	private List<String> countrycode = new ArrayList<String>();
	private List<String> alarmphone = new ArrayList<String>();
	private String validfrom;
	private String validthrough;
	private DoorlockUser doorlockUser;
	private ZWaveDevice zwavedevice ;
	private Date dvalidfrom;
	private Date dvalidthrough;
	private int status ;
	private long expiretime;
	private String operation = "editdoorlockuser";
	private String scene;
	private String partition;
	private String password;
	private String armpartition;
	private List<String> armtype = new ArrayList<String>();
	
	public String execute()
	{
		if(StringUtils.isNotBlank(partition)&&StringUtils.isNotBlank(armpartition)){
			if(partition.contains(armpartition)||armpartition.contains(partition)){
				return Action.ERROR;
			}
		}
		if ( editDoorlock() == false )
			return Action.ERROR;
		
		editNotification();
		
		initZWaveDevice();
		if ( zwavedevice == null )
			return Action.ERROR;
		zwavedeviceid = zwavedevice.getZwavedeviceid();
		
		doorlockAssociation();
		
		if ( isValidTimeChanged() == false )
			return Action.SUCCESS;
		
		if ( !ConnectionManager.isOnline(zwavedevice.getDeviceid())) 
		{
			this.status = STATUS_OFFLINE;

			if ( DoorlockHelper.isWifiLock(zwavedevice) )
			{
				initValidtimeSetter();
				return this.getMessage();
			}
			else 
				return "gatewayoffline";
		}
		
		if ( editValidtime() == false )
		{
			if ( this.resultCode == ErrorCodeDefine.TIME_OUT)
			{
				this.status = STATUS_OFFLINE;

				if ( DoorlockHelper.isWifiLock(zwavedevice) )
				{
					initValidtimeSetter();
					return this.getMessage();
				}
				else 
					return "gatewayoffline";
			}
			return Action.ERROR;
		}
		
		
		return Action.SUCCESS;
	}
	
	private void doorlockAssociation(){
		DoorlockAssociationService das = new DoorlockAssociationService();
		int zwaveid = zwavedeviceid;
		das.deletebyzwavedeviceidandusercodeandobjtype(zwaveid, doorlockUser.getUsercode(), 1);
		das.deletebyzwavedeviceidandusercodeandobjtype(zwaveid, doorlockUser.getUsercode(), 2);
		das.deletebyzwavedeviceidandusercodeandobjtype(zwaveid, doorlockUser.getUsercode(), 3);
		das.deletebyzwavedeviceidandusercodeandobjtype(zwaveid, doorlockUser.getUsercode(), 4);
		
		if(!StringUtils.isEmpty(scene)){
			if(scene.contains(",")){
				String[] scenearr = scene.split(",");
				for(int i =0;i<scenearr.length;i++){
					DoorlockAssociation doorlockAssociation = new DoorlockAssociation(zwaveid,doorlockUser.getUsercode(),1,Integer.parseInt(scenearr[i].trim()));
					doorlockAssociation.setCreattime(new Date());
					das.saveOrUpdate(doorlockAssociation);
				}
			}else{
				DoorlockAssociation doorlockAssociation = new DoorlockAssociation(zwaveid,doorlockUser.getUsercode(),1,Integer.parseInt(scene.trim()));
				doorlockAssociation.setCreattime(new Date());
				das.saveOrUpdate(doorlockAssociation);
			}
		}
		if(!StringUtils.isEmpty(partition)){
			String[] passwordarr = password.split(",");
			if(partition.contains(",")){
				String [] partitionarr = partition.split(",");
				for(int i =0;i<partitionarr.length;i++){
					String[] pararr = partitionarr[i].split("#");
					String pass = passwordarr[Integer.parseInt(pararr[0].trim())].trim();
					DoorlockAssociation doorlockAssociation2 = new DoorlockAssociation(zwaveid,doorlockUser.getUsercode(),2,Integer.parseInt(pararr[1].trim()));
					doorlockAssociation2.setAppendmessage(AES.encrypt2Str(pass));
					doorlockAssociation2.setCreattime(new Date());
					das.saveOrUpdate(doorlockAssociation2);
				}
			}else{
				String[] pararr = partition.split("#");
				String pass = passwordarr[Integer.parseInt(pararr[0].trim())].trim();
				DoorlockAssociation doorlockAssociation2 = new DoorlockAssociation(zwaveid,doorlockUser.getUsercode(),2,Integer.parseInt(pararr[1].trim()));
				doorlockAssociation2.setAppendmessage(AES.encrypt2Str(pass));
				doorlockAssociation2.setCreattime(new Date());
				das.saveOrUpdate(doorlockAssociation2);
			}
		}
		if(!StringUtils.isEmpty(armpartition)){
			if(armpartition.contains(",")){
				String [] armpartitionarr = armpartition.split(",");
				for(int i =0;i<armpartitionarr.length;i++){
					String[] armpararr = armpartitionarr[i].split("#");
					DoorlockAssociation doorlockAssociation3 = new DoorlockAssociation(zwaveid,doorlockUser.getUsercode(),Integer.parseInt(armtype.get(Integer.parseInt(armpararr[0].trim()))),Integer.parseInt(armpararr[1].trim()));
					doorlockAssociation3.setCreattime(new Date());
					das.saveOrUpdate(doorlockAssociation3);
				}
			}else{
				String[] armpararr = armpartition.split("#");
				DoorlockAssociation doorlockAssociation3 = new DoorlockAssociation(zwaveid,doorlockUser.getUsercode(),Integer.parseInt(armtype.get(Integer.parseInt(armpararr[0].trim()))),Integer.parseInt(armpararr[1].trim()));
				doorlockAssociation3.setCreattime(new Date());
				das.saveOrUpdate(doorlockAssociation3);
			}
		}
	}
	
	private void initValidtimeSetter()
	{		
		IDoorlockOperationProcessor setter = (IDoorlockOperationProcessor)DoorlockOperationStore.getInstance().get(String.valueOf(zwavedevice.getZwavedeviceid()));
		
		if ( setter != null && !setter.isFinished() )
		{
			if ( log.isInfoEnabled())
				log.info(JSON.toJSONString(setter));
			resultCode = ErrorCodeDefine.NO_PRIVILEGE_3;
			this.status  = STATUS_DEVICE_BUSSING;
			return ;
		}
		
		expiretime = System.currentTimeMillis() + 30 * 1000 ;
		GatewayEventConsumerStore.getInstance().put(zwavedevice.getDeviceid(), this);
		DoorlockOperationStore.getInstance().put(String.valueOf(zwavedevice.getZwavedeviceid()), this);
		
	}
	
	private void initZWaveDevice()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService() ;
		zwavedevice = zds.query(doorlockUser.getZwavedeviceid());
	}
	
	private boolean isValidTimeChanged()
	{
		if ( StringUtils.isBlank(validfrom) && StringUtils.isBlank(validthrough))
			return false;
		
		Calendar cs = Calendar.getInstance();
		Calendar ce = Calendar.getInstance();
		
		if ( StringUtils.isBlank(validfrom))
			validfrom = "2001-01-01 00:00";
		dvalidfrom =Utils.parseMin(validfrom);
		cs.setTime(dvalidfrom);

		if ( StringUtils.isBlank(validthrough))
			validthrough = "2099-12-31 23:59";
		dvalidthrough =Utils.parseMin(validthrough);
		ce.setTime(dvalidthrough);
		
		if ( doorlockUser.getValidfrom() != null && doorlockUser.getValidfrom().getTime() == dvalidfrom.getTime()
			&& doorlockUser.getValidthrough() != null && doorlockUser.getValidthrough().getTime() == dvalidthrough.getTime())
			return false;
		validfrom = Utils.formatTime(cs.getTime());  //Change time format from yyyy-MM-dd HH:mm to yyyy-MM-dd HH:mm:ss
		validthrough = Utils.formatTime(ce.getTime()); 
		return true;
	}
	
	private boolean editValidtime()
	{
		this.resultCode = DoorlockHelper.sendTimeConfigure(zwavedevice.getDeviceid(), zwavedevice.getNuid(), (byte)doorlockUser.getUsertype(), (byte)doorlockUser.getUsercode(), validfrom, validthrough , true);
		
		boolean rst = (this.resultCode == ErrorCodeDefine.SUCCESS);
		
		if ( rst == true )
		{
			doorlockUser.setValidfrom(dvalidfrom);
			doorlockUser.setValidthrough(dvalidthrough);
			this.status = STATUS_SUCCESS;
		}
		else 
			this.status = STATUS_FAILED;
		return rst ;
	}
	
	public boolean editDoorlock(){
		DoorlockUserService dus = new DoorlockUserService();
		doorlockUser = dus.query(doorlockuserid);
		if(doorlockUser == null)
		{
			this.resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return false;
		}
		doorlockUser.setUsername(username);
		doorlockUser.getDoorlockalarmphone().clear();
		boolean isalarm = false;
		for(int i=0; i < alarmphone.size(); i++){
			if(alarmphone.get(i) != null && alarmphone.get(i).length() > 0){
				Doorlockalarmphone doorlockalarmphone = new Doorlockalarmphone(doorlockUser, countrycode.get(i), alarmphone.get(i));
				doorlockUser.getDoorlockalarmphone().add(doorlockalarmphone);
				isalarm = true;
			}
		}
		if(isalarm){
			doorlockUser.setAlarmtype(IRemoteConstantDefine.DOORLOCK_USER_TYPE_ALARM);
		}else{
			doorlockUser.setAlarmtype(IRemoteConstantDefine.DOORLOCK_USER_TYPE_ORDINARY);
		}
		//dus.save(doorlockUser);
		
		return true;
	}
	
	private void editNotification()
	{
		if ( notificationid == null )
			return ;
		NotificationService ns = new NotificationService();
		Notification n = ns.query(notificationid);
		
		if ( n == null )
			return; 
		n.setAppendmessage(username);
		
	}
	
	public int getResultCode()
	{
		return resultCode;
	}

	public void setDoorlockuserid(int doorlockuserid)
	{
		this.doorlockuserid = doorlockuserid;
	}

	public String getMessage() {
		return STATUS[this.getStatus()];
	}

	public void setCountrycode(List<String> countrycode) {
		this.countrycode = countrycode;
	}
	public void setAlarmphone(List<String> alarmphone) {
		this.alarmphone = alarmphone;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getZwavedeviceid() {
		return zwavedeviceid;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setNotificationid(Integer notificationid) {
		this.notificationid = notificationid;
	}

	public void setValidfrom(String validfrom) {
		this.validfrom = validfrom;
	}

	public void setValidthrough(String validthrough) {
		this.validthrough = validthrough;
	}

	@Override
	public long getExpireTime() 
	{
		return expiretime;
	}

	@Override
	public void onGatewayOnline() 
	{
		GatewayEventConsumerStore.getInstance().remove(zwavedevice.getDeviceid(), this);
		
		DoorlockUserService dus = new DoorlockUserService();
		doorlockUser = dus.query(doorlockuserid);  // Reload door lock user because this function runs in a thread that different than that runs execute function. 
		if(doorlockUser == null)
		{
			this.status = STATUS_FAILED;
			return ;
		}
		expiretime = System.currentTimeMillis() + 30 * 1000 ;
		this.status = STATUS_SENDING;
		editValidtime();
	}

	@Override
	public void onGatewayOffline() 
	{
		
	}

	@Override
	public void reportArrive(String deviceid, int nuid, byte[] report) 
	{
		
	}

	@Override
	public int getStatus() 
	{
		return status;
	}

	@Override
	public boolean isFinished() 
	{
		return ( status == STATUS_FAILED || status == STATUS_SUCCESS || status == STATUS_DEVICE_BUSSING );
	}

	@Override
	public void init() 
	{
		
	}

	@Override
	public void sendcommand() 
	{
		
	}

	public DoorlockUser getDoorlockUser() {
		return doorlockUser;
	}

	public String getOperation() {
		return operation;
	}
	
	public boolean isZwavedevice()
	{
		return DeviceHelper.isZwavedevice(zwavedevice);
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public void setPartition(String partition) {
		this.partition = partition;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setArmpartition(String armpartition) {
		this.armpartition = armpartition;
	}

	public void setArmtype(List<String> armtype) {
		this.armtype = armtype;
	}

	
	
}
