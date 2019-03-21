package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Marta extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Marta";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ Âí¶ûËþ(Marta) 1
"00e04700338233810f291f2866296529662820286628652920286529202820282028662820281f2866281f291f2965291f2965281f291f292029652965291f2965281f286428662865298992823b8086280000000000000000000000000000000000000000000000000000000000",
};
}