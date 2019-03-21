package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Intertronic extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Intertronic";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Intertronic(Intertronic) 1
"00e04700338234811028202820281f282028202820281f291f29202865296628652965286529652865281f291f296528652965291f2865291f29662865291f291f291f2866281f2a65288993823c8086280000000000000000000000000000000000000000000000000000000000",
};
}