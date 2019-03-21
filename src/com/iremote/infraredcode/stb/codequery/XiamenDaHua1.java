package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class XiamenDaHua1 extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "XiamenDaHua1";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 夏门大华(Xiamen Da Hua) 1
"00e04700338234811028652a1f291f291f281f2820282028202866281f29652965296628652965286728202820286529652965292028662966286528662820281f281f2965291f291f29899c823c8086290000000000000000000000000000000000000000000000000000000000",
};
}