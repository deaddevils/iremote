package com.iremote.device.operate.zwavedevice;

import java.util.List;

import com.iremote.infraredtrans.tlv.CommandTlv;

public class OperationTranslatorHelper
{
	public static byte[] createCommand(List<CommandTlv> lst)
	{
		if ( lst == null || lst.size() == 0 )
			return null; 
		
		byte b[][] = new byte[lst.size()][];
		for ( int i = 0 ; i < lst.size() ; i ++ )
			b[i] = lst.get(i).getByte();

		int size = 0 ;
		for ( int i = 0 ; i < b.length ; i ++ )
			size += ( b[i].length - 5 ) ;
		byte command[] = new byte[size];
		int index = 0 ;
		for ( int i = 0 ; i < b.length ; i ++ )
		{
			System.arraycopy(b[i], 4, command, index,  b[i].length - 5);
			index += b[i].length - 5;
		}
		
		return command ;
	}
}
