package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ShaanxiDahua extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ShaanxiDahua";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 陕西大华(Shaanxi Dahua) 1
"00e047003382328110281f2920291f29652920281f29662820286628662866291f296529652966291f2920286528202866281f281f281f2a1f2965291f28662820286628652966296528899b823c8086280000000000000000000000000000000000000000000000000000000000",
};
}