package com.iremote.action.device.dsc;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.DeviceInfoChange;
import com.iremote.common.jms.vo.InfoChange;
import com.iremote.domain.Camera;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.DeviceExtendInfo;
import com.iremote.domain.Partition;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.RoomAppliance;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.service.CameraService;
import com.iremote.service.DeviceCapabilityService;
import com.iremote.service.DeviceExtendInfoService;
import com.iremote.service.DoorlockAssociationService;
import com.iremote.service.RoomApplianceService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class SetDSCDeviceNameAction {
	private int zwavedeviceid ;
	private ZWaveDevice zwaveDevice ;
	private String name;
	private String password;
	private List<DeviceCapability> capabilitys;
	private  boolean fahrenheit = false;
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String switchsNames;
	private int channelnumber;
	private List<ZWaveSubDevice> zWaveSubDevices;
	private DeviceCapabilityService dc = new DeviceCapabilityService();
	private PhoneUser phoneuser ;
	private int partitionnumber;
	private String partitionNames;
	private List<Partition> partitions;
	private String subdevicetype;
	private String belongsto;
	
	public String execute(){

		ZWaveDeviceService zds = new ZWaveDeviceService();
		zwaveDevice = zds.query(zwavedeviceid);
		if(zwaveDevice == null){
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		updateCapability();
		zwaveDevice.setName(name);
		RoomApplianceService roomApplianceService = new RoomApplianceService();
		List<RoomAppliance> roomAppliances = roomApplianceService.query(zwaveDevice.getDeviceid(), zwaveDevice.getApplianceid());
		if(roomAppliances != null && roomAppliances.size() > 0){
			RoomAppliance roomAppliance = roomAppliances.get(0);
			roomAppliance.setName(name);
			roomApplianceService.save(roomAppliance);
		}
		capabilitys = zwaveDevice.getCapability();
		

		fahrenheit();
		savePassword();
		partitions = zwaveDevice.getPartitions();
		int partitionsize = partitions.size();
		if(switchsNames != null && switchsNames.length() > 0){
			String[] switchsName = switchsNames.split(",");
			String[] typearray = subdevicetype.split(",");
			String[] partitionarray = belongsto.split(",");
			int length = channelnumber>0?channelnumber:switchsName.length;
			
			if(partitionNames != null && partitionNames.length() > 0){
				String[] partitionName = partitionNames.split(",");
				
				if(partitionnumber<partitionsize){//reduce
					for(int i = 0 ;i<partitionsize;i++){
						if(i<partitionnumber){
							partitions.get(i).setName(partitionName[i].trim());
						}else{
							Partition departition = partitions.get(i);
							ZWaveDeviceService dzds = new ZWaveDeviceService();
					    	CameraService dcs = new CameraService();
					    	List<ZWaveDevice> dezwavelist = dzds.querybypartitionid(departition.getPartitionid());
					    	List<Camera> decameralist = dcs.querybypartitionid(departition.getPartitionid());
					    	for(ZWaveDevice z:dezwavelist){
					    		z.setPartitionid(null);
					    	}
					    	for(Camera c:decameralist){
					    		c.setPartitionid(null);
					    		dcs.saveOrUpdate(c);
					    	}
					    	DoorlockAssociationService das = new DoorlockAssociationService();
					    	das.deletebyobjtypeandobjid(2, departition.getPartitionid());
					    	//zwaveDevice.getPartitions().remove(departition);
						}
					}
					for(int i=partitionsize-1;i>=partitionnumber;i--){
						zwaveDevice.getPartitions().get(i).setzWaveSubDevices(null);
						zwaveDevice.getPartitions().remove(i);
					}
				}else if(partitionnumber>partitionsize){//add partition
					for(int i = 0 ;i<partitionnumber;i++){
						if(i<partitionsize){
							partitions.get(i).setName(partitionName[i].trim());
						}else{
							Partition partition2 = new Partition(partitionName[i].trim(),zwaveDevice,i+1,0,0,0);
							partitions.add(partition2);
						}
					}
				}else{//equal
					for(int i = 0 ;i<partitionnumber;i++){
						partitions.get(i).setName(partitionName[i].trim());
					}
				}
			}
			
			//operation of zwavesubdevice
			zWaveSubDevices = zwaveDevice.getzWaveSubDevices();
			int dbsize = zWaveSubDevices.size();
			for(int i = 0; i < length; i++){//first channel disapper  -----------------------
				Partition par = zwaveDevice.getPartitions().get(Integer.parseInt(partitionarray[i].trim())-1);
				if(dbsize>i){
					ZWaveSubDevice asczwavesub = getASCZwavesubdevice(zWaveSubDevices,i);
					asczwavesub.setName(switchsName[i].trim());
					asczwavesub.setSubdevicetype(typearray[i].trim());
					asczwavesub.setPartition(par);
				}else{
					ZWaveSubDevice zms = new ZWaveSubDevice(i+1,switchsName[i].trim() , zwaveDevice,typearray[i].trim(),par);
					zWaveSubDevices.add(zms);
				}
			}
			
			if(dbsize>length){
				for(int i=dbsize-1;i>=length;i--){
					if(zWaveSubDevices.get(i).getPartition()!=null){
						zWaveSubDevices.get(i).setPartition(null);
					}
					zWaveSubDevices.remove(zWaveSubDevices.get(i));
				}
			}

		}
		
		phoneuser.setLastupdatetime(new Date());
		PhoneUserHelper.sendInfoChangeMessage(phoneuser);
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED, new InfoChange(zwaveDevice.getDeviceid() , new Date() , 0) );
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_DEVICE_INFO_CHANGED, new DeviceInfoChange(zwavedeviceid,zwaveDevice.getDeviceid(),zwaveDevice.getName(),zwaveDevice.getDevicetype(),new Date(),0) );
		
		return Action.SUCCESS;
	}

	private ZWaveSubDevice getASCZwavesubdevice(List<ZWaveSubDevice> zwavesub,int index){
		for(ZWaveSubDevice z:zwavesub){
			if(z.getChannelid()==(index+1)){
				return z;
			}
		}
		return null;
	}
	
	private void updateCapability() {
		if(zwaveDevice.getDevicetype().equals(IRemoteConstantDefine.DEVICE_TYPE_DSC)){
			DeviceCapability dca3 = dc.query(zwaveDevice,3);
			DeviceCapability dcb = dc.query(zwaveDevice,2);
			if(dca3!=null){
				dca3.setCapabilityvalue(String.valueOf(channelnumber));
				dc.update(dca3);	
			}else{
				DeviceCapability dec = new DeviceCapability();
				ZWaveDevice z = new ZWaveDevice();
				z.setZwavedeviceid(zwavedeviceid);
				dec.setZwavedevice(z);
				dec.setCapabilitycode(3);
				dec.setCapabilityvalue(String.valueOf(channelnumber));
				dc.save(dec);
			}
			if(dcb!=null){
				dcb.setCapabilityvalue(String.valueOf(partitionnumber));
				dc.update(dcb);	
			}else{
				DeviceCapability dec = new DeviceCapability();
				ZWaveDevice z = new ZWaveDevice();
				z.setZwavedeviceid(zwavedeviceid);
				dec.setZwavedevice(z);
				dec.setCapabilitycode(2);
				dec.setCapabilityvalue(String.valueOf(partitionnumber));
				dc.save(dec);		
			}
		}	
		
	}

	public void fahrenheit(){
		if(zwaveDevice.getDevicetype().equals(IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER)){
			DeviceCapability item = null;
			for(DeviceCapability  capability : capabilitys){
				if(capability.getCapabilitycode() == 1){
					item = capability;
				}
			}
			if(fahrenheit == true){
				if(item == null){
					item = new DeviceCapability(zwaveDevice, 1);
					capabilitys.add(item);
				}
			}else{
				if(item != null){
					capabilitys.remove(item);
				}
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
			dei.setZwavedeviceid(this.zwavedeviceid);
			
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
	
	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCapabilitys(List<DeviceCapability> capabilitys) {
		this.capabilitys = capabilitys;
	}

	public void setFahrenheit(boolean fahrenheit) {
		this.fahrenheit = fahrenheit;
	}

	public void setSwitchsNames(String switchsNames) {
		this.switchsNames = switchsNames;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
	public void setChannelnumber(int channelnumber) {
		this.channelnumber = channelnumber;
	}

	public void setPartitionnumber(int partitionnumber) {
		this.partitionnumber = partitionnumber;
	}

	public void setPartitionNames(String partitionNames) {
		this.partitionNames = partitionNames;
	}

	public void setSubdevicetype(String subdevicetype) {
		this.subdevicetype = subdevicetype;
	}

	public void setBelongsto(String belongsto) {
		this.belongsto = belongsto;
	}
	
	
}
