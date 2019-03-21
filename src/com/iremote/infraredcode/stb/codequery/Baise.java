package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Baise extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Baise";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ °ÙÉ«(Baise) 1
"00e04700338235810f281f281f2820282028202820282028642865286628652865286629652965281f281f2a65291f291f2965281f291f2a652965281f286529662820286628652820288996823b8086280000000000000000000000000000000000000000000000000000000000",
};
}