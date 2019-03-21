package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class NingboDVB extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "NingboDVB";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ Äþ²¨DVB(Ningbo DVB) 1
"00e047003382348110281f2a1f291f291f291f2820282128202866286529662965296629662865286728202866291f2966291f281f2920281f2866282028662820286628652a65296528899c823c8086290000000000000000000000000000000000000000000000000000000000",
};
}