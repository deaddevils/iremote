package com.iremote.infraredcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {

	public static void main(String arg[])
	{
		String str = "/*  5814Ç°²å/Èë/*52×é */222";
		Pattern pattern = Pattern.compile("/\\*.*\\*/" , Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		
		while (matcher.find()) 
		{
			System.out.println(matcher.group());
		}
	}
}
