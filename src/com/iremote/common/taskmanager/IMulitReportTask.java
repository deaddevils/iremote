package com.iremote.common.taskmanager;

public interface IMulitReportTask extends IStatusfulTask 
{
	boolean merge(IMulitReportTask task);
	boolean isReady();
	boolean isExecuted();
	
}
