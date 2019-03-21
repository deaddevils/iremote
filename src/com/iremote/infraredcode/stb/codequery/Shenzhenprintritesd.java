package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Shenzhenprintritesd extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ShenzhenPrintRiteSD";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ������������(Shenzhen Print-Rite SD) 1
"00e0370035823281102820282028642820286329202964291f2a1f2a1f291f291f296328202863281f28642964296329202863281f2963291f298bbd823a808528000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ ������������(Shenzhen Print-Rite SD) 2
"00e0370033823281102820281f2863291f2963291f2863281f281f291f29202820286428202864281f286328642863291f2963291f29632820288bbf823b808628000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}