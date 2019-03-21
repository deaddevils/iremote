package com.iremote.thirdpart.rentinghouse.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import com.iremote.action.camera.CameraHelper;
import com.iremote.action.camera.lechange.LeChangeRequestManagerStore;
import com.iremote.action.helper.GatewayHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.constant.CameraProductor;
import com.iremote.domain.Associationscene;
import com.iremote.domain.Camera;
import com.iremote.domain.Command;
import com.iremote.domain.Scene;
import com.iremote.service.AssociationsceneService;
import com.iremote.service.CameraService;
import com.iremote.service.CommandService;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.SceneService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;

public class DeleteRemoteProcessor extends RemoteProcessor {

	private List<Scene> rmlst = new ArrayList<Scene>();

	@Override
	public void run() 
	{
		GatewayHelper.clearDeviceShare(super.getDeviceid());
		unbindDahuaLeChangeCamera();
		clearSceneCommand();
		clearAssociationCommand();
		
		ComunityRemoteService crs = new ComunityRemoteService();
		ComunityRemote comnunityremote = crs.querybyDeviceid(getDeviceid());
		
		super.run();

		if ( comnunityremote == null || comnunityremote.getFix() == 1 )
			return ;
		
		crs.delete(comnunityremote);
	}
	
	private void unbindDahuaLeChangeCamera()
	{
		CameraService cs = new CameraService();
		List<Camera> lst = cs.querybydeviceid(super.getDeviceid());
		if ( lst == null || lst.size() == 0 )
			return ;
		
		for ( Camera c : lst)
		{
			if ( CameraProductor.dahualechange.getProductor().equals(c.getProductorid()) )
			{
				PhoneUserService pus = new PhoneUserService() ;
				if (IRemoteConstantDefine.CAMERA_DEVICE_TYPE_ABROAD.equals(c.getDevicetype())) {
					CameraHelper.unbindDahuaLechangeCamera(pus.query(super.getPhoneuserid()), c.getApplianceuuid(), c.getDevicetype());
				} else {
					CameraHelper.unbindDahuaLechangeCamera(pus.query(super.getPhoneuserid()), c.getApplianceuuid());
				}
			}
		}
			
	}
	
	//Delete all association commands that triggered by devices belong to other gateways and target devices belong to this gateway  
	//Delete all scene commands that target devices belong to this gateway
	private void clearSceneCommand()
	{
		SceneService ss = new SceneService();
		CommandService cs = new CommandService();
		
		//all commands that target devices belong to this gateway
		List<Command> lst = cs.querybydeviceid(super.getDeviceid());
		
		for ( Command c : lst )
		{
			if ( c.getScene() == null )
				continue;
			//Delete all scene commands 
			if ( c.getScene().getScenetype() == IRemoteConstantDefine.SCENE_TYPE_SCENE)
				c.getScene().getCommandlist().remove(c);
			else if ( c.getScene().getScenetype() == IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION  // association command 
					&& c.getAssociationscene() != null 
					&& c.getAssociationscene().getZwavedevice() != null 
					&& !super.getDeviceid().equals((c.getAssociationscene().getZwavedevice().getDeviceid())))  // triggered by devices belongs to other gateways 
				c.getScene().getCommandlist().remove(c);
			if ( c.getScene().getCommandlist().size() == 0 )
			{
				ss.delete(c.getScene());
				rmlst.add(c.getScene());
			}
		}
	}
	
	//Delete all association commands that triggered by devices belong to this gateway and target devices belong to other gateways
	//Delete all association commands that triggered by devices belong to this gateway and target is a scene.
	//Delete all scene associations that trigger devices belongs to the gateway 
	private void clearAssociationCommand()
	{
		SceneService ss = new SceneService();
		AssociationsceneService ass = new AssociationsceneService();
		
		//all zwave devices ID of this gateway
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<Integer> zids = zds.queryidbydeviceid(super.getDeviceid());
		
		//all associations triggered by devices belong to this gateway
		List<Associationscene> lst = ass.querybyZwaveDeviceid(zids);
		
		if ( lst == null || lst.size() == 0 )
			return ;
		
		//all infrared devices ID of this gateway
		InfraredDeviceService ids = new InfraredDeviceService();
		List<Integer> iids = ids.queryidbydeviceid( Arrays.asList(new String[]{super.getDeviceid()}));
		
		for ( Associationscene as : lst)
		{
			if ( as.getCommandlist() == null )
				continue;
			
			if ( as.getScenetype() == IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION )
			{
				for ( ListIterator<Command> it = as.getScene().getCommandlist().listIterator() ; it.hasNext() ; )
				{
					Command c = it.next();
					if ( ( c.getZwavedeviceid() != null && !zids.contains(c.getZwavedeviceid()))  // target device is not a zwave device of this gateway
							|| c.getInfrareddeviceid() != null && !iids.contains(c.getInfrareddeviceid()) // target device is not a infrared device of this gateway
							|| c.getScene() != null ) //target is a scene.
						it.remove();
				}
			}
			else if ( as.getScenetype() == IRemoteConstantDefine.SCENE_TYPE_SCENE ) // delete scene association which trigger devices belong to the gateway 
				as.getScene().getAssociationscenelist().remove(as);
				
			if ( as.getScene().getCommandlist().size() == 0 )
			{
				ss.delete(as.getScene());
				rmlst.add(as.getScene());
			}
		}
	}
	
	@Override
	protected String getType() {
		return IRemoteConstantDefine.NOTIFICATION_DELETE_REMOTE;
	}

}
