package com.iremote.task.devicereaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.alibaba.fastjson.JSON;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.Associationscene;
import com.iremote.domain.ZWaveDevice;
import com.iremote.scene.SceneExecutor;
import com.iremote.service.AssociationsceneService;

@Deprecated
public class DeviceAssociationSceneTaskProcessor implements Runnable {

	private static Log log = LogFactory.getLog(DeviceAssociationSceneTaskProcessor.class);
	
	private ZWaveDevice device ;
	private int channelid ;
	private int[] status ;
	private long taskid; 
	
	public DeviceAssociationSceneTaskProcessor(ZWaveDevice device,
			int channelid, int status , long taskid) {
		super();
		this.device = device;
		this.channelid = channelid;
		this.status = new int[]{status};
		this.taskid = taskid;
	}

	@Override
	public void run() 
	{
		if ( log.isInfoEnabled())
			log.info(JSON.toJSONString(this));
		status = mapstatus();
		Associationscene as = queryAssociationscene();
		if ( as == null )
			return ;
		
		SceneExecutor se = new SceneExecutor(as.getScenedbid() , null , null ,
				IRemoteConstantDefine.ASSOCIATION_SCENE_TASK_OPERATOR,
				IRemoteConstantDefine.OPERATOR_TYPE_ASSCIATION,
				IRemoteConstantDefine.SCENE_EXECUTE_TYPE_USER_TRIGGER);
		se.run();
		//SceneHelper.executeScene(as.getCommandlist() ,IRemoteConstantDefine.OPERATOR_TYPE_ASSCIATION , IRemoteConstantDefine.ASSOCIATION_SCENE_TASK_OPERATOR );
	}

	public long getTaskIndentify() {
		return taskid;
	}
	
	private Associationscene queryAssociationscene()
	{
		AssociationsceneService svr = new AssociationsceneService();
		return null ; //svr.query(device, channelid, status);
	}
	
	private int[] mapstatus()
	{
		if ( IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(device.getDevicetype())
				|| IRemoteConstantDefine.DEVICE_TYPE_LOCK_CYLINDER.equals(device.getDevicetype()))
		{
			if ( status[0] != 255 )
				return new int[]{0 , 1} ;
		}
		
		return status;
	}
}
