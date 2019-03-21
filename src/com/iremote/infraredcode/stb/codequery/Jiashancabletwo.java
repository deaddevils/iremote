package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Jiashancabletwo extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Jiashancabletwo";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 嘉善有线二(Jiashan cable  two) 1
"00e0470033823481102820281f281f291f2a1f291f291f281f286628662866296629652865296529652820281f286628662966291f281f281f29652965281f2820282028662865296529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}