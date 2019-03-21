package com.iremote.dataprivilege.interceptorchecker;

import com.iremote.common.processorstore.ClassMapper;
import com.iremote.dataprivilege.attribute.DeviceManagePrivilegeCheckerforThirdpart;
import com.iremote.dataprivilege.gateway.GatewayModifyPrivilegeCheckerforThirdpart;
import com.iremote.dataprivilege.zwavedevice.DevicePrivilegeCheckerforThirdparter;
import com.iremote.domain.ThirdPart;

public class DataPrivilegeCheckerStoreforThirdpart extends ClassMapper<IURLDataPrivilegeChecker<ThirdPart>>
{
	private static DataPrivilegeCheckerStoreforThirdpart instance = new DataPrivilegeCheckerStoreforThirdpart();
	
	protected DataPrivilegeCheckerStoreforThirdpart()
	{
		registProcessor(makekey(DataPrivilegeType.OPERATION , "device") , DevicePrivilegeCheckerforThirdparter.class);
		registProcessor(makekey(DataPrivilegeType.MODIFY , "device") , DevicePrivilegeCheckerforThirdparter.class);
		registProcessor(makekey(DataPrivilegeType.READ , "device") , DevicePrivilegeCheckerforThirdparter.class);
		registProcessor(makekey(DataPrivilegeType.MODIFY , "sharedevice") , DevicePrivilegeCheckerforThirdparter.class);
		registProcessor(makekey(DataPrivilegeType.MODIFY , "gateway") , GatewayModifyPrivilegeCheckerforThirdpart.class);
		registProcessor(makekey(DataPrivilegeType.ATTRIBUTE , "devicemanage") , DeviceManagePrivilegeCheckerforThirdpart.class);
	}
		
	public static DataPrivilegeCheckerStoreforThirdpart getInstance()
	{
		return instance ;
	}
	
	public IURLDataPrivilegeChecker<ThirdPart> getPhoneUserPrivilgeChecker(DataPrivilegeType privlegetype , String domain)
	{
		return super.getProcessor(makekey(privlegetype , domain)) ;
	}
	
	private String makekey(DataPrivilegeType type , String domain)
	{
		return String.format("%s_%s", type.toString() , domain);
	}
}
