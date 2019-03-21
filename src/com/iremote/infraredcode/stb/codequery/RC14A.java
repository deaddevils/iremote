package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class RC14A extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "RC14A";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ RC-14A(RC-14A) 1
"00e047003582338111282028202865291f2a1f291f291f2920282028662820286529652965281f2965291f2920286528662866281f291f2a1f29652966281f2920282028662966296528899b823c8086280000000000000000000000000000000000000000000000000000000000",
};
}