package cn.com.isurpass.iremote.opt.gateway;

import com.iremote.common.Utils;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.service.RemoteService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "admin", parameter = "olddeviceid")
public class ChangeDeviceidAction
{
	private String errmsg = "success" ;
	private String olddeviceid ;
	private String newdeviceid ;
	private int evn = 0xff;
	
	public String execute()
	{
		
		if ( !ConnectionManager.contants(olddeviceid) )
		{
			errmsg = "gateway offline";
			return Action.SUCCESS;
		}
		
		RemoteService rs = new RemoteService();
		if ( rs.getIremotepassword(newdeviceid) != null )
		{
			errmsg = "ID exists";
			return Action.SUCCESS;
		}
		
		CommandTlv ct = createCommand();
		
		SynchronizeRequestHelper.asynchronizeRequest(olddeviceid, ct , 0);
		
		return Action.SUCCESS;
	}
	
	private CommandTlv createCommand()
	{
		byte[] db = newdeviceid.getBytes();
		
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

	public void setOlddeviceid(String olddeviceid)
	{
		this.olddeviceid = olddeviceid;
	}

	public void setNewdeviceid(String newdeviceid)
	{
		this.newdeviceid = newdeviceid;
	}

	public void setEvn(int evn)
	{
		this.evn = evn;
	}
	
	public static void main(String arg[])
	{
		ChangeDeviceidAction cda = new ChangeDeviceidAction();
		cda.setEvn(0xff);
		cda.setNewdeviceid("iRemote2005000000010");
		
		CommandTlv ct = cda.createCommand();
		Utils.print("", ct.getByte());
	}
}
