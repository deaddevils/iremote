package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class BESTV extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "BESTV";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ BESTV(BESTV) 1
"00e0470033823481102866291f291f2920286629202820281f281f2966296528652a1f296529662866286528652820291f281f281f2965291f2920281f2867286628652965291f296529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}