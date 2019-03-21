package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class WycombeVC9030RC extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "WycombeVC9030RC";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ Íþ¿ÆÄ·VC-9030RC(Wycombe VC-9030RC) 1
"00e047003382348111286628652865291f2965286629662820281f292029202965281f2920281f2966281f281f28202866281f281f2865291f296528662866282028652966291f28652a899b823b80862a0000000000000000000000000000000000000000000000000000000000",
};
}