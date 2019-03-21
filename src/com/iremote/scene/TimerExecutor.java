package com.iremote.scene;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.Timer;
import com.iremote.service.TimerService;

public class TimerExecutor implements Runnable
{
	private int timerid ;
	
	public TimerExecutor(int timerid)
	{
		super();
		this.timerid = timerid;
	} 

	@Override
	public void run()
	{
		TimerService ts = new TimerService();
		Timer t = ts.query(timerid);
		if ( t == null )
			return ;
		if ( t.getExecutor() != IRemoteConstantDefine.DEVICE_TIMER_EXECUTOR_SERVER)
			return ;
		if ( t.getZwavedevice() != null 
				&& t.getZwavedevice().getEnablestatus() != IRemoteConstantDefine.DEVICE_ENABLE_STATUS_ENABLE )
			return ;
		if ( t.getScene() == null )
			return ;
		SceneExecutor se = new SceneExecutor(t.getScene().getScenedbid() , null , null ,
				IRemoteConstantDefine.DEVICE_TIMER_TASK_OPERATOR,
				IRemoteConstantDefine.OPERATOR_TYPE_TIMER,
				IRemoteConstantDefine.SCENE_EXECUTE_TYPE_TIMER);
		se.run();
	}

}
