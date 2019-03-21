package com.iremote.event.gateway;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.RemoteEvent;
import com.iremote.domain.Partition;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.DoorlockAssociationService;
import com.iremote.service.PartitionService;
import com.iremote.service.ZWaveDeviceService;

public class ClearDoorlockAssociationOnRemoteDelete extends RemoteEvent implements ITextMessageProcessor{

	@Override
	public void run() {
		Set<Integer> partitionidset = new HashSet<>();
		String gatewayid = super.getDeviceid();
		int phoneuserid2 = super.getPhoneuserid();
		ZWaveDeviceService zds = new ZWaveDeviceService();
		PartitionService ps = new PartitionService();
		DoorlockAssociationService das = new DoorlockAssociationService();
		List<ZWaveDevice> doorlocklist = zds.querybydeviceidtypelist(gatewayid, IRemoteConstantDefine.DEVICE_TYPE_LIST_WHICH_HAS_ASSOCIATION);
		for(ZWaveDevice z : doorlocklist){
			das.deletebyzwavedeviceid(z.getZwavedeviceid());
		}
		List<Partition> partitionlist1 = ps.queryParitionsByPhoneuserid(phoneuserid2);
		List<ZWaveDevice> dsclist = zds.querybydeviceidtype(gatewayid, IRemoteConstantDefine.DEVICE_TYPE_DSC);
		for(ZWaveDevice z:dsclist){
			if(z.getPartitions()!=null&&z.getPartitions().size()>0){
				for(Partition p:z.getPartitions()){
					partitionidset.add(p.getPartitionid());
				}
			}
		}
		for(Partition p:partitionlist1){
			partitionidset.add(p.getPartitionid());
		}
		das.deletebyobjtypeandobjid(2, partitionidset);
		
	}

	@Override
	public String getTaskKey() {
		return super.getDeviceid();
	}

}
