package com.iremote.test.unittest.dataprivilege;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.http.HttpUtil;

public class DataPrivilegeTest
{
	@Test
	public void checkUnlockProcess()
	{
		
	}
	
	private void thirdpartprivilegetest(String url , JSONObject parameter)
	{
		HttpUtil.httprequest(url, parameter);
	}
}
