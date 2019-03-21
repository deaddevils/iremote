package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class COSHIPChau extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "COSHIPChau";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ COSHIPÍ¬ÖÞ(COSHIP Chau)-N9201 1
"00e0470033823481112920282028202820281f281f2965291f291f292028662966281f28652820291f2865291f2965291f281f282028662820281f28652a1f2965296628662820286629899b823b8087290000000000000000000000000000000000000000000000000000000000",
};
}