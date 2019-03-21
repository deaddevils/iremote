package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Zibocable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Zibocable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ×Í²©ÓÐÏß(Zibo cable) 1
"00e047003382358110291f29202820281f281f29202820291f2965296529652866286628662965286628652a6529652866282028202866291f2920291f291f291f296629662820286528899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}