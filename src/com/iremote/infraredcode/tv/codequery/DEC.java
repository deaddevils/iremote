package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class DEC extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "DEC";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” DEC(DEC) 1
"00e05b003382328110281e281e286428632962281f281e281f2963291e291f281f291f28632963281e291f281e286328632963281f2862286429642962281f2863291e281e2963281e2820281e2864281e29632863291e2863296328632885bd823a810f28000000000000000000",
};
}