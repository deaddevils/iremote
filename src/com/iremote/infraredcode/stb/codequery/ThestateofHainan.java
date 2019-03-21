package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ThestateofHainan extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ThestateofHainan";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 海南州(The state of Hainan) 1
"00e0470033823481112820291f291f291f29202820281f292028662865286628652a652965296628662820281f2865286628652a1f291f291f2965286728202820281f28652a65296529899b823b8086280000000000000000000000000000000000000000000000000000000000",
};
}