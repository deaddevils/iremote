package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Ganzhou extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Ganzhou";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ����(Ganzhou) 1
"00e0470033823581102a65281f281f292028202820281f29202920291f291f296628652965286728662865291f2a1f291f291f2920282028202820286628652966296529652965296628899b823b8087280000000000000000000000000000000000000000000000000000000000",
//������ ����(Ganzhou) 2
"00e047003582338110296529202820282028202820281f281f291f2a1f291f296528662866286528662866281f281f2a1f291f291f291f2820281f286628652965296528652865296528899c823c8086280000000000000000000000000000000000000000000000000000000000",
};
}