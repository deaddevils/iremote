package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Evolution extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Evolution";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” —›ªØ(Evolution) 1
"00e047003582328110291f281f281f291f291f2a652866282028652866296529652865281f2820286529652965281f281f281f291f2a1f2a1f291f291f286528642865296529652865288993823b8087280000000000000000000000000000000000000000000000000000000000",
};
}