package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Huainanradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Huainanradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 淮南广电(Huainan radio and television) 1
"00e03700338232810f2920292028642a1f2963291f2963281f281f281f291f29202964282028642820286328642864281f2963291f2963291f288bc0823b808628000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}