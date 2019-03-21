package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Insiginia extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Insiginia";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Insiginia(Insiginia) 1
"00e04700338232810f291f2865296528202820291f281f28652965282028652920281f28202820282028652965296528652920282028202820281f281f281f291f2966286628652866288993823c8086280000000000000000000000000000000000000000000000000000000000",
};
}