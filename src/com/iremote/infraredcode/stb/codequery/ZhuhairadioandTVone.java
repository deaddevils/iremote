package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ZhuhairadioandTVone extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ZhuhairadioandTVone";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 珠海广电一(Zhuhai radio and TV one) 1
"00e037003382328110291f2963291f296328632820286328202820281f291f291f2963291f2963281f286228642864281f29632a1f29632820288bbf823b808629000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}