package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Sylvania extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Sylvania";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 西尔韦尼亚(Sylvania) 1
"00e04700338233810f291f291f2865281f291f29202820286628202820281f281f2920286529652965281f281f291f291f292029652820281f28642866286529652865281f28652866298993823b8087290000000000000000000000000000000000000000000000000000000000",
};
}