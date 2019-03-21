package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Zinwell extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Zinwell";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Zinwell(Zinwell) 1
"00e0470033823381102820286628202820281f291f296628662820281f281f2920282028202965281f291f2920292029652865291f2920292029652865296528202820286628652865288996823b8086290000000000000000000000000000000000000000000000000000000000",
};
}