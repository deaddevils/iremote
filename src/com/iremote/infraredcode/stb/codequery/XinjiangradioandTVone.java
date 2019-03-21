package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class XinjiangradioandTVone extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "XinjiangradioandTVone";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 新疆广电一(Xinjiang radio and TV one) 1
"00e047003582338110291f291f2966281f292028202866292028662865291f2965296528202820286628652966291f2965291f291f291f2920281f281f2966291f286529652965296628899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}