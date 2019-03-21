package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GaussBaer extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GaussBaer";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¸ßË¹±´¶û(Gauss Baer) 1
"00e047003382348110281f2a1f291f291f291f2820286728202820281f29652965281f2966291f2920286528202866281f281f281f2a65291f291f28662820286628652966291f286529899b823b8086280000000000000000000000000000000000000000000000000000000000",
};
}