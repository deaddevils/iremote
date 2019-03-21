package com.iremote.action.partition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.Partition;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.PartitionService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.action.helper.PhoneUserHelper;

public class PartitionHelper {
	
	public static boolean checkissensor(int zwavedeviceid){
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
	
	public static boolean checkisdsc(int zwavedeviceid){
    	ZWaveDeviceService zwds = new ZWaveDeviceService();
    	ZWaveDevice query2 = zwds.query(zwavedeviceid);
    	if(query2!=null && IRemoteConstantDefine.DEVICE_TYPE_DSC.equals(query2.getDevicetype())){
    		return true;
    	}
    	return false;
    }

	public static Set<Partition> getAllPartitionByPhoneuserid(int phoneuserid){
		Set<Partition> partitionset = new HashSet<>();
		PartitionService ps = new PartitionService();
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<Integer> phoneuserids = PhoneUserHelper.querybySharetoPhoneuserid(phoneuserid);
		List<Partition> zwavepartitions = ps.queryParitionsByPhoneuserid(phoneuserids);
		partitionset.addAll(zwavepartitions);
		List<String> deviceids = PhoneUserHelper.queryDeviceidbySharetoPhoneuserid(phoneuserid);
		List<ZWaveDevice> zwavedevices = zds.querybydeviceid(deviceids);
		for(ZWaveDevice zwave : zwavedevices){
			if(IRemoteConstantDefine.DEVICE_TYPE_DSC.equals(zwave.getDevicetype())){
				List<Partition> partitions1 = ps.querypartitionbyzwavedeviceid(zwave.getZwavedeviceid());
				partitionset.addAll(partitions1);
			}
		}
		
		return partitionset;
	}
}
