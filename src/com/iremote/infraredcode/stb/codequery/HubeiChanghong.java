package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class HubeiChanghong extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "HubeiChanghong";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ºþ±±³¤ºç(Hubei Changhong) 1
"00e047003382348110286628202820281f28202820282028202866282028652866296529652865296528202820286628652866291f29652965286529652820291f281f2865291f2920288995823b8085280000000000000000000000000000000000000000000000000000000000",
};
}