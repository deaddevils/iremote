package com.iremote.action.device;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import com.iremote.common.Utils;
import com.iremote.domain.*;
import com.iremote.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.iremote.action.helper.PhoneUserBlueToothHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.helper.TimeZoneHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.ServerRuntime;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.message.MessageManager;

public class SetDeiviceNamePageAction 
{
	protected Integer zwavedeviceid ;
	protected Integer infrareddeviceid ;
	protected Integer cameraid ;
	protected String deviceid ;
	protected ZWaveDevice zwaveDevice ;
	protected Camera camera ;
	protected InfraredDevice infrareddevice;
	protected List<DeviceCapability> capabilitys;
	protected boolean backlock = false;
	protected boolean fahrenheit = false;
	protected boolean isExit = false;
	protected int channelcount = 0 ;
	protected int partitioncount = 1 ;
	protected List<ZWaveSubDevice> zWaveSubDevices;
	protected List<Partition> partitions;
	protected ZWaveDeviceService zds = new ZWaveDeviceService();
	protected CameraService cs = new CameraService();
	protected InfraredDeviceService ids = new InfraredDeviceService();
	protected DeviceCapabilityService dc = new DeviceCapabilityService();
	protected List<String> channeltypes ;
	protected List<String> belongsto;
	protected int sensitivity ;
	protected int silentseconds;
	protected String sensitivitytime;
	protected String silentsecondstime;
	protected PhoneUser phoneuser ;
	private List<Pair<String , String>> timezonenames ;
	private String currenttimezone;
	protected int delay;
	protected int inhomenotalarm;
	protected int sound;
	protected int music;
	protected int time;
	private List<Integer> voltagechoose = new ArrayList<>();
	private List<Integer> frequency = new ArrayList<>();
	private List<Integer> sensitivityofefance = new ArrayList<>();
	private List<Integer> baojingmenxian= new ArrayList<>();
	private List<Integer> delaytimes = new ArrayList<>();
	private List<Integer> alarmtimes = new ArrayList<>();
	private List<Integer> highvoltage = new ArrayList<>();
	private List<Integer> lowvoltage = new ArrayList<>();
	private int platform;
	private boolean hasArmFunction = false;
	private boolean isOnlyBlueToothLock = false;
	private String addrinfo;
	private String areaid;
	
	
	public String execute()
	{
		if(phoneuser!=null){
			platform = phoneuser.getPlatform();
			hasArmFunction = PhoneUserHelper.hasArmFunction(phoneuser);
		}
		if ( zwavedeviceid != null && zwavedeviceid != 0 )
			return processZwaveDevice();
		else if ( cameraid != null && cameraid != 0 )
			return processCamera();
		else 
			return processInfraredDevice();
	}

	protected String processInfraredDevice()
	{
		InfraredDeviceService ids = new InfraredDeviceService();
		infrareddevice = ids.query(infrareddeviceid);
		return "infrareddevice";
	}

	protected String processCamera()
	{
		camera = cs.query(cameraid);
		DeviceCapability cameracapability = dc.queryByCamera(camera, IRemoteConstantDefine.DEVICE_CAPABILITY_ALARM_WHEN_HOME_ARMED);
		DeviceCapability queryByCamera = dc.queryByCamera(camera, IRemoteConstantDefine.DEVICE_CAPABILITY_DELAY_TIME_CAPABILITCODE);
		if(cameracapability!=null){
			inhomenotalarm = 1;
		}
		if(queryByCamera!=null){
			delay = Integer.parseInt(queryByCamera.getCapabilityvalue());
		}
		return "camera";
	}
	
