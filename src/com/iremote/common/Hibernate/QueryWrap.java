package com.iremote.common.Hibernate;

import java.util.List;

public interface QueryWrap {

	public int count();
	public <T> List<T> list();
	public void setFirstResult(int first);
	public void setMaxResults(int maxResult);
}
