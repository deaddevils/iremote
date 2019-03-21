package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Zhuhairadioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Zhuhairadioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 珠海广电(Zhuhai radio and television) 1
"00e0370033823381102a1f2863281f29632a63291f2963281f291f281f291f291f296329202964281f28632963296328202864281f29642820288bc6823b808729000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}