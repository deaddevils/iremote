package com.iremote.common.taskmanager;

public interface IStatusfulTask extends Runnable 
{
	boolean isFinished();
}
