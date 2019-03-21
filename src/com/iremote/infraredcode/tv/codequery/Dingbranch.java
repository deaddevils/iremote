package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Dingbranch extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Dingbranch";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ∂¶ø∆(Ding branch) 1
"00e04700338234810f292028202820281f291f2965291f291f28652964286629652965281f28642866281f2965291f291f2865281f291f29202866282028652965281f286529662865298992823b8086280000000000000000000000000000000000000000000000000000000000",
};
}