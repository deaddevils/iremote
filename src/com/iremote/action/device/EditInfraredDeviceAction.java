package com.iremote.action.device;

import java.util.Date;
import java.util.List;

import com.iremote.service.InfraredDeviceService2;
import com.iremote.service.InfreredCodeLiberayService;

import org.apache.commons.lang3.StringUtils;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.Utils;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.RoomAppliance;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.RemoteService;
import com.iremote.service.RoomApplianceService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "infrareddevice", parameter = "infrareddeviceid")
public class EditInfraredDeviceAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int infrareddeviceid;
	private String name;
	private String codeid;
	private String codelibery;
	private String productorid;
	private String controlmodeid;
	private Integer codeindex;
	private PhoneUser phoneuser ;
	
	public String execute()
	{
		if ( StringUtils.isBlank(name))
		{
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		
		RemoteService rs = new RemoteService();
		List<String> ldid = rs.queryDeviceidbyPhoneUserid(phoneuser.getPhoneuserid());
		
		InfraredDeviceService ids = new InfraredDeviceService();
		List<InfraredDevice> lst = ids.querybydeviceid(ldid);
		
		for ( InfraredDevice id : lst )
			if ( id.getInfrareddeviceid() != infrareddeviceid && name.equals(id.getName()) )
			{
				resultCode = ErrorCodeDefine.NAME_IS_EXIST;
				return Action.SUCCESS;
			}
		InfraredDevice infrareddevice = ids.query(infrareddeviceid);
		
		if ( infrareddevice == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		infrareddevice.setName(name);
		
		if ( StringUtils.isNotBlank(codeid))
			setCodeLibrary(infrareddevice);
		if ( StringUtils.isNotBlank(productorid))
			infrareddevice.setProductorid(productorid);
		if ( StringUtils.isNotBlank(controlmodeid))
			infrareddevice.setControlmodeid(controlmodeid);
		if ( codeindex != null )
			infrareddevice.setCodeindex(codeindex);
		RoomApplianceService roomApplianceService = new RoomApplianceService();
		RoomAppliance ra = roomApplianceService.querybyInfraredid(infrareddeviceid);
		
		if ( ra != null )
			ra.setName(name);
		
		phoneuser.setLastupdatetime(new Date());
		PhoneUserHelper.sendInfoChangeMessage(phoneuser);
		
		return Action.SUCCESS;
	}

	private void setCodeLibrary(InfraredDevice infrareddevice) 
	{
		if (StringUtils.isNumeric(codeid))
        {
			InfreredCodeLiberayService ids2 = new InfreredCodeLiberayService();
            String codelabrary = ids2.queryByCodeid(Integer.valueOf(codeid), infrareddevice.getDevicetype());
			infrareddevice.setCodeid(codeid);
			infrareddevice.setCodelibery(Utils.jsontoIntArray(codelabrary));
        }
        else if ( StringUtils.isNotBlank(codeid))
		{
			infrareddevice.setCodeid(codeid);
			if ( StringUtils.isNotBlank(codelibery))
				infrareddevice.setCodelibery(Utils.jsontoIntArray(codelibery));
		}
	}

	public int getResultCode()
	{
		return resultCode;
	}
	public void setInfrareddeviceid(int infrareddeviceid)
	{
		this.infrareddeviceid = infrareddeviceid;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}

	public void setCodelibery(String codelibery) {
		this.codelibery = codelibery;
	}

	public void setProductorid(String productorid) {
		this.productorid = productorid;
	}

	public void setControlmodeid(String controlmodeid) {
		this.controlmodeid = controlmodeid;
	}

	public void setCodeindex(Integer codeindex) {
		this.codeindex = codeindex;
	}
	
	
	
}
