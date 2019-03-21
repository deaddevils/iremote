package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Guangdongcable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Guangdongcable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 广东有线(Guangdong cable) 1
"00e02700373632776776673e3277323e6777323d323d333e95d63e31766877673e3278333e6777323e323d323e0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}