package com.iremote.asiainfo.helper;

import java.io.UnsupportedEncodingException;

public class ByteWrap 
{
	private byte[] content;
	private int index = 0 ;
	
	public ByteWrap(int size)
	{
		if ( size == 0 ) 
			size = 512 ;
		content = new byte[size];
	}
	
	public ByteWrap(byte[] content)
	{
		this.content = content;
	}
	
	public void appendbyte(byte value)
	{
		content[index++] = value;
	}
	
	public byte getbyte()
	{
		return content[index++];
	}
	
	public void appendint(int value , int length)
	{
//		for (int i = 0; i < length; i++) {
//			content[index++] = (byte) (value >>> ((length - i) * 8));
//		}
		
		int ix = index + length - 1 ;
		for ( int i = 0 ; i < length ; i ++ )
		{
			content[ix--] = (byte)( value % 256 ) ;
			value /= 256 ;
		}
		
		index += length;
	}
	
	public int getint(int length)
	{
		int value = 0 ;
		for ( int i = 0 ; i < length ; i ++ )
		{
			value <<= 8 ;
			value |= ( content[index++] & 0xff);
		}
		return value;
	}
	
	public void appendString(String value , int length)
	{
		if ( value == null )
			value = "";
		try {
			byte[] b = value.getBytes("GBK");
			appendbytes(b , length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public String getString(int length)
	{
		if ( length == 0 )
			return "";
		byte[] b = getbytes(length);
		int i = 0 ;
		for ( ; i < b.length && b[i] == 0 ; i ++ );
		int j = i ; 
		for ( ; j < b.length && b[j] != 0 ; j ++ );
		
		if ( i != 0 || j != b.length )
		{
			byte[] bb = new byte[j - i];
			System.arraycopy(b, i, bb, 0, j - i );
			b = bb;
		}
		try {
			return new String(b , "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	public void appendbytes(byte[] value , int length)
	{
		if ( value == null )
			value = new byte[0] ;
		
		if ( length == 0 )
		{
			System.arraycopy(value, 0, content, index, value.length);
			length = value.length ;
		}
		else if ( length > value.length )
		{
			int ix = index ;
			for( int i = 0 ; i < value.length - length ; i ++ )
				content[ix++] = 0 ;
			System.arraycopy(value, 0, content, ix , value.length);
		}
		else 
		{
			System.arraycopy(value, 0, content, index, length);
		}
		index += length;
	}
	
	public byte[] getbytes(int length)
	{
		byte[] b = new byte[length];
		System.arraycopy(content, index, b, 0, length);
		index += length;
		return b ;
	}
	
	public byte[] getContent()
	{
		byte[] b = new byte[index];
		System.arraycopy(content, 0, b, 0, index);
		return b ;
	}
}
