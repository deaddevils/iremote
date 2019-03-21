package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ChaozhouChaoancable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ChaozhouChaoancable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 潮州潮安有线(Chaozhou Chaoan cable) 1
"00e047003382358110291f292028202820282028202866291f29202a1f29652966291f2966281f28202966291f28652a1f291f291f2965282028202866281f28652a652965281f296628899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}