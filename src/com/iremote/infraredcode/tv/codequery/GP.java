package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GP extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GP";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ½ð±Ò(GP) 1
"00e04700338234810f281f2866291f2865291f291f291f281f281f281f291f29202920291f291f281f281f286529202820286628202820281f2866281f28652965281f286428642866298993823b8087280000000000000000000000000000000000000000000000000000000000",
};
}