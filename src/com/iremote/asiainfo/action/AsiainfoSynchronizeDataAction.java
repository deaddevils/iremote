package com.iremote.asiainfo.action;

import com.iremote.action.data.SynchronizeDataAction;
import com.iremote.asiainfo.task.devicemanage.DeleteInfraredDeviceTaskProcessor;
import com.iremote.asiainfo.task.devicemanage.DeleteZwaveDeviceTaskProcessor;
import com.iremote.asiainfo.task.devicemanage.AddZwaveDeviceTaskProcessor;
import com.iremote.asiainfo.task.devicemanage.AddInfraredDeviceTaskProcessor;
import com.iremote.asiainfo.task.devicemanage.UnbindRemoteTaskProcessor;
import com.iremote.common.taskmanager.TaskManager;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;

public class AsiainfoSynchronizeDataAction extends SynchronizeDataAction
{
	@Override
	protected void onRemoveRemote(Remote remote)
	{
		TaskManager.addTask(new UnbindRemoteTaskProcessor(remote , phoneuser));
	}
	
	@Override
	protected void onAddZwaveDevice(ZWaveDevice device)
	{
		TaskManager.addTask(new AddZwaveDeviceTaskProcessor(phoneuser , device , shareuses));
	}

	@Override
	protected void onDeleteZwaveDevice(ZWaveDevice device)
	{
		TaskManager.addTask(new DeleteZwaveDeviceTaskProcessor(phoneuser , device , shareuses));
	}
	
	@Override
	protected void onAddInfraredDevice(InfraredDevice device)
	{
		TaskManager.addTask(new AddInfraredDeviceTaskProcessor(phoneuser , device , shareuses));	
	}
	
	@Override
	protected void onDeleteInfraredDevice(InfraredDevice device)
	{
		TaskManager.addTask(new DeleteInfraredDeviceTaskProcessor(phoneuser , device , shareuses));

	}
}
