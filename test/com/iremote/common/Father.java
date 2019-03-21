package com.iremote.common;

import java.util.ArrayList;
import java.util.List;

public class Father
{
	private static List<String> lst = new ArrayList<String>();
	
	protected void add(String str)
	{
		lst.add(str);
	}
	
	public void print()
	{
		for ( String str : lst )
			System.out.println(str);
	}
	
}
