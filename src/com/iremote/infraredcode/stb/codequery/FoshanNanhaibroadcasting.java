package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class FoshanNanhaibroadcasting extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "FoshanNanhaibroadcasting";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 佛山南海广电(Foshan Nanhai broadcasting) 1
"00e04700338233811128202820281f281f291f2a1f291f291f29652867286628652866286528652965292028202867286628652820291f291f286529652920281f282028662866286529899c823c80862a0000000000000000000000000000000000000000000000000000000000",
};
}