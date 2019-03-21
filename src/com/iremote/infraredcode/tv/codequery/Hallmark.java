package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Hallmark extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Hallmark";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 标志(Hallmark) 1
"00e0470033823381102820282029652820281f281f2920292028662865291f2866296529662866286529202820281f2865291f291f291f281f286529652865291f2965286528652965298993823b8087280000000000000000000000000000000000000000000000000000000000",
};
}