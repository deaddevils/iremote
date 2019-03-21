package com.iremote.dataprivilege;

public interface IUserDataPrivilegeChecker
{
	boolean checkZWaveDeviceModifyPrivilege(String deviceid , int zwavedeviceid);
	boolean checkZWaveDeviceOperationPrivilege(String deviceid , int zwavedeviceid);
	boolean checkInfraredDeviceModifyPrivilege(String deviceid , int infrareddeviceid);
	boolean checkInfraredDeviceOperationPrivilege(String deviceid , int infrareddeviceid);

	boolean checkPartitionOperationPrivilege(int partitionid);
	boolean checkPartitionModifyPrivilege(int partitionid);

	boolean checkZWaveDeviceModifyPrivilege(int zwavedeviceid);
	boolean checkZWaveDeviceOperationPrivilege(int zwavedeviceid);
	boolean checkInfraredDeviceModifyPrivilege(int infrareddeviceid);
	boolean checkInfraredDeviceOperationPrivilege(int infrareddeviceid);
}
