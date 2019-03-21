package com.iremote.infraredtrans.serianet;

import com.iremote.common.taskmanager.IMulitReportTask;

public abstract class SeriaNetReportBaseProcessor implements ISeriaNetReportProcessor {

    protected SeriaNetReportBean seriaNetReportBean;
    protected boolean finished = false;
    protected boolean executed = false;
    protected boolean initSucceed = true;

    @Override
    public void run() {
        executed = true;
        init();
        if (initSucceed){
            updateDeviceStatus();
            pushMessage();
            writeLog();
        }
        finished = true;
    }

    @Override
    public void setReport(SeriaNetReportBean seriaNetReportBean) {
        this.seriaNetReportBean = seriaNetReportBean;
    }

    @Override
    public boolean merge(IMulitReportTask task) {
        return false;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public boolean isExecuted() {
        return executed;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

	public long getTaskIndentify()
	{
		return seriaNetReportBean.getReportid();
	}

    protected abstract void updateDeviceStatus();

    protected abstract void pushMessage();

    protected abstract void writeLog();

    protected abstract void init();
}
