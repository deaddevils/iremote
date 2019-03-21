package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Linn extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Linn";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ¡÷∂˜(Linn) 1
"00e047003382348110281f2920281f291f291f291f296529202866291f2965291f281f2865292029202965286528652866296529652865296528202820281f281f281f291f291f2a1f298996823a8086290000000000000000000000000000000000000000000000000000000000",
};
}