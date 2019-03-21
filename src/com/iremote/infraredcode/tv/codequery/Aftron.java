package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Aftron extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Aftron";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 该公司(Aftron) 1
"00e05b003382348110281e281f296329632863281e281f291e2863291f281f291f282027642863291e281f291f286329632963281e286328632a632864281e2864281e291e28622820281e281f2963291e29632963281f2963296328632985bd823b811028000000000000000000",
};
}