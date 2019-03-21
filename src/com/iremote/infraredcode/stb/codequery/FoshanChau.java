package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class FoshanChau extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "FoshanChau";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ·ðÉ½Í¬ÖÞ(Foshan Chau) 1
"00e047003382358111281f292028202820281f291f2920291f29652866296628652866286628652966291f281f28662966286528202820282028652965291f291f292028662866286629899c823b8087290000000000000000000000000000000000000000000000000000000000",
};
}