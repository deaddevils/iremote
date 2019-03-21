package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ApexDigital extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ApexDigital";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 先端数字(Apex Digital) 1
"00e04700338233810f281f29202820281f281f282028662820286528652866296529652865291f2a65291f2865291f291f2966291f281f281f2865292028662866281f296528662865288995823a8086290000000000000000000000000000000000000000000000000000000000",
};
}