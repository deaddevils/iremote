package com.iremote.common;

public class RGBHSVTest 
{
	public static void main(String arg[])
	{
		RGBHSV rh = RGBHSV.rgbtrans((byte)254,(byte) 94, (byte)158, (byte)68);
		
		System.out.println(rh.getR());
		System.out.println(rh.getG());
		System.out.println(rh.getB());
	}
}
