package com.iremote.action.device.dsc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.DeviceInfoChange;
import com.iremote.common.jms.vo.InfoChange;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.DeviceExtendInfo;
import com.iremote.domain.Partition;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.service.DeviceCapabilityService;
import com.iremote.service.DeviceExtendInfoService;
import com.iremote.service.PartitionService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class AddDSCDeviceAction
{
	private int nuid =11401;
	private ZWaveDevice zwaveDevice ;
	private String name;
	private String password;
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String switchsNames;
	private String subdevicetype;
	private String belongsto;
	private String partitionNames;
	private List<ZWaveSubDevice> zWaveSubDevices;
	private List<Partition> partitions;
	private PhoneUser phoneuser ;
	private int zwavedeviceid ;
	private String deviceid;
	private int channelnumber;
	private int partitionnumber;
	private Partition partition;
	public String execute()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		zwaveDevice = zds.querybydeviceid(deviceid, nuid);
		
		/*if ( zwaveDevice != null ){
			this.resultCode = ErrorCodeDefine.DEVICE_HAS_EXIST;
			return Action.SUCCESS;
		}
		zwaveDevice = new ZWaveDevice();*/
		if(zwaveDevice==null){
			zwaveDevice = new ZWaveDevice();
		}
		zwaveDevice.setDeviceid(deviceid);
		zwaveDevice.setNuid(nuid);
		zwaveDevice.setName(name);
		zwaveDevice.setApplianceid(Utils.createtoken());
		zwaveDevice.setBattery(100);
		zwaveDevice.setDevicetype(IRemoteConstantDefine.DEVICE_TYPE_DSC);
		zwaveDevice.setStatus(0);
		zds.saveOrUpdate(zwaveDevice);
		zwavedeviceid = zwaveDevice.getZwavedeviceid();
		savePassword();
		savepartitions();
		addzwavetopartition();
		savesubdevices();
		savechannelnumber();
		savepartitionnumber();
		phoneuser.setLastupdatetime(new Date());
		PhoneUserHelper.sendInfoChangeMessage(phoneuser);
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED, new InfoChange(zwaveDevice.getDeviceid() , new Date() , 0) );
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_DEVICE_INFO_CHANGED, new DeviceInfoChange(zwavedeviceid,zwaveDevice.getDeviceid(),zwaveDevice.getName(),zwaveDevice.getDevicetype(),new Date(),0) );
		return Action.SUCCESS;
	}


	private void savechannelnumber() {
		if(channelnumber==0){
			return;
		}
		DeviceCapabilityService devicecap = new DeviceCapabilityService();
		DeviceCapability dc = new DeviceCapability();
		ZWaveDevice z = new ZWaveDevice();
		z.setZwavedeviceid(zwavedeviceid);
		DeviceCapability query3 = devicecap.query(z, 3);
		if(query3!=null){
			query3.setCapabilityvalue(String.valueOf(channelnumber));
		}else{
			dc.setZwavedevice(z);
			dc.setCapabilitycode(3);
			dc.setCapabilityvalue(String.valueOf(channelnumber));
			devicecap.save(dc);		
		}
	}
	private void savepartitionnumber() {
		if(partitionnumber==0){
			return;
		}
		DeviceCapabilityService devicecap = new DeviceCapabilityService();
		DeviceCapability dc = new DeviceCapability();
		ZWaveDevice z = new ZWaveDevice();
		z.setZwavedeviceid(zwavedeviceid);
		DeviceCapability query = devicecap.query(z, 2);
		if(query!=null){
			query.setCapabilityvalue(String.valueOf(partitionnumber));
		}else{
			dc.setZwavedevice(z);
			dc.setCapabilitycode(2);
			dc.setCapabilityvalue(String.valueOf(partitionnumber));
			devicecap.save(dc);		
		}
	}

	private void savesubdevices(){
		List<Integer> statuslist = new ArrayList<>();
		List<String> warningstatuseslist = new ArrayList<>();
		List<Integer> enablestatuslist = new ArrayList<>();
		zWaveSubDevices = zwaveDevice.getzWaveSubDevices();
		for(int i=0;i<zWaveSubDevices.size();i++){
			statuslist.add(zWaveSubDevices.get(i).getStatus());
			warningstatuseslist.add(zWaveSubDevices.get(i).getWarningstatuses());
			enablestatuslist.add(zWaveSubDevices.get(i).getEnablestatus());
		}
		if(switchsNames != null && switchsNames.length() > 0){
			String[] switchsName = switchsNames.split(",");
			String[] typearray = subdevicetype.split(",");
			String[] partitionarray = belongsto.split(",");
			zWaveSubDevices.clear();
			for(int i = 0; i < channelnumber; i++){	
				try{
					partition = partitions.get(Integer.parseInt(partitionarray[i].trim())-1);
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
				ZWaveSubDevice zms  ;
				if(statuslist.size()>i){
					zms = new ZWaveSubDevice(i+1,switchsName[i].trim() , zwaveDevice,typearray[i].trim(),partition,statuslist.get(i),warningstatuseslist.get(i),enablestatuslist.get(i));
				}else{
					zms = new ZWaveSubDevice(i+1,switchsName[i].trim() , zwaveDevice,typearray[i].trim(),partition);
				}
				zWaveSubDevices.add(zms);
			}
		}
	}

	private void savepartitions(){
		List<Integer> armstatuslist = new ArrayList<>() ;
		List<Integer> warningstatuslist = new ArrayList<>() ;
		List<Integer> statuslist = new ArrayList<>() ;
		partitions = zwaveDevice.getPartitions();
		for(int i = 0; i < partitions.size(); i++){
			armstatuslist.add(partitions.get(i).getArmstatus());
			warningstatuslist.add(partitions.get(i).getWarningstatus());
			statuslist.add(partitions.get(i).getStatus());
		}
		if(partitionNames != null && partitionNames.length() > 0){
			String[] partitionName = partitionNames.split(",");
				partitions.clear();
			for(int i = 0; i < partitionnumber; i++){
				Partition p ;
				if(statuslist.size()>i){
					p = new Partition(partitionName[i].trim(),zwaveDevice,i+1,statuslist.get(i),armstatuslist.get(i),warningstatuslist.get(i)); 
				}else{
					p = new Partition(partitionName[i].trim(),zwaveDevice,i+1,0,0,0); 
				}
				partitions.add(p);
			}
		}

	}
	
	private void addzwavetopartition() {
		PartitionService ps = new PartitionService();
		int pid =0;
		List<Partition> partitionlist = ps.querypartitionbyzwavedeviceid(zwavedeviceid);
		for(Partition p : partitionlist){
			if(p.getDscpartitionid()==1){
				pid = p.getPartitionid();
			}
		}
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> zwavelist = zds.querybydeviceid(deviceid);
		for(ZWaveDevice z:zwavelist){
			if(checkissensor(z.getZwavedeviceid())&&z.getPartitionid()==null){
				z.setPartitionid(pid);
			}
		}
	}
	
	private void savePassword()
	{
		if ( StringUtils.isBlank(password))
			return ;
	
		for ( ; password.length() < 6 ;)
			password += "0";
		
		DeviceExtendInfoService dis = new DeviceExtendInfoService();
		List<DeviceExtendInfo> dil = dis.querybyzwavedeviceid(zwavedeviceid);
		
		if ( dil == null || dil.size() == 0 )
		{
			DeviceExtendInfo dei = new DeviceExtendInfo();
			dei.setDevicepassword(password);
			dei.setZwavedeviceid(zwavedeviceid);
			
			dis.save(dei);
			return ;
		}
		
		for ( DeviceExtendInfo dei : dil)
			if ( StringUtils.isNotBlank(dei.getDevicepassword()))
			{
				dei.setDevicepassword(password);
				return ;
			}
		dil.get(0).setDevicepassword(password);
	}
	private boolean checkissensor(int zwavedeviceid){
    	List<String> sensortypelist = new ArrayList<String>();
    	sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_SMOKE);
    	sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_DOOR_SENSOR);
    	sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_WATER);
    	sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_MOVE);
    	sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_GAS);
    	sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_SOS_ALARM);
    	sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK);
    	sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_ALARM);
    	sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_WALLREADER);
    	sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_GARAGEDOOR);


    	ZWaveDeviceService zwds = new ZWaveDeviceService();
    	ZWaveDevice query2 = zwds.query(zwavedeviceid);
    	if(query2!=null && sensortypelist.contains(query2.getDevicetype())){
    		return true;
    	}
    	return false;
    }
	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSwitchsNames(String switchsNames) {
		this.switchsNames = switchsNames;
	}
	
	public void setSubdevicetype(String subdevicetype) {
		this.subdevicetype = subdevicetype;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setNuid(int nuid)
	{
		this.nuid = nuid;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

	public void setChannelnumber(int channelnumber) {
		this.channelnumber = channelnumber;
	}

	public void setPartitionNames(String partitionNames) {
		this.partitionNames = partitionNames;
	}

	public void setPartitionnumber(int partitionnumber) {
		this.partitionnumber = partitionnumber;
	}

	public void setBelongsto(String belongsto) {
		this.belongsto = belongsto;
	}
	
}
