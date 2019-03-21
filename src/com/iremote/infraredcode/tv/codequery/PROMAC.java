package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class PROMAC extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "PROMAC";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” PROMAC(PROMAC) 1
"00e05b00338234810f2a1e291f286329632963281e281f281e2863291e281e291f281f28632863291e281e291f286328632863281e2863296329632864281e2864291e291e2962281f281e281f2963291e29622864281e2963286428632985bd823b810f29000000000000000000",
};
}