package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class KunmingPutian extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "KunmingPutian";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ À¥Ã÷ÆÕÌì(Kunming Putian) 1
"00e0470033823381102a1f281f2920281f281f2920282028202865286628652a65296529662866286528202920286628652a65291f291f2820286728662820291f29202a652965296529899b823d8086290000000000000000000000000000000000000000000000000000000000",
};
}