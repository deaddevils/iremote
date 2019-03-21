package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Curtis extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Curtis";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ¿ÂµÙË¹(curtis) 1
"00e047003382338110281f292028202865291f2920281f282028662a65286628202865286528652966286529652965281f2964282028652965291f281f281f286428202865291f291f298992823b8086290000000000000000000000000000000000000000000000000000000000",
};
}