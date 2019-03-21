package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Addison extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Addison";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ∞¨µœ…˙(Addison) 1
"00e04700338234811028652965296529202820281f2965291f291f292028202820281f291f291f2920281f28652820281f291f29202820281f2866281f286529652865286529662866288995823b8086280000000000000000000000000000000000000000000000000000000000",
};
}