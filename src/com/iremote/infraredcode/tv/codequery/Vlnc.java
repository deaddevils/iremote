package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Vlnc extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Vlnc";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Vlnc(Vlnc) 1
"00e0470033823481102964281f282028202820282028202820282028642866296529652865296428652965291f292029202820281f291f291f291f2965286529652865296529652865298993823b8086290000000000000000000000000000000000000000000000000000000000",
};
}