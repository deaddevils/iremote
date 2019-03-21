package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ShandongSKYWORTH extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ShandongSKYWORTH";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 山东创维(Shandong SKYWORTH) 1
"00e0470033823481102965282028202820281f281f2a1f291f29652820286728662865296629652865291f291f296528672866281f2965286628652a65291f291f282028672820282028899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}