package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ZunyiNetcom extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ZunyiNetcom";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 遵义金网通(Zunyi Netcom) 1
"00e04700338232810f281f291f291f292028202820291f281f286428662865296528662865296628652920286529202865291f281f281f281f2a652820286628202865286629652865298992823a8086280000000000000000000000000000000000000000000000000000000000",
};
}