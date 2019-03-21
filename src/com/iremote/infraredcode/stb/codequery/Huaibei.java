package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Huaibei extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Huaibei";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ����(Huaibei) 1
"00e03700358232810f2920282028202863291f291f2963291f281f281f281f291f2964282028642820286329632964281f2963292028642820288bbf823a808628000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ ����(Huaibei) 2
"00e037003382348110281f291f29202864282028202864281f282028202820281f2963291f2963291f2863286329632920286329202863291f298bbf823b808628000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ ����(Huaibei) 3
"00e037003382348110282028202963281f2863281e2862282028202820282028202863281f28642820286329632963291f2863281f2962281f288bc0823b808629000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ ����(Huaibei) 4
"00e047003382348110281f28202865291f291f291f28202820282028662820286528662865291f29652820281f286529652966281f291f291f29652866281f291f291f296528662865298996823c8087280000000000000000000000000000000000000000000000000000000000",
};
}