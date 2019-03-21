package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Tailaicable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Tailaicable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 泰来有线(Tailai cable) 1
"00e047003582338110281f2920282028202820286428202820286628652865281f29662865286629652965286529652820291f291f2865291f29202a1f291f29652865296628202866288996823b8086280000000000000000000000000000000000000000000000000000000000",
//机顶盒 泰来有线(Tailai cable) 2
"00e047003582338110282028202820291f281f286529202920286628662865282028652965296629652a6529652864281f292028202965281f281f291f291f2a652965286428202866288996823c8086290000000000000000000000000000000000000000000000000000000000",
};
}