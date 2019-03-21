package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class TheNorthSea extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "TheNorthSea";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ±±º£(The North Sea) 1
"00e04700338232810f281f28202820282028202820282028642865286628652866286529652965281f291f2a65291f291f2865291f291f2a652965281f296528662820286528652820288996823b8086280000000000000000000000000000000000000000000000000000000000",
};
}