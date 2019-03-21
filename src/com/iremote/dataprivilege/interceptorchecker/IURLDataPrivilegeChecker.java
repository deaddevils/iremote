package com.iremote.dataprivilege.interceptorchecker;

import java.util.Map;

public interface IURLDataPrivilegeChecker<T>
{
	void setUser(T user);
	void setParameter(String parameter);
	void SetParameters(Map<String , String> parameters);
	boolean checkprivilege();
}
