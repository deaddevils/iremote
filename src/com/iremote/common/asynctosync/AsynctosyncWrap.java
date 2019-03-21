package com.iremote.common.asynctosync;
@Deprecated
public class AsynctosyncWrap implements IAsynctosync {

	private IAsynctosync obj;
	private boolean autodelete = false ;

	public AsynctosyncWrap(IAsynctosync obj) {
		super();
		this.obj = obj;
	}

	@Override
	public long getTimeoutMilliseconds() 
	{
		return obj.getTimeoutMilliseconds();
	}

	@Override
	public String getkey() 
	{
		return obj.getkey();
	}

	@Override
	public void sendRequest() throws Exception 
	{
		obj.sendRequest();
	}

	public boolean isAutodelete() {
		return autodelete;
	}

	public void setAutodelete(boolean autodelete) {
		this.autodelete = autodelete;
	}
}
