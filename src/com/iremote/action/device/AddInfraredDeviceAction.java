package com.iremote.action.device;

import java.util.Date;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.InfoChange;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.PhoneUser;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.InfreredCodeLiberayService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang.StringUtils;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "gateway", parameter = "deviceid")
public class AddInfraredDeviceAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String name;
	private String devicetype;
	private String codeid;
	private String deviceid ;
	private String codelibery;
	private String productorid;
	private String controlmodeid;
	private int codeindex;
	private InfraredDevice infrareddevice;
	private PhoneUser phoneuser;

	public String execute()
	{
		createInfraredDevice();
		
		InfraredDeviceService svr = new InfraredDeviceService();
		svr.save(infrareddevice);
		
		phoneuser.setLastupdatetime(new Date());
		PhoneUserHelper.sendInfoChangeMessage(phoneuser);
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED, new InfoChange(deviceid , new Date() , 0) );

		return Action.SUCCESS;
	}
	
	private void createInfraredDevice()
	{
		infrareddevice = new InfraredDevice();
		infrareddevice.setDeviceid(deviceid);
		infrareddevice.setApplianceid(Utils.createsecuritytoken(32));
		setCodeLibrary();
		infrareddevice.setCodeindex(codeindex);
		infrareddevice.setControlmodeid(controlmodeid);
		infrareddevice.setDevicetype(devicetype);
		infrareddevice.setName(name);
		infrareddevice.setProductorid(productorid);

	}

	private void setCodeLibrary() 
	{
		InfreredCodeLiberayService cls = new InfreredCodeLiberayService();
		if ( StringUtils.isNumeric(codeid) )
		{
			String codelabrary = cls.queryByCodeid(Integer.valueOf(codeid), devicetype);
		
			infrareddevice.setCodeid(codeid);
			infrareddevice.setCodelibery(Utils.jsontoIntArray(codelabrary));
		}
		else
		{
			infrareddevice.setCodeid(codeid);
			infrareddevice.setCodelibery(Utils.jsontoIntArray(codelibery));
		}
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public InfraredDevice getInfrareddevice()
	{
		return infrareddevice;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setDevicetype(String devicetype)
	{
		this.devicetype = devicetype;
	}

	public void setCodeid(String codeid)
	{
		this.codeid = codeid;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

	public void setCodelibery(String codelibery)
	{
		this.codelibery = codelibery;
	}

	public void setProductorid(String productorid)
	{
		this.productorid = productorid;
	}

	public void setControlmodeid(String controlmodeid)
	{
		this.controlmodeid = controlmodeid;
	}

	public void setCodeindex(int codeindex)
	{
		this.codeindex = codeindex;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
}
