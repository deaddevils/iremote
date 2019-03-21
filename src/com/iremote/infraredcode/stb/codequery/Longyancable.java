package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Longyancable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Longyancable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÁúÑÒÓÐÏß(Longyan cable) 1
"00e04700338234810f291f28202820282028202820282028652966286628652866286628652866281f291f2966281f282028662820282028652865282028662865282028662866281f288998823c8086280000000000000000000000000000000000000000000000000000000000",
};
}