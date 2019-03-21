package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class DigitalTV extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "DigitalTV";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 数字电视(Digital TV) 1
"00e04700358233811028202820281f281f2a1f2a1f291f291f2866286628652865286628652a652965292028202866282028652920291f291f29652965291f2965282028662866296529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}