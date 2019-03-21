package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Kaibor extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Kaibor";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¿ª²©¶û(Kai bor)F4F9 1
"00e0470033823581102a1f281f292028652920282028662820286628652a65291f296528662966281f28202966291f28652a1f291f291f291f286528202866281f28652a652965286529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}