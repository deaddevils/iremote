package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Jiashancable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Jiashancable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¼ÎÉÆÓÐÏß(Jiashan cable) 1
"00e047003382358110291f291f2920281f281f28202820282029652865296529652867286628662866281f281f286529652966281f2820282028662965291f291f281f29662966286528899c823b8086280000000000000000000000000000000000000000000000000000000000",
};
}