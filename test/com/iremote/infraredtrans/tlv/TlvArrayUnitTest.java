package com.iremote.infraredtrans.tlv;

import com.iremote.common.Utils;

public class TlvArrayUnitTest
{

	public static void main(String arg[])
	{
		TlvArrayUnit tau = new TlvArrayUnit(30 * 256 + 7 );
		tau.addUnit(new TlvIntUnit(1 , 1 , 2));
		tau.addUnit(new TlvIntUnit(2 , 2 , 2));
		tau.addUnit(new TlvIntUnit(3 , 3 , 2));
		tau.addOrReplaceUnit(new TlvIntUnit(2 , 4 , 4));
		
		Utils.print("", tau.getByte());
	}
}
