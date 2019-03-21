package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Yinchuan extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Yinchuan";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ Òø´¨(Yinchuan) 1
"00e0470033823481102966282028202820281f281f291f2a1f296529202866296628662966286628652a1f291f296528662966281f2865286628652965291f291f282028662920282028899c823b8087280000000000000000000000000000000000000000000000000000000000",
};
}