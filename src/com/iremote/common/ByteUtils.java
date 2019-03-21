package com.iremote.common;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class ByteUtils 
{
	public static String toHexString(byte[] content)
	{
		if ( content == null )
			return null ;
		
		StringBuffer sb = new StringBuffer();
		for ( int i = 0 ; i < content.length ; i ++ )
		{
			String t = Integer.toHexString(content[i] & 0xff);
			if ( t.length() == 1 )
				sb.append("0");
			sb.append(t);
		}
		return sb.toString();
	}
	
	public static byte[] jsonarraytobytearray(String str)
	{
		if ( str == null )
			return null ;
		
		return tobytearray(JSON.parseArray(str));
	}
	
	public static byte[] tobytearray(JSONArray ja)
	{
		if ( ja == null )
			return null;
		
		byte[] bb = new byte[ja.size()];
		for ( int i = 0 ; i < ja.size() ; i ++ )
			bb[i] = ja.getByteValue(i) ;
		return bb; 
	}
	
	public static boolean isByteAryEqual(byte[] key , byte[] cmd )
	{
		if ( key == null && cmd == null )
			return true;
		if ( key == null || cmd == null )
			return false ;
		if ( key.length != cmd.length ) 
			return false ;
		for ( int i = 0 ; i < key.length ; i ++ )
			if ( key[i] != cmd[i] )
				return false ;
		return true ;
	}

	public static byte[] tobytearray(List<Byte> lst)
	{
		if ( lst == null || lst.size() == 0 )
			return new byte[0];
		
		byte[] ba = new byte[lst.size()];
		for ( int i = 0 ; i < lst.size() ; i ++ )
			ba[i] = lst.get(i);
		return ba ;
	}

	public static byte[] addBytes(byte[] data1, byte[] data2) {
		byte[] data3 = new byte[data1.length + data2.length];
		System.arraycopy(data1, 0, data3, 0, data1.length);
		System.arraycopy(data2, 0, data3, data1.length, data2.length);
		return data3;
	}
}
