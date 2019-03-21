package com.iremote.action.device.doorlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.DoorlockAssociation;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.Doorlockalarmphone;
import com.iremote.domain.Notification;
import com.iremote.domain.Partition;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Scene;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.DoorlockAssociationService;
import com.iremote.service.DoorlockUserService;
import com.iremote.service.NotificationService;
import com.iremote.service.PartitionService;
import com.iremote.service.RemoteService;
import com.iremote.service.SceneService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class ShowDoorlockUserAction {
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private Integer doorlockuserid;
	private int zwavedeviceid;
	private DoorlockUser doorlocklser;
	private Doorlockalarmphone[] alarmphoneArray = new Doorlockalarmphone[5];
	private boolean isalarm = true;
	private boolean isscene = false;
	private boolean ispartition = false;
	private boolean ispartition2 = false;
	private Integer notificationid;
	private boolean toapp = false;
	private String validfrom;
	private String validthrough;
	private boolean showTime = false;
	private PhoneUser phoneuser ;
	private ZWaveDeviceService zds = new ZWaveDeviceService();
	private RemoteService rs = new RemoteService();
	private SceneService ss = new SceneService();
	private List<Scene> scenelist = new ArrayList<>();
	private PartitionService ps = new PartitionService();
	private List<Partition> partitionlist = new ArrayList<>();
	private List<Integer> choosescenelist = new ArrayList<>();
	private List<Integer> choosepartitionlist = new ArrayList<>();
	private List<Integer> choosepartitionlist2 = new ArrayList<>();
	private List<Integer> choosepartitiontype = new ArrayList<>();
	private DoorlockAssociationService das = new DoorlockAssociationService();
	private int platform;
	private boolean hasArmFunction = false;
	
	private static Log log = LogFactory.getLog(ShowDoorlockUserAction.class);

	public String execute()
	{
		if(phoneuser!=null){
			platform = phoneuser.getPlatform();
			hasArmFunction = PhoneUserHelper.hasArmFunction(phoneuser);
		}
		getDoorlockuserByNotificationid();
		getDoorlockuserById();
		if(doorlocklser == null){
			return Action.ERROR;
		}
		
		getSceneList(doorlocklser);
		getPartitionList(doorlocklser);
		
		getAlarmphone(doorlocklser);
		getTime(doorlocklser);
		
		return Action.SUCCESS;
	}
	
	public void getDoorlockuserByNotificationid(){
		if(notificationid == null){
			return;
		}
		NotificationService ns = new NotificationService();
		Notification n = ns.query(notificationid);
		toapp = true;
		if ( n == null || n.getZwavedeviceid() == null || n.getZwavedeviceid() == 0 )
		{
			return;
		}
		
		JSONArray ja = JSON.parseArray(n.getOrimessage());
		
		byte[] b = new byte[ja.size()] ;
		for ( int i = 0 ; i < b.length ; i ++ )
			b[i] = ja.getByteValue(i);

		int usercode;
		int usertype;
		if (!isStandardZwaveDoorLock(n.getZwavedeviceid())) {
			usercode = b[9] & 0xff;
			usertype = b[8] & 0xff;
		} else {
			usercode = b[3] & 0xff;
			usertype = 21;
		}

		DoorlockUserService dus = new DoorlockUserService();
		doorlocklser = dus.query(n.getZwavedeviceid(),usertype , usercode);
	}
	
	public void getDoorlockuserById(){
		if(doorlockuserid == null || doorlockuserid == 0){
			return;
		}
		DoorlockUserService dus = new DoorlockUserService();
		doorlocklser = dus.query(doorlockuserid);
	}
	
	private void getSceneList(DoorlockUser du){
		int zwaveid = du.getZwavedeviceid();
		ZWaveDevice zwave = zds.query(zwaveid);
		Integer phoneuserid = rs.queryOwnerId(zwave.getDeviceid());
		if(phoneuserid==null){
			return;
		}
		List<Integer> phoneuserids = PhoneUserHelper.querybySharetoPhoneuserid(phoneuserid);
		scenelist = ss.queryScenebyPhoneuserid(phoneuserids);
		List<DoorlockAssociation> dascenelist = das.querybyzwavedeviceidandusercodeandobjtype(zwaveid, doorlocklser.getUsercode(), 1);
		if(dascenelist!=null&&dascenelist.size()>0){
			for(DoorlockAssociation d:dascenelist){
				choosescenelist.add(d.getObjid());
			}
			isscene = true;
		}
	}
	
	private void getPartitionList(DoorlockUser du){
		int zwaveid = du.getZwavedeviceid();
		ZWaveDevice zwave = zds.query(zwaveid);
		Integer phoneuserid = rs.queryOwnerId(zwave.getDeviceid());
		if(phoneuserid==null){
			return;
		}
		List<Integer> phoneuserids = PhoneUserHelper.querybySharetoPhoneuserid(phoneuserid);
		partitionlist = ps.queryParitionsByPhoneuserid(phoneuserids);//zwave partition
		List<String> devices = PhoneUserHelper.queryDeviceidbySharetoPhoneuserid(phoneuserid);
		
		for ( String r : devices ){
			List<ZWaveDevice> zls = zds.querybydeviceid(r) ;
			for ( ZWaveDevice zd : zls ){
				if ( IRemoteConstantDefine.DEVICE_TYPE_DSC.equals(zd.getDevicetype())){
					List<Partition> partitions = zd.getPartitions();
					for(Partition p : partitions){
						if(!partitionlist.contains(p)){
							partitionlist.add(p);
						}
					}
				}
			}	
		}
		List<DoorlockAssociation> dapartitionlist = das.querybyzwavedeviceidandusercodeandobjtype(zwaveid, doorlocklser.getUsercode(), 2);
		if(dapartitionlist!=null&&dapartitionlist.size()>0){
			for(DoorlockAssociation d:dapartitionlist){
				choosepartitionlist.add(d.getObjid());
			}
			ispartition = true;
		}
		List<DoorlockAssociation> dapartitionlist2 = das.querybyzwavedeviceidandusercodeandobjtype(zwaveid, doorlocklser.getUsercode(), 3);
		if(dapartitionlist2!=null&&dapartitionlist2.size()>0){
			for(DoorlockAssociation d:dapartitionlist2){
				choosepartitionlist2.add(d.getObjid());
				choosepartitiontype.add(3);
			}
			ispartition2 = true;
		}
		List<DoorlockAssociation> dapartitionlist3 = das.querybyzwavedeviceidandusercodeandobjtype(zwaveid, doorlocklser.getUsercode(), 4);
		if(dapartitionlist3!=null&&dapartitionlist3.size()>0){
			for(DoorlockAssociation d:dapartitionlist3){
				choosepartitionlist2.add(d.getObjid());
				choosepartitiontype.add(4);
			}
			ispartition2 = true;
		}
	}
	
	public void getAlarmphone(DoorlockUser du){
		if(du == null)
			return;
		List<Doorlockalarmphone> list = du.getDoorlockalarmphone();
		if(list != null && list.size() > 0){
			if(list.size() <= 5){
				for(int i = 0; i < list.size(); i++){
					alarmphoneArray[i] = list.get(i);
				}
				for(int i = list.size(); i < 5; i++){
					alarmphoneArray[i] = new Doorlockalarmphone(doorlocklser, phoneuser.getCountrycode(), "");
				}
			}
		} else{
			isalarm = false;
			for(int i = 0; i < 5; i++){
				alarmphoneArray[i] = new Doorlockalarmphone(doorlocklser, phoneuser.getCountrycode(), "");
			}
		}
	}
	public void getTime(DoorlockUser du){
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zWaveDevice = zds.query(du.getZwavedeviceid());
		Set<Integer> sc = DeviceHelper.initDeviceCapability(zWaveDevice);
		
		if ( ( du.getUsertype() == IRemoteConstantDefine.DOOR_LOCK_USERTYPE_PASSWORD &&  sc.contains(IRemoteConstantDefine.DOOR_LOCK_CAPABILITY_PASSWORD_TIME) )
			|| ( du.getUsertype() == IRemoteConstantDefine.DOOR_LOCK_USERTYPE_FINGERPRINT && sc.contains(IRemoteConstantDefine.DOOR_LOCK_CAPABILITY_FINGERPRINT_TIME) )
			|| ( du.getUsertype() == IRemoteConstantDefine.DOOR_LOCK_USERTYPE_CARD && sc.contains(IRemoteConstantDefine.DOOR_LOCK_CAPABILITY_CARD_TIME)) )
			showTime = true;
		
		if(showTime)
		{
			try
			{
				if(du.getValidfrom() != null)
					validfrom =Utils.formatTime(du.getValidfrom());
				if(du.getValidthrough() != null )
					validthrough =Utils.formatTime(du.getValidthrough());
				if ( validfrom == null ||  validthrough == null )
					showTime = false ;
			}
			catch(Exception e)
			{
				log.error(e.getMessage(), e);
			}
		}
	}

	private boolean isStandardZwaveDoorLock(int zwavedeviceid) {
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zd = zds.query(zwavedeviceid);
		if (zd == null) {
			return false;
		}

		List<DeviceCapability> capabilityList = zd.getCapability();
		for (DeviceCapability deviceCapability : capabilityList) {
			if (deviceCapability.getCapabilitycode() == IRemoteConstantDefine.DEVICE_CAPABILITY_CODE_STANDARD_ZWAVE_DOOR_LOCK) {
				return true;
			}
		}
		return false;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public void setDoorlockuserid(Integer doorlockuserid)
	{
		this.doorlockuserid = doorlockuserid;
	}

	public DoorlockUser getDoorlocklser() {
		return doorlocklser;
	}

	public Doorlockalarmphone[] getAlarmphoneArray() {
		return alarmphoneArray;
	}

	public boolean getIsalarm() {
		return isalarm;
	}
	
	public void setNotificationid(Integer notificationid)
	{
		this.notificationid = notificationid;
	}

	public boolean getToapp() {
		return toapp;
	}

	public String getValidfrom() {
		return validfrom;
	}

	public String getValidthrough() {
		return validthrough;
	}

	public boolean getShowTime() {
		return showTime;
	}

	public int getZwavedeviceid() {
		return zwavedeviceid;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public Integer getNotificationid() {
		return notificationid;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public List<Scene> getScenelist() {
		return scenelist;
	}

	public List<Partition> getPartitionlist() {
		return partitionlist;
	}
	public boolean getIsscene() {
		return isscene;
	}
	public boolean getIspartition() {
		return ispartition;
	}

	public List<Integer> getChoosescenelist() {
		return choosescenelist;
	}

	public List<Integer> getChoosepartitionlist() {
		return choosepartitionlist;
	}

	public boolean getIspartition2() {
		return ispartition2;
	}

	public List<Integer> getChoosepartitionlist2() {
		return choosepartitionlist2;
	}

	public List<Integer> getChoosepartitiontype() {
		return choosepartitiontype;
	}
	public boolean getHasArmFunction() {
		return hasArmFunction;
	}
	public int getPlatform() {
		return platform;
	}

}
