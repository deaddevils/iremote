package com.iremote.device;

import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.Associationscene;
import com.iremote.domain.Camera;
import com.iremote.domain.RoomAppliance;
import com.iremote.domain.UserShareDevice;
import com.iremote.domain.ZWaveDeviceShare;
import com.iremote.service.AssociationsceneService;
import com.iremote.service.RoomApplianceService;
import com.iremote.service.SceneService;
import com.iremote.service.UserShareDeviceService;
import com.iremote.service.UserShareService;
import com.iremote.service.ZWaveDeviceShareService;

public class ClearCamera
{
	private Camera camera;

	public ClearCamera(Camera camera)
	{
		super();
		this.camera = camera;
	}
	
	public void clear() 
	{
		clearRoomAppliance();
		//clearSceneCommand();
		clearUserShare();
		clearDeviceShare();
		clearAssociationScene();
	}
	
	public void clearAssociationScene()
	{
		AssociationsceneService ass = new AssociationsceneService();
		List<Associationscene> lst = ass.querybyCameraid(camera.getCameraid());
		
		if ( lst == null || lst.size() == 0 )
			return ;
		
		SceneService ss = new SceneService();
		for ( Associationscene as : lst )
		{
			if ( as.getScene() == null )
			{
				ass.delete(as);
				continue;
			}
			if ( as.getScenetype() == IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION)
				ss.delete(as.getScene());
			else if ( as.getScene().getAssociationscenelist() != null )
				as.getScene().getAssociationscenelist().remove(as);
		}
	}
	
	public void clearRoomAppliance()
	{
		RoomApplianceService ras = new RoomApplianceService();
		RoomAppliance ra = ras.querybyCameraid(camera.getCameraid());
		
		if ( ra != null )
			ras.delete(ra);
	}
	
	public void clearUserShare()
	{
		UserShareService uss = new UserShareService();
		UserShareDeviceService usds = new UserShareDeviceService();
		List<UserShareDevice> userShareDevices = usds.queryCameraid(camera.getCameraid());
		if(userShareDevices != null)
			for(UserShareDevice usd : userShareDevices)
			{
				usd.getUserShare().getUserShareDevices().remove(usd);
				if ( usd.getUserShare().getUserShareDevices().size() == 0 )
					uss.delete(usd.getUserShare());
			}
	}
	
	public void clearDeviceShare()
	{
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		
		List<ZWaveDeviceShare> lst = zdss.querybyCameraid(camera.getCameraid());
		zdss.delete(lst);
	}
}
