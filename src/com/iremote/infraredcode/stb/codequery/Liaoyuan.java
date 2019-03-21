package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Liaoyuan extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Liaoyuan";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÁÉÔ´(Liaoyuan) 1
"00e0470033823481112866281f281f291f291f291f291f28202867282028662965296528652965296528202820286628662965291f29652865296529652820282028202866291f291f2a899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}