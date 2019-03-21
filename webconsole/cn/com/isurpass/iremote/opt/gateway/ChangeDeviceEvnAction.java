package cn.com.isurpass.iremote.opt.gateway;

import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.opensymphony.xwork2.Action;



@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "admin", parameter = "deviceid")
public class ChangeDeviceEvnAction
{
	private String errmsg = "success" ;
	private String deviceid;
	private int evn; 
	public String execute()
	{
		if ( !ConnectionManager.contants(deviceid) )
		{
			errmsg = "gateway offline";
			return Action.SUCCESS;
		}
		
		CommandTlv ct = createCommand();
		
		SynchronizeRequestHelper.asynchronizeRequest(deviceid, ct , 0);
		
		return Action.SUCCESS;
	}
	
	private CommandTlv createCommand()
	{
		byte[] db = deviceid.getBytes();
		
		CommandTlv ct = new CommandTlv(0xfe , 0x03);
		byte[] b = new byte[db.length+1];

		System.arraycopy(db, 0, b, 0, db.length);
		b[b.length-1] = (byte)evn;
		
		ct.addUnit(new TlvByteUnit(2,b));
		return ct ;
	}

	public String getErrmsg()
	{
		return errmsg;
	}


	public void setEvn(int evn)
	{
		this.evn = evn;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}
}
