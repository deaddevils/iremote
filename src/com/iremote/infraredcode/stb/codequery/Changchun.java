package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Changchun extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Changchun";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ³¤´º(Changchun) 1
"00e04700338234811028652a1f291f291f281f2820282028202866281f29652965296628652965286728202820286529652965292028662966286528662820281f281f2965291f291f29899c823c8086280000000000000000000000000000000000000000000000000000000000",
};
}