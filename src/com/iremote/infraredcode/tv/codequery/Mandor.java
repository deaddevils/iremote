package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Mandor extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Mandor";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 门多尔(Mandor) 1
"00e049003481188116281e286429642963281f281f281f281e281f296329642864291f281e291e281e291e2a1e296328202864291e281e2a1e28632864281e2964291e296428642963298b40811f8115286229000000000000000000000000000000000000000000000000000000",
};
}