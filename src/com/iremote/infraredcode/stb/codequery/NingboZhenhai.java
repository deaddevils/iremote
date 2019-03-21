package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class NingboZhenhai extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "NingboZhenhai";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ Äþ²¨-Õòº£(Ningbo Zhenhai) 1
"00e0470033823581102a1f29202820281f282028202820281f29652965286528652965286628662866292029202965286529652920281f282028662866281f291f2a1f29652966286529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}