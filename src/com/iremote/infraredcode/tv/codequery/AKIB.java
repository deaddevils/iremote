package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class AKIB extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "AKIB";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ °¢¼ª(AKIB) 1
"00e04700338234810f281f28202820282028202820281f2865292029662866286528662865296529202820281f281f296529652920281f281f2965296528652a1f291f296528652865298996823b8087280000000000000000000000000000000000000000000000000000000000",
};
}