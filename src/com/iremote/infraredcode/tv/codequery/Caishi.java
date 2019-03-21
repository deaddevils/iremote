package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Caishi extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Caishi";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ≤… ´(Caishi) 1
"00e05b003382348111291f291f286329632863281e2820281e2863291f281e281f281e28642a63291f281e281f296329632864281e2863296428632863291f2863281e281e2963281e291f281e2864291e29632864281e2863296328642a85bc823b810f29000000000000000000",
};
}