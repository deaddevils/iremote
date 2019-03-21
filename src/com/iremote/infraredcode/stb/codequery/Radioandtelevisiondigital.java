package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Radioandtelevisiondigital extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Radioandtelevisiondigital";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 广电数字(Radio and television digital) 1
"00e02b00372c636d646d63393038306c30396338303831383138944e38636d636d63383139316c3039633931383038303800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}