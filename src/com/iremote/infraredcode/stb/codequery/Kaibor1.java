package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Kaibor1 extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Kaibor1";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¿ª²©¶û(Kai bor)-K360i 1
"00e0470033823481112820291f291f291f2920282028662820281f281f29202820291f291f2966281f2966281f28672866281f281f2965291f291f2966281f2820286628652820296528899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}