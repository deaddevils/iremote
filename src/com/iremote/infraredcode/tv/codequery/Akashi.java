package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Akashi extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Akashi";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 明石(Akashi) 1
"00e05b003382348110291e281e296328642863291f291e281e2964281f281e281e281f29632963281e281e281e286429642963291f2863296328632863281e2963291e281e2963281e281e281e2864291e29632864281e2864296329642885be823b810f29000000000000000000",
};
}