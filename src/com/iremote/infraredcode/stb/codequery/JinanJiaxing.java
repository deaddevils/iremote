package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class JinanJiaxing extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "JinanJiaxing";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 济南/嘉兴(Jinan Jiaxing) 1
"00e0470033823481112820281f281f2865291f291f296528202866286629652820296528652965291f28202820286628662965291f291f291f296629662820281f28202966296528652a899b823c80862a0000000000000000000000000000000000000000000000000000000000",
};
}