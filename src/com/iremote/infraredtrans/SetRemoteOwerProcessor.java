package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.RemoteOwnerChangeEvent;
import com.iremote.common.push.PushMessage;
import com.iremote.domain.Remote;
import com.iremote.domain.CommunityAdministrator;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.UserInOut;
import com.iremote.domain.UserShare;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.CommunityAdministratorService;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.UserInOutService;
import com.iremote.service.UserShareService;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;

public class SetRemoteOwerProcessor implements IRemoteRequestProcessor 
{
	private static CommandTlv RESULT_SUCCESS = new CommandTlv(101 , 4);
	private static CommandTlv RESULT_ERROR = new CommandTlv(101 , 4);
	private static CommandTlv RESULT_NO_PRIVILEGE = new CommandTlv(101 , 4);

	protected String countrycode = IRemoteConstantDefine.DEFAULT_COUNTRYCODE;
	protected String phonenumber ;
	protected PhoneUser phoneuser;
	protected PhoneUser olduser;
	protected Remote remote;
	
	private PhoneUserService pus = new PhoneUserService();
	
	static 
	{
		RESULT_SUCCESS.addUnit(new TlvIntUnit(1,0,2));
		RESULT_ERROR.addUnit(new TlvIntUnit(1,ErrorCodeDefine.NO_PRIVILEGE,2));
		RESULT_NO_PRIVILEGE.addUnit(new TlvIntUnit(1,ErrorCodeDefine.NO_PRIVILEGE,2));
	}
	
	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException 
	{
		String uuid = nbc.getDeviceid();
		
		phonenumber = TlvWrap.readString(request, 12, 4);
		int idx = phonenumber.indexOf(":");
		if (  idx != -1 )
		{
			countrycode = phonenumber.substring(0 , idx );
			phonenumber = phonenumber.substring( idx + 1);
		}

		phoneuser = pus.query(countrycode , phonenumber , PhoneUserHelper.createplatform(Utils.getRemotePlatform(uuid)));
		
		if ( phoneuser == null )
			return RESULT_ERROR;
		
		IremotepasswordService svr = new IremotepasswordService();
		remote = svr.getIremotepassword(uuid);
		
		if ( checkprivilege() == false )
			return RESULT_NO_PRIVILEGE;

		if ( bindDevice() == false )
			return RESULT_ERROR;

		Integer oldowner = 0;

		if ( remote != null )
		{
			if ( remote.getPhoneuserid() != null )
				oldowner = remote.getPhoneuserid();
			remote.setLastupdatetime(new Date());
			remote.setPlatform(Utils.getRemotePlatform(uuid));
			setRemote(request , remote);
		}
		else 
		{
			remote = new Remote();
			remote.setDeviceid(uuid);
			setRemote(request , remote);
			remote.setCreatetime(new Date());
			remote.setLastupdatetime(new Date());
			remote.setPlatform(Utils.getRemotePlatform(uuid));
			remote.setBattery(100);
			svr.save(remote);
		}
		

		if ( oldowner != 0 && oldowner != remote.getPhoneuserid() )
		{
			olduser = pus.query(oldowner);
			if ( olduser != null )
				olduser.setLastupdatetime(new Date());

			notifyoldowner(oldowner , uuid , remote.getName());
		}
		
		ownerchange(oldowner , remote.getPhoneuserid());
		
		athome();
		
		RemoteOwnerChangeEvent re = new RemoteOwnerChangeEvent(remote.getDeviceid() , new Date() , 0 , phoneuser.getPhoneuserid() , null , phoneuser.getPhonenumber() , System.currentTimeMillis());
		if ( olduser != null )
		{
			re.setOldownerid(olduser.getPhoneuserid());
			re.setOldownerphonenumber(olduser.getPhonenumber());
		}
		re.setRemote(remote);
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_OWNER_CHANGED,re );
		
		return RESULT_SUCCESS;
	}
	
	protected boolean checkprivilege()
	{
		if ( checkThirdpartPrivilege() == false )
			return false ;
		
		return true;
	}
	
	private boolean checkThirdpartPrivilege()
	{
		if ( remote == null )
			return true ;
		
		ComunityRemoteService crs = new ComunityRemoteService();
		ComunityRemote cr = crs.querybyDeviceid(this.remote.getDeviceid());
		if ( cr == null )
			return true;
		
		CommunityAdministratorService cas = new CommunityAdministratorService();
		CommunityAdministrator ca = cas.querybyphoneuserid(phoneuser.getPhoneuserid());
		if ( ca == null )
			return false ;
		if ( ca.getThirdpartid() != cr.getThirdpartid() || ca.getCommunityid() != cr.getComunityid() )
			return false ;
		
		return true;
	}
	
	protected boolean bindDevice()
	{
		return true;
	}


	private void ownerchange(int owner1 , int owner2)
	{		
		UserShareService uss = new UserShareService();
		List<UserShare> lst = uss.querybyShareUser(owner1, owner2);
		
		Set<Integer> set = new HashSet<Integer>();
		for ( UserShare us : lst )
			set.add(us.getTouserid());
		set.add(owner1);
		set.add(owner2);
		
		List<String> aliaslst = pus.queryAlias(set);
		
		PushMessage.pushInfoChangedMessage(aliaslst.toArray(new String[0]) , remote.getPlatform());

	}
	
	private void notifyoldowner(int oldowner , String uuid , String name )
	{
		PhoneUser pu = pus.query(oldowner);
		if ( pu == null )
			return ;
		PushMessage.pushOnwerChangedMessage(pu, remote.getPlatform(), uuid, name , pu.getLanguage());
	}
	
	private void setRemote(byte[] request , Remote r)
	{
		GatewayReportHelper.setRemote(request, r);
		
		if ( phoneuser == null )
			return ;
		
		phoneuser.setLastupdatetime(new Date());
		
		r.setPhonenumber(phoneuser.getPhonenumber());
		r.setPhoneuserid(phoneuser.getPhoneuserid());

		if ( GatewayReportHelper.checkhomeid(request, r) ) 
		{
			GatewayReportHelper.clearRemote(r);
			
			RemoteOwnerChangeEvent re = new RemoteOwnerChangeEvent(remote.getDeviceid() , new Date() , 0 , phoneuser.getPhoneuserid() , null , phoneuser.getPhonenumber() , 0);
			re.setRemote(remote);
			JMSUtil.sendmessage(IRemoteConstantDefine.MESSAGE_TYPE_REMOTE_RESET, re);
		}
	}
	
	private void athome()
	{
		UserInOutService svr = new UserInOutService();
		UserInOut nio = svr.query(phoneuser.getPhoneuserid(), remote.getDeviceid());
		
		if ( nio != null )
			nio.setAction(IRemoteConstantDefine.USDRINOUT_IN);
		else 
		{
			nio = new UserInOut();
			nio.setAction(IRemoteConstantDefine.USDRINOUT_IN);
			nio.setDeviceid(remote.getDeviceid());
			nio.setPhonenumber(phoneuser.getPhonenumber());
			nio.setPhoneuserid(phoneuser.getPhoneuserid());
			svr.save(nio);
		}
	}

	public static void main(String arg[])
	{
		System.out.println(Integer.MAX_VALUE);
	}

}
