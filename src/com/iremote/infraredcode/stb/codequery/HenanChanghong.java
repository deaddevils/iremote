package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class HenanChanghong extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "HenanChanghong";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 河南长虹(Henan Changhong) 1
"00e0470033823481112866291f281f281f291f281f291f2920286528202866286529652965296628652920281f286628662865291f2a65296528662966281f282129202866281f281f28899b823b8086290000000000000000000000000000000000000000000000000000000000",
};
}