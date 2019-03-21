package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Nanhaibroadcasting extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Nanhaibroadcasting";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 南海广电(Nanhai broadcasting) 1
"00e047003382358110281f29202920291f291f281f281f291f286529652865286529662965286529662820282028652864286628202820281f2865286628202820281f286528662865288996823c8086280000000000000000000000000000000000000000000000000000000000",
};
}