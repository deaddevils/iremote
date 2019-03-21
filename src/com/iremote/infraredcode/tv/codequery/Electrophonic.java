package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Electrophonic extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Electrophonic";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” µÁœÏ(Electrophonic) 1
"00e047003382338110281f296628662865281f28652965282028662820281f281f2866281f291f2965291f281f2864281f2965281f291f292028652865281f29652820286628652965288992823b8086280000000000000000000000000000000000000000000000000000000000",
};
}