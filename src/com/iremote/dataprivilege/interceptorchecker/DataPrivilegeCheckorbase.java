package com.iremote.dataprivilege.interceptorchecker;

import java.util.HashMap;
import java.util.Map;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.dataprivilege.IUserDataPrivilegeChecker;

public abstract class DataPrivilegeCheckorbase implements IUserDataPrivilegeChecker
{
	private Map<String , DataPrivilegeType> privilegemap = new HashMap<String , DataPrivilegeType>();

	@Override
	public boolean checkPartitionModifyPrivilege(int partitionid) {
		return (checkprivilege("", IRemoteConstantDefine.MAJORTYPE_PARTITION, partitionid, DataPrivilegeType.MODIFY));
	}

	@Override
	public boolean checkPartitionOperationPrivilege(int partitionid) {
		return (checkprivilege("", IRemoteConstantDefine.MAJORTYPE_PARTITION, partitionid, DataPrivilegeType.OPERATION));
	}

	@Override
	public boolean checkZWaveDeviceModifyPrivilege(String deviceid, int zwavedeviceid)
	{
		if ( checkprivilege(deviceid,IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , 0 , DataPrivilegeType.MODIFY ))
			return true;
		return checkprivilege(deviceid,IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , zwavedeviceid , DataPrivilegeType.MODIFY );
	}
	
	@Override
	public boolean checkZWaveDeviceModifyPrivilege(int zwavedeviceid)
	{
		return checkprivilege(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , zwavedeviceid , DataPrivilegeType.MODIFY );
	}

	@Override
	public boolean checkZWaveDeviceOperationPrivilege(String deviceid, int zwavedeviceid)
	{
		if ( checkprivilege(deviceid,IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , 0 , DataPrivilegeType.OPERATION ))
			return true;
		return checkprivilege(deviceid,IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , zwavedeviceid , DataPrivilegeType.OPERATION );
	}
	
	@Override
	public boolean checkZWaveDeviceOperationPrivilege(int zwavedeviceid)
	{
		return checkprivilege(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , zwavedeviceid , DataPrivilegeType.OPERATION );
	}

	@Override
	public boolean checkInfraredDeviceModifyPrivilege(String deviceid, int infrareddeviceid)
	{
		if ( checkprivilege(deviceid,IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED , 0 , DataPrivilegeType.MODIFY ))
			return true;
		return checkprivilege(deviceid,IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED , infrareddeviceid , DataPrivilegeType.MODIFY );
	}

	@Override
	public boolean checkInfraredDeviceModifyPrivilege(int infrareddeviceid)
	{
		return checkprivilege(IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED , infrareddeviceid , DataPrivilegeType.MODIFY );
	}
	
	@Override
	public boolean checkInfraredDeviceOperationPrivilege(String deviceid, int infrareddeviceid)
	{
		if ( checkprivilege(deviceid,IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED , 0 , DataPrivilegeType.OPERATION ))
			return true;
		return checkprivilege(deviceid,IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED , infrareddeviceid , DataPrivilegeType.OPERATION );
	}

	@Override
	public boolean checkInfraredDeviceOperationPrivilege(int infrareddeviceid)
	{
		return checkprivilege(IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED , infrareddeviceid , DataPrivilegeType.OPERATION );
	}

	protected boolean checkprivilege(String deviceid , String type ,  int id , DataPrivilegeType privilege)
	{
		DataPrivilegeType p = privilegemap.get(makekey(deviceid , type , id));
		if ( p == null )
			return false ;
		if ( privilege.getPrivilegelevel() <= p.getPrivilegelevel())
			return true;
		return false;
	}
	
	protected boolean checkprivilege(String type ,  int id , DataPrivilegeType privilege)
	{
		DataPrivilegeType p = privilegemap.get(makekey("" , type , id));
		if ( p == null )
			return false ;
		if ( privilege.getPrivilegelevel() <= p.getPrivilegelevel())
			return true;
		return false;
	}
	
	protected String makekey(String deviceid , String type ,  int id)
	{
		return String.format("%s_%s_%d", deviceid ,type , id);
	}
	
	protected void addPrivelege(String deviceid , String type ,  int id , DataPrivilegeType privilege)
	{
		this.privilegemap.put(makekey(deviceid , type , id), privilege);
	}
	
	protected void addPrivelege(String type ,  int id , DataPrivilegeType privilege)
	{
		this.privilegemap.put(makekey("" , type , id), privilege);
	}
}
