package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ChinaTelecomIhaveEhome extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ChinaTelecomIhaveEhome";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 中国电信我有E家(China Telecom I have E home) 1
"00e0470033823481122820282028202820281f281f281f291f29662865286728662865286628652865291f291f296528672866281f2920296629652965291f2920281f28672866282028899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}