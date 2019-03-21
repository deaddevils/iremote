package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class JiuzhouNingbo extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "JiuzhouNingbo";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¾ÅÖÞÄþ²¨(Jiuzhou  Ningbo) 1
"00e047003382358110291f291f281f282028202866281f28652a65291f296628662865282028662820281f28652a1f296529202820282128202866281f2965291f296529662966286528899b823b8086280000000000000000000000000000000000000000000000000000000000",
};
}