package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class RC8AZhuhai extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "RC8AZhuhai";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ RC-8A Öéº£(RC-8A Zhuhai) 1
"00e0470033823481102820281f2965291f291f291f292028202866286528202966296528652965296528652920286528662820281f281f281f291f29652920281f286728662865296529899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}