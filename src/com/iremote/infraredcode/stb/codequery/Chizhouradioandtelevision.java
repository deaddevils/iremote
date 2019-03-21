package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Chizhouradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Chizhouradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 池州广电(Chizhou radio and television) 1
"00e037003382348110281e281e2863281f2863291f2963291f281f281f281f291f2963282028642820286329632963281f2863291f2963281f288bbf823b808629000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//机顶盒 池州广电(Chizhou radio and television) 2
"00e03700338234810f2a1f281f2863281f296329202863291f29202820281f281f286428202863291f2963296328642820286328202864281f298bbf823b808629000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}