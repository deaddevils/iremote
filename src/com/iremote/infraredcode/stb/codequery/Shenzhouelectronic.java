package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Shenzhouelectronic extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Shenzhouelectronic";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 神州电子(Shenzhou electronic) 1
"00e04700358233811028202820281f291f2a1f2a1f291f291f2866286628652866286628652a6529652920281f2866286528662820281f281f29652965291f2820282028662866296529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}