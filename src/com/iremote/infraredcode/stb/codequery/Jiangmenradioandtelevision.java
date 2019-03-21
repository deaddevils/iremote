package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Jiangmenradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Jiangmenradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 江门广电(Jiangmen radio and television) 1
"00e037003382328110281f2863281f2963291f291f2963281f281f291f291f29202863281f2963291f296328632863291f296328202863291f298bbe823c808628000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//机顶盒 江门广电(Jiangmen radio and television) 2
"00e037003382348110291f2963281f2863281f28202864281f291f291f291f291f2963281f2863291f296428632863291f2963291f2863281f288bbe823c808628000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}