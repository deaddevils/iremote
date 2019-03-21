package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Jensen extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Jensen";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” —”…≠(Jensen) 1
"00e047003382348111281f291f291f292028202820291f2865281f2965286529652965286529662820286628652866291f2965291f291f281f2920291f291f2965291f286528652965298994823a8086280000000000000000000000000000000000000000000000000000000000",
};
}