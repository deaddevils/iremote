package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Suqiancable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Suqiancable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ËÞÇ¨ÓÐÏß(Suqian cable) 1
"00e04700338234811029662820281f281f29202820282028202866281f286628652965296528652866281f291f296529652865291f2966286529652966281f291f2a1f29652820281f288993823c8086280000000000000000000000000000000000000000000000000000000000",
};
}