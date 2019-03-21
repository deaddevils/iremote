package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Sinudyne extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Sinudyne";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Sinudyne(Sinudyne) 1
"00e04700338232810f291f291f291f291f291f291f281f28652965286529652965281f2865296628202820286528202866291f291f291f291f2965281f286428202865296529652865298992823a8086290000000000000000000000000000000000000000000000000000000000",
};
}