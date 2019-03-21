package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Bork extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Bork";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ≤©øÀ(Bork) 1
"00e047003582328110281f291f292029202820291f281f281f28642865286529652965286529202866282028202820281f2866281f291f291f2965296528642865281f296529652864288992823a8086280000000000000000000000000000000000000000000000000000000000",
};
}