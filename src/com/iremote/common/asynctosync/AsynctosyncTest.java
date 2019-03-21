package com.iremote.common.asynctosync;
@Deprecated
public class AsynctosyncTest implements IAsynctosync {

	public static void main(String arg[])
	{
		Thread td = new Thread(new Runnable(){
			@Override
			public void run()
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				AsynctosyncManager.getInstance().onResponse("AsynctosyncTest1", "AsynctosyncTest1");
				AsynctosyncManager.getInstance().onResponse("AsynctosyncTest2", "AsynctosyncTest2");
				AsynctosyncManager.getInstance().onResponse("AsynctosyncTest2", "AsynctosyncTest2222");
			}});
		td.start();
		
		AsynctosyncTest t = new AsynctosyncTest();
		t.test();
		

	}
	
	public void test()
	{
		try {
			Object rst = AsynctosyncManager.getInstance().execute(this);
			System.out.println(rst);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendRequest() throws Exception 
	{
		
	}

	@Override
	public String getkey() {
		return "AsynctosyncTest2";
	}

	@Override
	public long getTimeoutMilliseconds() {
		return 2000;
	}
}
