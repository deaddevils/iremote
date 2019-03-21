package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Albatron extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Albatron";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” «‡‘∆(Albatron) 1
"00e047003382348110281f291f29202820291f2965281f291f296528662865281f2866286529652965281f291f2a1f291f291f291f281f292028652965296528642865286529652965288994823a8087280000000000000000000000000000000000000000000000000000000000",
};
}