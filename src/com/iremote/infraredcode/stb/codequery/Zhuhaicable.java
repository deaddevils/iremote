package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Zhuhaicable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Zhuhaicable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 珠海有线(Zhuhai cable) 1
"00e0470033823281102920281f2965291f291f291f2920282028652965291f286529652866286628652866291f29652965281f281f291f291f2a1f2965281f281f2865296628662865288996823c8086280000000000000000000000000000000000000000000000000000000000",
};
}