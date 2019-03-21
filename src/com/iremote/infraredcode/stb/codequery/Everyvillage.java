package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Everyvillage extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Everyvillage";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 村村通(Every village) 1
"00e047003582338110291f291f2966286628202820286528662865291f29652965286629202820281f28652966296529652965296628662865281f29202820291f291f29202820281f29899b823b8087290000000000000000000000000000000000000000000000000000000000",
};
}