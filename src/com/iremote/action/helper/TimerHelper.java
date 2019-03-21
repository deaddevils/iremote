package com.iremote.action.helper;

import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.common.taskmanager.QueueTaskManager;
import com.iremote.domain.SystemParameter;
import com.iremote.domain.Timer;
import com.iremote.infraredtrans.SceneProcessorRunner;
import com.iremote.scene.TimerExecutor;

public class TimerHelper {
	private static QueueTaskManager<Runnable> scenetaskmgt = new QueueTaskManager<Runnable>(3);

	public static void cancelTimer(List<Timer> lst)
	{
		if ( lst == null )
			return ;
		for ( Timer t : lst)
		{
			if ( t.getExecutor() == IRemoteConstantDefine.DEVICE_TIMER_EXECUTOR_SERVER 
					&& t.getValid() == IRemoteConstantDefine.DEVICE_TIMER_STATUS_VALID )
			{
                if (t.getTimetype() == IRemoteConstantDefine.SCENE_TIME_TYPE_SUNRISE || t.getTimetype() == IRemoteConstantDefine.SCENE_TIME_TYPE_SUNSET) {
                    cancelSunTimer(t);
                } else {
                    ScheduleManager.cancelJob(t.getTimerid(), IRemoteConstantDefine.QUARTZ_GROUP_DEVICE_TIMER);
                }
			}
		}
	}

	public static void addTimer(List<Timer> lst)
	{
		for ( Timer t : lst)
		{
			if ( t.getExecutor() == IRemoteConstantDefine.DEVICE_TIMER_EXECUTOR_SERVER 
					&& t.getValid() == IRemoteConstantDefine.DEVICE_TIMER_STATUS_VALID )
			{
                if (t.getTimetype() == IRemoteConstantDefine.SCENE_TIME_TYPE_SUNRISE || t.getTimetype() == IRemoteConstantDefine.SCENE_TIME_TYPE_SUNSET) {
                    addSunTimer(t);
                } else {
                    //ExecuteMultiDeviceCommand dca = createExecuteDeviceCommand(t);
                    ScheduleManager.excuteTimer(t.getWeekday(), t.getTime(), t.getTimerid(), new TimerExecutor(t.getTimerid()));
                }
			}
		}
	}

	private static void addSunTimer(Timer timer) {
		scenetaskmgt.addTask(String.valueOf(timer.getScenedbid()), new SceneProcessorRunner(System.currentTimeMillis(), timer, true));
	}


	private static void cancelSunTimer(Timer timer) {
		scenetaskmgt.addTask(String.valueOf(timer.getScenedbid()), new SceneProcessorRunner(System.currentTimeMillis(), timer, false));
	}

//	private static ExecuteMultiDeviceCommand createExecuteDeviceCommand(Timer t)
//	{
//		List<CommandTlv> lst = new ArrayList<CommandTlv>();
//		String deviceid = null;
//		if ( t.getInfrareddevice() != null )
//		{
//			deviceid = t.getInfrareddevice().getDeviceid();
//			CommandTlv ct = new CommandTlv(4 , 1);
//			ct.addUnit(new TlvByteUnit(40 , t.getInfraredcode()));
//			lst.add(ct);
//		}
//		else if ( t.getZwavedevice() != null )
//		{
//			deviceid = t.getZwavedevice().getDeviceid();
//			lst = CommandHelper.splitCommand(t.getZwavecommand());
//			
//			for ( CommandTlv ct : lst)
//			{
//				ct.addUnit(new TlvByteUnit(12 , IRemoteConstantDefine.DEVICE_TIMER_TASK_OPERATOR.getBytes()));
//				ct.addUnit(new TlvIntUnit(79 ,6 , 1));
//			}
//		}
//		return new ExecuteMultiDeviceCommand(deviceid , lst.toArray(new CommandTlv[0]));
//	}
}
