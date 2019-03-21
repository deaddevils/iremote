package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Hefei extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Hefei";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ �Ϸ�(Hefei) 1
"00e0370033823481102820282028202864281f28202864281f291f291f291f291f2963281f2863291f296328632863291f2963291f2863281f288bbf823b808629000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ �Ϸ�(Hefei) 2
"00e037003582338110281f281f281f286329202820286329202820281f281f291f2963291f2964281f29632a63296428202864282028632820288bc1823b808629000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ �Ϸ�(Hefei) 3
"00e03700338233810f282028202863291f28642920296329202a1f291f291f281f2863281e28622820286429632963291f2963281f2862281e288bbf823b808728000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ �Ϸ�(Hefei) 4
"00e0470035823281102920281f2965291f281f28202820282028202866271f28662665286727202866281f2820296628662867271f28202820286628672720281e2820296726662865288996823c8086280000000000000000000000000000000000000000000000000000000000",
};
}