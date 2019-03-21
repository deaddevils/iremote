package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Harbincable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Harbincable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¹þ¶û±õÓÐÏß(Harbin cable) 1
"00e047003382338110291f291f291f281f282028202866281f281f291f2a6529652820286628202820286528202965281f291f281f2966281f282028662820286529652965281f286629899b823b8087290000000000000000000000000000000000000000000000000000000000",
};
}