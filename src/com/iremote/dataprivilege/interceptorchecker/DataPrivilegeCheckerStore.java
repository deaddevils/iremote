package com.iremote.dataprivilege.interceptorchecker;

import com.iremote.common.processorstore.ClassMapper;
import com.iremote.dataprivilege.admin.AdminPrivilegeChecker;
import com.iremote.dataprivilege.camera.CameraModifyPrivilegeCheckerofPhoneUser;
import com.iremote.dataprivilege.camera.CameraOperationPrivilegeCheckerofPhoneUser;
import com.iremote.dataprivilege.cameras.CamerasModifyPrivilegeCheckerofPhoneUser;
import com.iremote.dataprivilege.devicerawcommand.DeviceRawCommandModifyPrivilegeChecher;
import com.iremote.dataprivilege.devicerawcommand.DeviceRawCommandOperationPrivilegeChecker;
import com.iremote.dataprivilege.gateway.GatewayModifyPrivilegeCheckerforPhoneUser;
import com.iremote.dataprivilege.infrareddevice.InfraredDeviceModifyPrivilegeCheckerforPhoneUser;
import com.iremote.dataprivilege.infrareddevices.InfraredDevicesModifyPrivilegeCheckerforPhoneUser;
import com.iremote.dataprivilege.partition.PartitionModifyPrivilegeChecker;
import com.iremote.dataprivilege.partition.PartitionOperationPrivilegeChecker;
import com.iremote.dataprivilege.room.RoomModifyPrivilegeCheckerforPhoneUser;
import com.iremote.dataprivilege.usershare.ShareDeviceModifyPrivilegeCheckerforPhoneUser;
import com.iremote.dataprivilege.usershare.UserShareModifyPrivilegeChecker;
import com.iremote.dataprivilege.zwavedevice.DeviceModifyPrivilegeCheckerforPhoneUser;
import com.iremote.dataprivilege.zwavedevice.DeviceOperationPrivilegeCheckerforPhoneUser;
import com.iremote.dataprivilege.zwavedevice.ZwaveDeviceModifyPrivilegeCheckerforPhoneUser;
import com.iremote.dataprivilege.zwavedevice.ZwaveDeviceOperationPrivilegeCheckerforPhoneUser;
import com.iremote.dataprivilege.zwavedevices.ZWaveDevicesModifyPrivilegeCheckerforPhoneUser;
import com.iremote.domain.PhoneUser;

public class DataPrivilegeCheckerStore extends ClassMapper<IURLDataPrivilegeChecker<PhoneUser>>
{
	private static DataPrivilegeCheckerStore instance = new DataPrivilegeCheckerStore();
	
	protected DataPrivilegeCheckerStore()
	{
		registProcessor(makekey(DataPrivilegeType.OPERATION , "device") , DeviceOperationPrivilegeCheckerforPhoneUser.class);
		registProcessor(makekey(DataPrivilegeType.MODIFY , "device") , DeviceModifyPrivilegeCheckerforPhoneUser.class);
		registProcessor(makekey(DataPrivilegeType.MODIFY , "sharedevice") , ShareDeviceModifyPrivilegeCheckerforPhoneUser.class);
		registProcessor(makekey(DataPrivilegeType.MODIFY , "gateway") , GatewayModifyPrivilegeCheckerforPhoneUser.class);
		registProcessor(makekey(DataPrivilegeType.MODIFY , "infrareddevice") , InfraredDeviceModifyPrivilegeCheckerforPhoneUser.class);
		registProcessor(makekey(DataPrivilegeType.MODIFY , "infrareddevices") , InfraredDevicesModifyPrivilegeCheckerforPhoneUser.class);
		registProcessor(makekey(DataPrivilegeType.OPERATION , "zwavedevice") , ZwaveDeviceOperationPrivilegeCheckerforPhoneUser.class);
		registProcessor(makekey(DataPrivilegeType.MODIFY , "zwavedevice") , ZwaveDeviceModifyPrivilegeCheckerforPhoneUser.class);
		registProcessor(makekey(DataPrivilegeType.MODIFY , "zwavedevices") , ZWaveDevicesModifyPrivilegeCheckerforPhoneUser.class);
		registProcessor(makekey(DataPrivilegeType.MODIFY , "room") , RoomModifyPrivilegeCheckerforPhoneUser.class);
		registProcessor(makekey(DataPrivilegeType.OPERATION , "camera") , CameraOperationPrivilegeCheckerofPhoneUser.class);
		registProcessor(makekey(DataPrivilegeType.MODIFY , "camera") , CameraModifyPrivilegeCheckerofPhoneUser.class);
		registProcessor(makekey(DataPrivilegeType.MODIFY , "cameras") , CamerasModifyPrivilegeCheckerofPhoneUser.class);
		registProcessor(makekey(DataPrivilegeType.MODIFY , "admin") , AdminPrivilegeChecker.class);
		registProcessor(makekey(DataPrivilegeType.MODIFY , "usershare") , UserShareModifyPrivilegeChecker.class);
		registProcessor(makekey(DataPrivilegeType.OPERATION , "partition") , PartitionOperationPrivilegeChecker.class);
		registProcessor(makekey(DataPrivilegeType.MODIFY , "partition") , PartitionModifyPrivilegeChecker.class);
		registProcessor(makekey(DataPrivilegeType.MODIFY , "devicerawcmd") , DeviceRawCommandModifyPrivilegeChecher.class);
		registProcessor(makekey(DataPrivilegeType.OPERATION , "devicerawcmd") , DeviceRawCommandOperationPrivilegeChecker .class);
	}
	
	public static DataPrivilegeCheckerStore getInstance()
	{
		return instance ;
	}
	
	public IURLDataPrivilegeChecker<PhoneUser> getPhoneUserPrivilgeChecker(DataPrivilegeType privlegetype , String domain)
	{
		return super.getProcessor(makekey(privlegetype , domain)) ;
	}
	
	private String makekey(DataPrivilegeType type , String domain)
	{
		return String.format("%s_%s", type.toString() , domain);
	}
}
