package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class MusicRadio extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "MusicRadio";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 民乐广电(Music Radio) 1
"00e04700358232810f2965281f281f281f291f29202a1f291f291f286528652966286628652866286529652865281f291f292028202820291f281f281f2865296628662865296629652a8994823b8085280000000000000000000000000000000000000000000000000000000000",
};
}