package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Zibocablefour extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Zibocablefour";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 淄博有线四(Zibo cable four) 1
"00e047003382358110291f291f291f281f2820282028202820286529652965296628662865286628662865296529652966291f29202865282028202820291f281f28652965291f286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}