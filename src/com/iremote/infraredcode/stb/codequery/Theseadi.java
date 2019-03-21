package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Theseadi extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Theseadi";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ º£ÃÀµÏ(The sea di)-HD600A 1
"00e047003382358110291f29202820282028662820281f291f2966296529662966281f2867286628662965291f296529662966281f2867282028202865291f2a1f291f29652820286728899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}