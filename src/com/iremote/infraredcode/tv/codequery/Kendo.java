package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Kendo extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Kendo";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Ω£µ¿(Kendo) 1
"00e047003382348110282028202820282028202820282028652866296529652866281f286628652a1f291f2965281f28642820281f282028202865291f2966281f2965286628652965288993823a8086290000000000000000000000000000000000000000000000000000000000",
};
}