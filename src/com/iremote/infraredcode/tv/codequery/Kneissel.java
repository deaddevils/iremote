package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Kneissel extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Kneissel";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Kneissel(Kneissel) 1
"00e04700338232810f29662a1f2965291f291f281f281f2865292028662865291f2920282028662a1f291f2965281f281f28642820282028202866281f286528662a1f296529652865288994823a8086290000000000000000000000000000000000000000000000000000000000",
};
}