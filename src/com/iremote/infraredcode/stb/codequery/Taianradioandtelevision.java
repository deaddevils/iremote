package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Taianradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Taianradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ̩�����(Taian radio and television) 1
"00e047003382348110281f291f291f291f2920281f281f281f296629652865296529652866296628662820282029652865296529202820282028662866291f2920291f29652966296529899b823c8087280000000000000000000000000000000000000000000000000000000000",
//������ ̩�����(Taian radio and television) 2
"00e047003382348110281f29202a1f291f291f2920282028202866286629662965286529652965286629202820286629662965281f2a1f291f2965286628202820282028652965296528899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}