package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Yum extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Yum";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ °ÙÊ¤(Yum) 1
"00e02d0034814b81d11181d11281351281d31281341381d31181d11281d21281d2128134118c4d1281341281d11281d21281341281d11181341181d21281d21381d11181d11281351200000000000000000000000000000000000000000000000000000000000000000000000000",
};
}