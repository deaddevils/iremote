package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class SVA extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "SVA";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 上广电(SVA) 1
"00e0370033823581112920281f2963292028632820286428202820282028202820286328202864282028642863296329202864291f2863281f298bc5823d808629000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}