package com.iremote.dataprivilege;

import java.util.HashSet;
import java.util.List;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeCheckorbase;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.*;
import com.iremote.service.*;

import static java.util.stream.Collectors.toList;

public class PhoneUserDataPrivilegeCheckor extends DataPrivilegeCheckorbase
{
	private PhoneUser phoneuser ;

	public PhoneUserDataPrivilegeCheckor(PhoneUser phoneuser)
	{
		super();
		this.phoneuser = phoneuser;
		init();
	}

	protected void init()
	{
		
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		List<ZWaveDeviceShare> lstzds = zdss.query(this.phoneuser.getPhoneuserid());
		
		for ( ZWaveDeviceShare zds : lstzds )
		{
			if ( zds.getZwavedeviceid() != null && zds.getZwavedeviceid() != 0 )
			{
				addPrivelege(zds.getDeviceid() ,IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE, zds.getZwavedeviceid(), DataPrivilegeType.OPERATION);
				addPrivelege(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE, zds.getZwavedeviceid(), DataPrivilegeType.OPERATION);
			}
			else if ( zds.getInfrareddeviceid() != null && zds.getInfrareddeviceid() != 0 )
			{
				addPrivelege(zds.getDeviceid() ,IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED, zds.getInfrareddeviceid(), DataPrivilegeType.OPERATION);
				addPrivelege(IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED, zds.getInfrareddeviceid(), DataPrivilegeType.OPERATION);
			}
		}
		
		RemoteService rs = new RemoteService();
		
		List<String> lst = PhoneUserHelper.queryDeviceidbySharetoPhoneuserid(this.phoneuser.getPhoneuserid());
		for ( String deviceid : lst )
		{
			addPrivelege(deviceid ,IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE, 0, DataPrivilegeType.OPERATION);
			addPrivelege(deviceid ,IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED, 0, DataPrivilegeType.OPERATION);
		}

		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<Integer> zdl = zds.queryidbydeviceid(lst);
		for ( Integer zid : zdl )
			addPrivelege(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE, zid, DataPrivilegeType.OPERATION);
		
		InfraredDeviceService ids = new InfraredDeviceService();
		List<Integer> idl = ids.queryidbydeviceid(lst);
		for ( Integer id : idl)
			addPrivelege(IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED, id, DataPrivilegeType.OPERATION);
		
		lst = rs.queryDeviceidbyPhoneUserid(this.phoneuser.getPhoneuserid());
		for ( String deviceid : lst )
		{
			addPrivelege(deviceid ,IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE, 0, DataPrivilegeType.MODIFY);
			addPrivelege(deviceid ,IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED, 0, DataPrivilegeType.MODIFY);
		}
		
		zdl = zds.queryidbydeviceid(lst);
		for ( Integer zid : zdl )
			addPrivelege(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE, zid, DataPrivilegeType.MODIFY);
		
		idl = ids.queryidbydeviceid(lst);
		for ( Integer id : idl)
			addPrivelege(IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED, id, DataPrivilegeType.MODIFY);

		addPartitionPrivelege();
	}

	private void addPartitionPrivelege() {
		UserShareService uss = new UserShareService();
		PhoneUserService pus = new PhoneUserService();
		ZWaveDeviceService zds = new ZWaveDeviceService();
		PartitionService ps = new PartitionService();
		RemoteService rs = new RemoteService();
		HashSet<Integer> idSet = new HashSet<>();
		HashSet<Integer> canOperateParitionIdSet = new HashSet<>();
		HashSet<Integer> canModifyParitionIdSet = new HashSet<>();

		List<PhoneUser> familyPhoneuser = pus.querybyfamiliyid(phoneuser.getFamilyid());
		List<Integer> phoneuserid = uss.queryFriendIdsByTouserid(phoneuser.getPhoneuserid());

		idSet.addAll(phoneuserid);
		for (PhoneUser familyPhoneuserId : familyPhoneuser) {
			idSet.add(familyPhoneuserId.getPhoneuserid());
		}

		List<Integer> list = ps.queryParitionidByPhoneuserList(idSet);
		canOperateParitionIdSet.addAll(list);

		List<String> deviceIdList = rs.queryDeviceidListPhoneUserid(idSet);
		List<ZWaveDevice> zWaveDeviceList = zds.queryByDevicetype(deviceIdList, IRemoteConstantDefine.DEVICE_TYPE_DSC);

		for (ZWaveDevice zWaveDevice : zWaveDeviceList) {
			canOperateParitionIdSet.addAll(zWaveDevice.getPartitions().stream().map(Partition::getPartitionid).collect(toList()));
		}

		List<Remote> remotes = rs.queryRemoteByPhoneuserid(phoneuser.getPhoneuserid());
		List<ZWaveDevice> zWaveDevices = zds.queryByDevicetype(remotes.stream().map(Remote::getDeviceid).collect(toList()), IRemoteConstantDefine.DEVICE_TYPE_DSC);
		for (ZWaveDevice zWaveDevice : zWaveDevices) {
			canModifyParitionIdSet.addAll(zWaveDevice.getPartitions().stream().map(Partition::getPartitionid).collect(toList()));
		}

		List<Partition> partitions = ps.queryParitionsByPhoneuserid(phoneuser.getPhoneuserid());
		for (Partition partition : partitions) {
			canModifyParitionIdSet.add(partition.getPartitionid());
		}

		for (Integer id : canModifyParitionIdSet) {
			addPrivelege(IRemoteConstantDefine.MAJORTYPE_PARTITION, id, DataPrivilegeType.MODIFY);
			addPrivelege(IRemoteConstantDefine.MAJORTYPE_PARTITION, id, DataPrivilegeType.OPERATION);
		}
		for (Integer id : canOperateParitionIdSet) {
			addPrivelege(IRemoteConstantDefine.MAJORTYPE_PARTITION, id, DataPrivilegeType.OPERATION);
		}
	}

	public void initt() {
		init();
	}
}
