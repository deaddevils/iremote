package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Huaxiandigital extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Huaxiandigital";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ »¬ÏØÊý×Ö(Huaxian digital) 1
"00e047003382348110291f2920281f291f29202820281f281f2966296528652965296528672866286628202866281f2865291f291f291f2820286728202866291f296629652966296529899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}