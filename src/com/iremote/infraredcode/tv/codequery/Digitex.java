package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Digitex extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Digitex";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Digitex(Digitex) 1
"00e047003382348110282028202820281f28652820281f291f29652965286529662820286628652866296528662866281f28652820286529652820281f281f28652920286529202820288994823c8087280000000000000000000000000000000000000000000000000000000000",
};
}