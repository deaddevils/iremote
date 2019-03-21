package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class PuTiancable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "PuTiancable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÆÖÌïÓÐÏß(Pu Tian cable) 1
"00e04700358232810f28202820281f281e28202820282029652864286528662865286628652965291f281f29662a1f291f2965281f281f29652a65291f286529652820286628652820288996823b8086290000000000000000000000000000000000000000000000000000000000",
};
}