package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Aihuabroadcasting extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Aihuabroadcasting";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 爱华广电(Aihua broadcasting) 1
"00e04700338234811028202a1f291f29652820281f2966281f2865286628652a1f29652965286629202820286629202866281f281f291f291f2966281f28672820286628652a65296528899b823c8086280000000000000000000000000000000000000000000000000000000000",
};
}