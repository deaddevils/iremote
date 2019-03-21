package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class HarbinSidakang extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "HarbinSidakang";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¹þ¶û±õË¹´ï¿µ(Harbin Sidakang) 1
"00e04700338235810f28202820281f291f2965291f291f281f29662a65296528652a1f29652965296629652965281f291f291f291f2920281f281f2820286628662865286529662865288999823b8087280000000000000000000000000000000000000000000000000000000000",
};
}