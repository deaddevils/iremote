package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class HenanGNI extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "HenanGNI";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ºÓÄÏGNI(Henan GNI) 1
"00e0470033823581102864282028202820281f281f281f291f2a1f29652865286529662866286528662865296528202820281f2820282028202820281f296628662865286628652965298996823b8086290000000000000000000000000000000000000000000000000000000000",
};
}