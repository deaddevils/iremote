package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Tianjin1 extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Tianjin1";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ���(Tianjin) 1
"00e0470033823481102820281f281f2865291f291f291f2820282028662866291f29202a652965286529652866282028202820281f2865291f291f291f28662866286528652820296528899b823c8086290000000000000000000000000000000000000000000000000000000000",
//������ ���(Tianjin) 2
"00e04700338234811128202820281f28652a1f291f291f291f282028672866281f291f2966296528652965296528202820282028202865291f2a1f291f29652866296628662820286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}