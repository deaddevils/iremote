package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Digitallife extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Digitallife";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 数字生活(Digital life) 1
"00e05b003382348110281e281f286328632862281e291e281e2a63281f281e281e281e29632a63281f281e291e286428632863281f2963286428632963291e2962281f291e2964281e281e281e2963291e28632862281f2963296328632985bc823b810f29000000000000000000",
};
}