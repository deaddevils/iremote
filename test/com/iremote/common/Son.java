package com.iremote.common;

public class Son extends Father
{
	public static void main(String arg[])
	{
		Father f = new Father();
		f.add("father");
		
		Son s = new Son();
		s.add("son");
		
		f.print();
		s.print();
	}
}
