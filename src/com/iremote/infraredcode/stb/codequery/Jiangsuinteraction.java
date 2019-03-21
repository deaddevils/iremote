package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Jiangsuinteraction extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Jiangsuinteraction";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ½­ËÕ»¥¶¯(Jiangsu interaction) 1
"00e0470033823481102866282028202820281f291f29202820286628202864286628652965296528652920291f296528642864282028662865286629652a1f291f291f2865281f291f2a8994823c8086280000000000000000000000000000000000000000000000000000000000",
};
}