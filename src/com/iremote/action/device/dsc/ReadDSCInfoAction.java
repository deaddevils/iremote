package com.iremote.action.device.dsc;

import java.util.ArrayList;
import java.util.List;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.message.MessageManager;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.Partition;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.service.DeviceCapabilityService;
import com.iremote.service.PartitionService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZWaveSubDeviceService;

public class ReadDSCInfoAction
{
	private ZWaveDevice zwaveDevice ;
	private String deviceid ;
	private List<Integer> nuids = new ArrayList<Integer>() ;
	private PhoneUser phoneuser ;
	private List<ZWaveSubDevice> zWaveSubDevices;
	private List<Partition> partitions;;
	private int channelcount = 8;
	private int partitioncount = 1;
	private int zwavedeviceid;
	private PartitionService ps = new PartitionService();
	private DeviceCapabilityService dc = new DeviceCapabilityService();
	private int platform;
	public String execute()
	{
		for ( int i = 11401 ; i <= 11408 ; i ++ )
			nuids.add(i);
		platform = phoneuser.getPlatform();
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> lst = zds.querybydeviceid(deviceid);
		List<ZWaveDevice> zwaveDevicelist = zds.querybydeviceidtype(deviceid,"47");
		zwaveDevice = zwaveDevicelist.get(0);
		if(zwaveDevice!=null){
			DeviceCapability dca1 = dc.query(zwaveDevice,1);
			DeviceCapability dca3 = dc.query(zwaveDevice,3);
			DeviceCapability dcb = dc.query(zwaveDevice,2);
			if(dca3!=null){
				channelcount=Integer.valueOf(dca3.getCapabilityvalue());
			}else if(dca1!=null){
				channelcount=Integer.valueOf(dca1.getCapabilityvalue());
			}
			if(dcb!=null){
				partitioncount=Integer.valueOf(dcb.getCapabilityvalue());
			}
		}else{
			ZWaveSubDeviceService zsds = new ZWaveSubDeviceService();
			List<ZWaveSubDevice> sublist = zsds.querychannelbydeviceid(deviceid);
			if(sublist!=null&&sublist.size()!=0){
				List<ZWaveSubDevice> channellist = new ArrayList<>();
				for(ZWaveSubDevice z : sublist){
					if(z.getZwavedevice().getDeviceid()==sublist.get(0).getZwavedevice().getDeviceid()){
						channellist.add(z);
					}
				}
				zwavedeviceid = channellist.get(0).getZwavedevice().getZwavedeviceid();
				channelcount = channellist.size();
				channelcount = channelcount%8==0?channelcount:(channelcount/8+1)*8;
			}
			List<Partition> plist = ps.querypartitionbyzwavedeviceid(zwavedeviceid);
			if(plist!=null&&plist.size()!=0){
				partitioncount = plist.size();
			}
		}
		for ( ZWaveDevice zd : lst )
			nuids.remove(new Integer(zd.getNuid()));
		
		zWaveSubDevices = new ArrayList<ZWaveSubDevice>();
		for ( int i = 0 ; i < 64 ; i ++ )
		{
			String name = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_TYPE_DSC_CHANNEL ,9,phoneuser.getLanguage());
			ZWaveSubDevice zms = new ZWaveSubDevice(i + 1, String.format("%s%d", name , (i + 1)), null);
			zWaveSubDevices.add(zms);
		}
		partitions = new ArrayList<Partition>();
		for ( int i = 0 ; i < 8  ; i ++ )
		{
			String name = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_TYPE_DSC_PARTITION ,9,phoneuser.getLanguage());
			Partition p = new Partition(String.format("%s%d", name , (i + 1)),i + 1);
			partitions.add(p);
		}
		
		return "dsc";
	}

	public String getDeviceid()
	{
		return deviceid;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

	public List<Integer> getZwavedeviceids()
	{
		return nuids;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public List<ZWaveSubDevice> getzWaveSubDevices()
	{
		return zWaveSubDevices;
	}

	public List<Partition> getPartitions() {
		return partitions;
	}

	public List<Integer> getNuids()
	{
		return nuids;
	}

	public int getChannelcount() {
		return channelcount;
	}

	public int getPartitioncount() {
		return partitioncount;
	}

	public ZWaveDevice getZwaveDevice() {
		return zwaveDevice;
	}

	public int getPlatform() {
		return platform;
	}


	
	
}
