package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Futronic extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Futronic";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ∏√(Futronic) 1
"00e05b00338232810f2a1e291e286428632963281f291f281e2864281e291e291e281f29632864291e281f291f286329632963281e2864286329632864291e2963281e281f2963291e291e281e2864281f29632863291e2864296228642985bd823b811028000000000000000000",
};
}