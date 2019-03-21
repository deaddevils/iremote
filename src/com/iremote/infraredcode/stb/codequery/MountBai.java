package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class MountBai extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "MountBai";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ °×É½(Mount Bai) 1
"00e0470033823481112866281f281f291f2a1f291f291f291f2866282028662865296629652865296529202820286728662865282029652865296529652820282128202866281f291f29899c823b80862a0000000000000000000000000000000000000000000000000000000000",
};
}