	protected String processZwaveDevice()
	{
		
		
		zwaveDevice = zds.query(zwavedeviceid);
		
		if(zwaveDevice == null)
		{
			return "";
		}
		isOnlyBlueToothLock = PhoneUserBlueToothHelper.isOnlyBlueToothLock(zwaveDevice);
		capabilitys = zwaveDevice.getCapability();
		zWaveSubDevices = zwaveDevice.getzWaveSubDevices();

		if (IRemoteConstantDefine.DEVICE_TYPE_PASS_THROUGH_DEVICE.equals(zwaveDevice.getDevicetype())
				|| IRemoteConstantDefine.DEVICE_TYPE_INFRARED_PASS_THROUGH_DEVICE.equals(zwaveDevice.getDevicetype())){
			return "passthroughdevice";
		}

		if ( Utils.DEVICE_TYPE_CHANNEL_MAP.containsKey(zwaveDevice.getDevicetype()) )
		{		
			channelcount = Utils.DEVICE_TYPE_CHANNEL_MAP.get(zwaveDevice.getDevicetype());
			if(IRemoteConstantDefine.DEVICE_TYPE_DSC.equals(zwaveDevice.getDevicetype())){
				DeviceCapability dca1 = dc.query(zwaveDevice,1);
				DeviceCapability dca3 = dc.query(zwaveDevice,3);
				DeviceCapability dcb = dc.query(zwaveDevice,2);
				if(dca3!=null&&dcb!=null){
					channelcount=Integer.valueOf(dca3.getCapabilityvalue());
					partitioncount=Integer.valueOf(dcb.getCapabilityvalue());
				}else if(dca1!=null&&dcb!=null){
					channelcount=Integer.valueOf(dca1.getCapabilityvalue());
					partitioncount=Integer.valueOf(dcb.getCapabilityvalue());
				}else{
					ZWaveSubDeviceService zsds = new ZWaveSubDeviceService();
					PartitionService ps = new PartitionService();
					List<ZWaveSubDevice> sublist = zsds.querychannelbydeviceid(deviceid);
					List<Partition> plist = ps.querypartitionbyzwavedeviceid(zwavedeviceid);
					if(sublist!=null&&sublist.size()!=0){
						List<ZWaveSubDevice> channellist = new ArrayList<>();
						for(ZWaveSubDevice z : sublist){
							if(z.getZwavedevice().getDeviceid()==sublist.get(0).getZwavedevice().getDeviceid()){
								channellist.add(z);
							}
						}
						channelcount = channellist.size();
						channelcount = channelcount%8==0?channelcount:(channelcount/8+1)*8;
					}
					if(plist!=null&&plist.size()!=0){
						partitioncount = plist.size();
					}
				}
				partitions = zwaveDevice.getPartitions();
				List<ZWaveSubDevice> list = new ArrayList<ZWaveSubDevice>();
				List<Partition> parlist = new ArrayList<Partition>();
				channeltypes = new ArrayList<>();
				belongsto = new ArrayList<>();
				for ( int i = 0 ; i < 64 ; i ++ ){
					ZWaveSubDevice zsd = this.findZWaveSubDevice(i+1);
					if ( zsd == null ){
						String name = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_TYPE_DSC_CHANNEL ,9,phoneuser.getLanguage());
						ZWaveSubDevice zms = new ZWaveSubDevice(i + 1, String.format("%s%d", name , (i + 1)), zwaveDevice);
						list.add(zms);
					}
					else if ( StringUtils.isBlank(zsd.getName())){
						zsd.setName(MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_TYPE_DSC_CHANNEL ,9,phoneuser.getLanguage()) + (i+1));
						list.add(zsd);
					}else{
						list.add(zsd);
					}
					channeltypes.add(list.get(i).getSubdevicetype());
					if(list.get(i).getPartition()==null){
						belongsto.add("1");
					}else{
						belongsto.add(list.get(i).getPartition().getDscpartitionid()+"");
					}
				}
				for ( int i = 0 ; i < 8 ; i ++ ){
					Partition pt = this.findPartition(i+1);
					if ( pt == null ){
						String name = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_TYPE_DSC_PARTITION ,9,phoneuser.getLanguage());
						Partition p = new Partition(String.format("%s%d", name , (i + 1)),i+1);
						parlist.add(p);
					}
					else if ( StringUtils.isBlank(pt.getName())){
						pt.setName(MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_TYPE_DSC_PARTITION ,9,phoneuser.getLanguage()) + (i+1));
						parlist.add(pt);
					}else{
						parlist.add(pt);
					}
				}
				partitions = parlist;
				zWaveSubDevices = list;
			}else{
				if(zWaveSubDevices == null)
					zWaveSubDevices = new ArrayList<ZWaveSubDevice>();
				
				for ( int i = 0 ; i < channelcount ; i ++ )
				{
					ZWaveSubDevice zsd = this.findZWaveSubDevice(i+1);
					if ( zsd == null )
					{
						String name = MessageManager.getmessage(IRemoteConstantDefine.MESSGAE_TYPE_CHANNEL ,phoneuser.getPlatform(),phoneuser.getLanguage());
						ZWaveSubDevice zms = new ZWaveSubDevice(i + 1, String.format("%s%d", name , (i + 1)), zwaveDevice);
						zWaveSubDevices.add(zms);
					}
					else if ( StringUtils.isBlank(zsd.getName()))
						zsd.setName(MessageManager.getmessage(IRemoteConstantDefine.MESSGAE_TYPE_CHANNEL ,phoneuser.getPlatform(),phoneuser.getLanguage()) + (i+1));
				}
			}
		}
		
		for(DeviceCapability capability : capabilitys){
			if(capability.getCapabilitycode() == IRemoteConstantDefine.BACKLOCK 
					&& zwaveDevice.getDevicetype() == IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER){
				fahrenheit = true;
			}else if(capability.getCapabilitycode() == IRemoteConstantDefine.BACKLOCK 
					&& IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(zwaveDevice.getDevicetype())){
				backlock = true;
			}
			if(capability.getCapabilitycode() == 1){
				isExit = true;
			}
			if(capability.getCapabilitycode()==IRemoteConstantDefine.DEVICE_CAPABILITY_DELAY_TIME_CAPABILITCODE){
				delay = Integer.parseInt(capability.getCapabilityvalue());
			}
			if(capability.getCapabilitycode()==IRemoteConstantDefine.DEVICE_CAPABILITY_ALARM_WHEN_HOME_ARMED){
				inhomenotalarm = 1;
			}
			if (capability.getCapabilitycode() == IRemoteConstantDefine.DEVICE_CAPABILITY_DRESS_HELPER_AREA_ID) {
				initDressHelperLocation(capability);
			}
		}
		if ( IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(zwaveDevice.getDevicetype()))
		{
			initTimeZones();
			initCurrentTimezone();
		}
		
		if ( IRemoteConstantDefine.DEVICE_TYPE_ELECTRIC_FENCE.equals(zwaveDevice.getDevicetype())){
			initEfance1();
			return "efance1";
		}
		if ( IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE.equals(zwaveDevice.getDevicetype())){
			initEfance2();
			return "efance2";
		}
		if ( IRemoteConstantDefine.DEVICE_TYPE_DSC.equals(zwaveDevice.getDevicetype()))
			return "dsc";
		Set<String> neoproductorset = ServerRuntime.getInstance().getNeoproductorset();
		Set<String> leedarsonproductorset = ServerRuntime.getInstance().getLeedarsonproductorset();
		Set<String> sirenproductorset = ServerRuntime.getInstance().getSirenproductorset();
		if(IRemoteConstantDefine.DEVICE_TYPE_MOVE.equals(zwaveDevice.getDevicetype())&&!StringUtils.isEmpty(zwaveDevice.getProductor())&&neoproductorset.contains(zwaveDevice.getProductor())){			
			initNeo();
			return "neo";
		}
		if(IRemoteConstantDefine.DEVICE_TYPE_MOVE.equals(zwaveDevice.getDevicetype())&&!StringUtils.isEmpty(zwaveDevice.getProductor())&&leedarsonproductorset.contains(zwaveDevice.getProductor())){			
			initLeedarson();
			return "leedarson";
		}
		if(IRemoteConstantDefine.DEVICE_TYPE_ALARM.equals(zwaveDevice.getDevicetype())&&!StringUtils.isEmpty(zwaveDevice.getProductor())&&sirenproductorset.contains(zwaveDevice.getProductor())){
			initSiren();
			return "siren";
		}
		return "zwavedevice";
	}

	private void initDressHelperLocation(DeviceCapability capability) {
		if (!IRemoteConstantDefine.DEVICE_TYPE_DRESS_HELPER.equals(zwaveDevice.getDevicetype())
				||StringUtils.isBlank(capability.getCapabilityvalue())) {
			return;
		}
		CountyService countyService = new CountyService();
		County county = countyService.queryByAliLocationKey(capability.getCapabilityvalue());
		areaid = capability.getCapabilityvalue();
		if (county == null) {
			return;
		}
		CityService cityService = new CityService();
		City city = cityService.queryByCityid(county.getCityid());
		if (city == null) {
			return;
		}
		ProvinceService provinceService = new ProvinceService();
		Province province = provinceService.queryByProvinceid(city.getProvinceid());
		addrinfo = String.format("%s-%s-%s", province.getName(), city.getName(), county.getName());
	}

	private void initEfance1(){
		String statuses = zwaveDevice.getStatuses();
		String[] split = {"0"};
		if(statuses!=null){
			statuses = statuses.substring(1,statuses.length()-1);
			split = statuses.split(",");
		}
		if(split.length>1&&!"null".equals(split[1])){
			voltagechoose.add(Integer.parseInt(split[1]));
		}else{
			voltagechoose.add(0);
		}
		if(split.length>2&&!"null".equals(split[2])){
			frequency.add(Integer.parseInt(split[2]));
		}else{
			frequency.add(-1);
		}
		if(split.length>3&&!"null".equals(split[3])){
			sensitivityofefance.add(Integer.parseInt(split[3]));
		}else{
			sensitivityofefance.add(-1);
		}
		if(split.length>4&&!"null".equals(split[4])){
			baojingmenxian.add(Integer.parseInt(split[4]));
		}else{
			baojingmenxian.add(-1);
		}
		if(split.length>5&&!"null".equals(split[5])){
			delaytimes.add(Integer.parseInt(split[5]));
		}else{
			delaytimes.add(-1);
		}
		if(split.length>6&&!"null".equals(split[6])){
			alarmtimes.add(Integer.parseInt(split[6]));
		}else{
			alarmtimes.add(-1);
		}
		if(split.length>7&&!"null".equals(split[7])){
			highvoltage.add(Integer.parseInt(split[7]));
		}else{
			highvoltage.add(-1);
		}
		if(split.length>8&&!"null".equals(split[8])){
			lowvoltage.add(Integer.parseInt(split[8]));
		}else{
			lowvoltage.add(-1);
		}
	}
	private void initEfance2(){
		for(ZWaveSubDevice sub : zWaveSubDevices){
			String statuses = sub.getStatuses();
			String[] split = {"0"};
			if(statuses!=null){
				statuses = statuses.substring(1,statuses.length()-1);
				split = statuses.split(",");
			}
			if(split.length>1&&!"null".equals(split[1])){
				voltagechoose.add(Integer.parseInt(split[1]));
			}else{
				voltagechoose.add(0);
			}
			if(split.length>2&&!"null".equals(split[2])){
				frequency.add(Integer.parseInt(split[2]));
			}else{
				frequency.add(-1);
			}
			if(split.length>3&&!"null".equals(split[3])){
				sensitivityofefance.add(Integer.parseInt(split[3]));
			}else{
				sensitivityofefance.add(-1);
			}
			if(split.length>4&&!"null".equals(split[4])){
				baojingmenxian.add(Integer.parseInt(split[4]));
			}else{
				baojingmenxian.add(-1);
			}
			if(split.length>5&&!"null".equals(split[5])){
				delaytimes.add(Integer.parseInt(split[5]));
			}else{
				delaytimes.add(-1);
			}
			if(split.length>6&&!"null".equals(split[6])){
				alarmtimes.add(Integer.parseInt(split[6]));
			}else{
				alarmtimes.add(-1);
			}
			if(split.length>7&&!"null".equals(split[7])){
				highvoltage.add(Integer.parseInt(split[7]));
			}else{
				highvoltage.add(-1);
			}
			if(split.length>8&&!"null".equals(split[8])){
				lowvoltage.add(Integer.parseInt(split[8]));
			}else{
				lowvoltage.add(-1);
			}
		}
	}
	private void initNeo(){
		String statuses = zwaveDevice.getStatuses();
		if(statuses!=null&&statuses.length()>2){
			statuses = statuses.substring(1,statuses.length()-1);
			String[] split = statuses.split(",");
			if(split.length>5&&!"null".equals(split[5])){
				sensitivity = Integer.parseInt(split[5]);
			}
			if(split.length>6&&!"null".equals(split[6])){
				silentseconds = Integer.parseInt(split[6]);
			}
		}
		DeviceOperationStepsService doss = new DeviceOperationStepsService();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DeviceOperationSteps sen = doss.querybyzwavedeviceidandtype(zwavedeviceid, DeviceOperationType.getNEOSensitivity);
		DeviceOperationSteps sil = doss.querybyzwavedeviceidandtype(zwavedeviceid, DeviceOperationType.getNEOSilentseconds);
		if(sen!=null){
			sensitivitytime = sdf.format(sen.getStarttime());
		}
		if(sil!=null){
			silentsecondstime = sdf.format(sil.getStarttime());
		}
	}
	private void initLeedarson(){
		String statuses = zwaveDevice.getStatuses();
		if(statuses!=null&&statuses.length()>2){
			statuses = statuses.substring(1,statuses.length()-1);
			String[] split = statuses.split(",");
			if(split.length>5&&!"null".equals(split[5])){
				sensitivity = Integer.parseInt(split[5]);
			}
			if(split.length>6&&!"null".equals(split[6])){
				silentseconds = Integer.parseInt(split[6]);
			}
		}
		DeviceOperationStepsService doss = new DeviceOperationStepsService();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DeviceOperationSteps sen = doss.querybyzwavedeviceidandtype(zwavedeviceid, DeviceOperationType.getLeedarsonSensitivity);
		DeviceOperationSteps sil = doss.querybyzwavedeviceidandtype(zwavedeviceid, DeviceOperationType.getLeedarsonSilentseconds);
		if(sen!=null){
			sensitivitytime = sdf.format(sen.getStarttime());
		}
		if(sil!=null){
			silentsecondstime = sdf.format(sil.getStarttime());
		}
	}
	
	private void initSiren(){
		String statuses = zwaveDevice.getStatuses();
		if(statuses!=null&&statuses.length()>2){
			statuses = statuses.substring(1,statuses.length()-1);
			String[] split = statuses.split(",");
			if(split.length>5&&!"null".equals(split[5])){//sound
				sound = Integer.parseInt(split[5]);
			}
			if(split.length>6&&!"null".equals(split[6])){//music
				music = Integer.parseInt(split[6]);
			}
			if(split.length>7&&!"null".equals(split[7])){//time
				time = Integer.parseInt(split[7]);
				if(time==0){
					time=4;
				}
			}
		}
	}
	private void initTimeZones()
	{
		timezonenames = new ArrayList<Pair<String , String>>();
		for ( String tzid : TimeZone.getAvailableIDs())
		{
			TimeZone tz = TimeZone.getTimeZone(tzid);
			timezonenames.add(Pair.of(TimeZoneHelper.createtimezonelabel(tz, phoneuser) , tz.getID()));
		}
		Collections.sort(timezonenames);
	}
	private void initCurrentTimezone()
	{
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(zwaveDevice.getDeviceid());
		if ( r.getTimezone() != null && StringUtils.isNotBlank(r.getTimezone().getId()) )
			this.currenttimezone = r.getTimezone().getId();
		else 
			this.currenttimezone = TimeZone.getDefault().getID();
	}
	
	public List<Pair<String , String>> getTimezonenames() {
		return timezonenames;
	}

	private ZWaveSubDevice findZWaveSubDevice(int channelid)
	{
		for ( ZWaveSubDevice zsd : zWaveSubDevices)
			if ( zsd.getChannelid() == channelid)
				return zsd ;
		return null ;
	}
	private Partition findPartition(int dscpartitionid){
		for ( Partition p : partitions)
			if ( p.getDscpartitionid() == dscpartitionid)
				return p  ;
		return null ;
	}

	public ZWaveDevice getZwaveDevice() {
		return zwaveDevice;
	}
	
	public boolean getIsExit() {
		return isExit;
	}
	public List<DeviceCapability> getCapabilitys() {
		return capabilitys;
	}

	public boolean isBacklock() {
		return backlock;
	}

	public boolean isFahrenheit() {
		return fahrenheit;
	}

	public void setZwavedeviceid(Integer zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public Integer getZwavedeviceid() {
		return zwavedeviceid;
	}

	public List<ZWaveSubDevice> getzWaveSubDevices() {
		return zWaveSubDevices;
	}

	public void setzWaveSubDevices(List<ZWaveSubDevice> zWaveSubDevices) {
		this.zWaveSubDevices = zWaveSubDevices;
	}

	public List<Partition> getPartitions() {
		return partitions;
	}

	public void setPartitions(List<Partition> partitions) {
		this.partitions = partitions;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}
	
	public Integer getCameraid()
	{
		return cameraid;
	}

	public void setCameraid(Integer cameraid)
	{
		this.cameraid = cameraid;
	}

	public Camera getCamera()
	{
		return camera;
	}

	public int getChannelcount()
	{
		return channelcount;
	}

	public Integer getInfrareddeviceid()
	{
		return infrareddeviceid;
	}

	public void setInfrareddeviceid(Integer infrareddeviceid)
	{
		this.infrareddeviceid = infrareddeviceid;
	}

	public InfraredDevice getInfrareddevice()
	{
		return infrareddevice;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public int getPartitioncount() {
		return partitioncount;
	}

	public List<String> getChanneltypes() {
		return channeltypes;
	}

	public int getSensitivity() {
		return sensitivity;
	}

	public int getSilentseconds() {
		return silentseconds;
	}

	public String getSensitivitytime() {
		return sensitivitytime;
	}

	public String getSilentsecondstime() {
		return silentsecondstime;
	}
	public List<String> getBelongsto() {
		return belongsto;
	}
	public String getCurrenttimezone() {
		return currenttimezone;
	}
	public int getDelay() {
		return delay;
	}
	public int getInhomenotalarm() {
		return inhomenotalarm;
	}
	public int getSound() {
		return sound;
	}
	public int getMusic() {
		return music;
	}
	public int getTime() {
		return time;
	}

	public List<Integer> getVoltagechoose() {
		return voltagechoose;
	}

	public List<Integer> getFrequency() {
		return frequency;
	}

	public List<Integer> getSensitivityofefance() {
		return sensitivityofefance;
	}

	public List<Integer> getBaojingmenxian() {
		return baojingmenxian;
	}

	public List<Integer> getDelaytimes() {
		return delaytimes;
	}

	public List<Integer> getAlarmtimes() {
		return alarmtimes;
	}

	public List<Integer> getHighvoltage() {
		return highvoltage;
	}

	public List<Integer> getLowvoltage() {
		return lowvoltage;
	}

	public int getPlatform() {
		return platform;
	}

	public boolean getHasArmFunction() {
		return hasArmFunction;
	}

	public boolean getIsOnlyBlueToothLock() {
		return isOnlyBlueToothLock;
	}

	public String getAddrinfo() {
		return addrinfo;
	}

	public String getAreaid() {
		return areaid;
	}
}
