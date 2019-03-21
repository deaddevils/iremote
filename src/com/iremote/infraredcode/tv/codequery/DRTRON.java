package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class DRTRON extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "DRTRON";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” DRTRON(DRTRON) 1
"00e049003481198116291f296429642964291e281e291e291e2820286429642864281e291e291e291f291e28202864291e29632820281f281f28632964281f2864291f296328632964288b4281208114286229000000000000000000000000000000000000000000000000000000",
};
}