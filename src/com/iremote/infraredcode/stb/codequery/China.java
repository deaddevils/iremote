package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class China extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "China";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//ЛњЖЅКа Щёжн(China) 1
"00e0470033823481102820281f28652a65291f29202866296628652820286628652965291f291f2920286629202820286629202820291f291f281f29662966281f286728662865296529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}