package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Haidong extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Haidong";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ º£¶«(Haidong) 1
"00e0470033823481112820291f291f281f2920281f281f292028652865286628652a652965296629662820281f2866286628652a1f291f291f2965286728202820281f2965296528652a899b823b8086280000000000000000000000000000000000000000000000000000000000",
};
}