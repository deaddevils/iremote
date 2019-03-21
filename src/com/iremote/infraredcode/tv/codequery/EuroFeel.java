package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class EuroFeel extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "EuroFeel";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ Å·Ôª(Euro-Feel) 1
"00e049003481198114291f296428642964291e281f281e291e2820286428632964281e281f281f281e281e281f2964291e2864281e281f281f2864286328202864291f286329642864288b3f811f8114286129000000000000000000000000000000000000000000000000000000",
};
}