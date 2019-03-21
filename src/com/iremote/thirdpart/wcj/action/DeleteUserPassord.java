package com.iremote.thirdpart.wcj.action;

import com.iremote.domain.*;
import com.iremote.service.DeviceCapabilityService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestManager;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestWrap;
import com.iremote.service.DoorlockUserService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid", "lockid"})
public class DeleteUserPassord  
{
	private static Log log = LogFactory.getLog(SetTempPassword.class);
	
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int lockid;
	private int zwavedeviceid;
	private ZWaveDevice lock;
	private ThirdPart thirdpart;
	private PhoneUser phoneuser;
	private int timeoutsecond = 10 ;
	private byte usercode = 0x01;
	protected int doorlockpasswordusertype = 1 ;
	private int asynch;
	
	private DoorlockPasswordService dpsvr = new DoorlockPasswordService();
	
	public String execute()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		lock = zds.query(lockid);
		
		if ( lock == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		DoorlockPassword dp = dpsvr.queryLatestActivePassword(zwavedeviceid,doorlockpasswordusertype, usercode);

		if ( dp == null 
				|| dp.getSynstatus() == IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_SENT 
				|| dp.getSynstatus() == IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET)
				sendcommand();
		if ( dp != null )
			dp.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_DELETE);
		
//		if ( this.resultCode == ErrorCodeDefine.SUCCESS)
//			deleteDoorlockUser();
		
		
		return Action.SUCCESS;
	}

	//	private void deleteDoorlockUser()
//	{
//		DoorlockUserService svr = new DoorlockUserService();
//		DoorlockUser du = svr.query(zwavedeviceid, usertype, usercode);
//		
//		if ( du == null )
//			return ;
//		svr.delete(du);
//	}
	
	protected void sendcommand()
	{
		byte[] b = createcommand();
				
		sendcommand(createCommandTlv(b));
	}
	
	protected byte[] createcommand()
	{
		return new byte[]{(byte)0x80,0x07,0x00,(byte)0x80,0x10,0x01,0x09,getUsercode(),0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
	}
	
	protected Byte[] responseKey()
	{
		return new Byte[]{(byte)0x80,0x07,0x00,(byte)0x80,0x10,0x01,0x0A,getUsercode()} ;
	}
	
	protected boolean sendcommand(CommandTlv ct)
	{
		try 
		{
			ZwaveReportRequestWrap wrap = ZwaveReportRequestManager.sendRequest(lock.getDeviceid(), lock.getNuid(), ct, responseKey(), timeoutsecond, 0);
			byte[] rst = wrap.getResponse();
			if ( rst == null )
			{
				resultCode = wrap.getAckresult();
				return false ;
			}
			return checkResult(rst);
			
		} 
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
		resultCode = ErrorCodeDefine.UNKNOW_ERROR;
		return false ;
	}
	
	protected boolean checkResult(byte[] cmd)
	{
		if ( ((cmd[3] & 0xff) == 0x80 ) && ( cmd[8] == 0x01) ) //delete password 
			return true;
		return false ;
	}
	
	protected CommandTlv createCommandTlv(byte[] command)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , command));
		ct.addUnit(new TlvIntUnit(71 , lock.getNuid() , 1));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		return ct ;
	}
	
	public void setUsercode(byte usercode) {
		this.usercode = usercode;
	}

	protected byte getUsercode() {
		return usercode;
	}

	public int getResultCode() {
		return resultCode;
	}


	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
		this.lockid = this.zwavedeviceid;
	}



	public void setLockid(int lockid) {
		this.lockid = lockid;
	}



	public void setThirdpart(ThirdPart thirdpart)
	{
		this.thirdpart = thirdpart;
	}

	public void setAsynch(int asynch) {
		this.asynch = asynch;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
}
