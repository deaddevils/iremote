package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Harbincablea extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Harbincablea";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 哈尔滨有线一(Harbin cable a) 1
"00e047003382358110281f2a1f291f291f291f281f2820282128662865286528662866286528662866281f2866282028662820281f291f29202965281f28652a1f2965296528652965298997823c8086290000000000000000000000000000000000000000000000000000000000",
};
}