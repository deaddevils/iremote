package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ZTEZTE extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ZTEZTE";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ZTEÖÐÐË(ZTE ZTE) 1
"00e04700338234811128202820291f291f281f2920281f2920286528662866286529652965286528652920281f282129202820291f2965291f296529652866286628662966291f286528899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}