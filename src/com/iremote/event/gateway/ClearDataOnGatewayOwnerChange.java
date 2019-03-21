package com.iremote.event.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.iremote.action.helper.GatewayHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.RemoteOwnerChangeEvent;
import com.iremote.dataprivilege.PhoneUserDataPrivilegeCheckor;
import com.iremote.domain.Associationscene;
import com.iremote.domain.Command;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Scene;
import com.iremote.service.AssociationsceneService;
import com.iremote.service.CommandService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RemoteService;
import com.iremote.service.SceneService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;

public class ClearDataOnGatewayOwnerChange extends RemoteOwnerChangeEvent implements ITextMessageProcessor
{	
	private List<Integer> sharetophoneuserids ;
	private List<Integer> sharefromphoneuserids ;
	private List<Scene> rmlst = new ArrayList<Scene>();
	
	@Override
	public void run()
	{
		if ( super.getNewownerid() == super.getOldownerid())
			return ;
		
		ComunityRemoteService crs = new ComunityRemoteService();
		if ( crs.querybyDeviceid(super.getDeviceid()) != null ) 
			return ;
		
		//all phone users ID to who new owner has shared his devices .  
		sharetophoneuserids = PhoneUserHelper.queryAuthorityPhoneuserid(super.getNewownerid());
		
		clearAssociationfornewOwner();
		clearSceneCommandforoldOwner();
		
		GatewayHelper.clearDeviceShare(super.getDeviceid());
	} 
	
	//Delete all association commands that trigger devices belongs to the gateway and new owner has no privilege to target devices
	//Delete all association commands that trigger devices belongs to the gateway and new owner has no privilege to target scene
	//Delete all scene associations that trigger devices belongs to the gateway and scene owner has no privilege to the gateway
	public void clearAssociationfornewOwner()
	{
		PhoneUserService pus = new PhoneUserService();
		PhoneUser pu = pus.query(super.getNewownerid());
	
		// all zwave devices ID belong to this gateway
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<Integer> zdid = zds.queryidbydeviceid(super.getDeviceid());
		
		if (zdid == null || zdid.size() == 0 )
			return ;
		
		// all association that triggers by devices belong to this gateway
		AssociationsceneService ass = new AssociationsceneService();
		List<Associationscene> al = ass.querybyZwaveDeviceid(zdid);
		
		if ( al == null || al.size() == 0 )
			return ;
		
		//all phone users ID who have shared their devices to new owner.  
		sharefromphoneuserids = PhoneUserHelper.querybySharetoPhoneuserid(super.getNewownerid());
		
		//Privilege checker for new owner .
		PhoneUserDataPrivilegeCheckor checkor = new PhoneUserDataPrivilegeCheckor(pu);
		SceneService ss = new SceneService();
		
		for ( Associationscene as : al  )
		{
			if ( as.getScene() == null 
					|| as.getScene().getCommandlist() == null 
					|| as.getScene().getCommandlist().size() == 0 ) 
			{
				ss.delete(as.getScene());
				continue ;
			}
			
			//association scene , delete those commands that new owner has no privilege to its target devices 
			if ( as.getScene().getScenetype() == IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION)  
			{				
				for ( ListIterator<Command> it = as.getScene().getCommandlist().listIterator() ; it.hasNext() ;)
				{
					Command c = it.next();
					
					if ( c.getZwavedevice() != null 
						&& checkor.checkZWaveDeviceOperationPrivilege(c.getZwavedeviceid()) == false ) //new owner has no privilege to target device 
						it.remove();
					else if ( c.getInfrareddeviceid() != null 
							&& checkor.checkInfraredDeviceOperationPrivilege(c.getInfrareddeviceid()) == false ) //new owner has no privilege to target device
						it.remove();
					else if ( c.getLaunchscenedbid() != null )
					{
						Scene lc = ss.query(c.getLaunchscenedbid());
						if ( lc == null || !sharefromphoneuserids.contains(lc.getPhoneuserid()))  //new owner has no privilege to target scene ;
							it.remove();
					}
				}
			}
			//normal scene , delete trigger devices if the scene owner has no privilege to the trigger devices .
			else if ( as.getScene().getScenetype() == IRemoteConstantDefine.SCENE_TYPE_SCENE)  // normal scene 
			{
				if ( as.getScene().getPhoneuserid() == null 
						|| !sharetophoneuserids.contains(as.getScene().getPhoneuserid()))  // gateway new owner has not shared devices to scene owner 
					as.getScene().getAssociationscenelist().remove(as);
			}
			
			if ( as.getScene().getCommandlist().size() == 0 )
			{
				ss.delete(as.getScene());
				rmlst.add(as.getScene());
			}
		}
	}
	
	//Delete all commands that target devices belong to this gateway and trigger devices belongs to those phone users that new owner not shares his devices to
	//Delete all commands that scene owner has no privilege to this gateway
	public void clearSceneCommandforoldOwner()
	{
		SceneService ss = new SceneService();
		
		RemoteService rs = new RemoteService();
		
		//all gateways ID of those phone users.
		List<String> didl = rs.queryDeviceidbyPhoneUserid(sharetophoneuserids);
		
		//all commands that target devices belong to the gateway
		CommandService cs = new CommandService();
		List<Command> lst = cs.querybydeviceid(super.getDeviceid());
		
		for ( Command c : lst )
		{
			if ( c.getScene() == null )
				continue;
			if ( c.getScene().getScenetype() == IRemoteConstantDefine.SCENE_TYPE_SCENE  //scene command
					&& !sharetophoneuserids.contains(c.getScene().getPhoneuserid()))  //scene owner has no privilege to this gateway 
				c.getScene().getCommandlist().remove(c);
			else if ( c.getScene().getScenetype() == IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION  //association command
					&& c.getAssociationscene() != null 
					&& c.getAssociationscene().getZwavedevice() != null 
					&& !didl.contains(c.getAssociationscene().getZwavedevice().getDeviceid()))  //trigger device's gateway not in the gateway list. 
				c.getScene().getCommandlist().remove(c);
			if ( c.getScene().getCommandlist().size() == 0 )  //delete scenes that command list is empty
			{
				ss.delete(c.getScene());
				rmlst.add(c.getScene());
			}
		}
	}

	@Override
	public String getTaskKey()
	{
		return super.getDeviceid();
	}

}
