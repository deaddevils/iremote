package com.iremote.test.data;

import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;

public class ZWaveCommandCreater {

	public static void main(String arg[])
	{

		byte[] b = createReport();
		
		for ( int i = 0 ; i < b.length ; i ++ )
			System.out.print(String.format("(byte)%d," , b[i] & 0xff))  ;
	}
	
	public static byte[] createReport()
	{
		int nuid = 3 ;
		byte[] command = new byte[]{(byte)156,2,8,2,(byte)255,0,0};
		byte[] time = new byte[]{87,(byte)130,(byte)242,(byte)66};
		
		CommandTlv ct = new CommandTlv(30 , 9);
		
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		ct.addUnit(new TlvByteUnit(70 , command));
		ct.addUnit(new TlvByteUnit(104 , time));
		return ct.getByte();
	}
	
	public static int getnuIdLenght(int nuid)
	{
		if ( nuid >= 10000 )
			return 4 ;
		return 1 ;
	}
}
