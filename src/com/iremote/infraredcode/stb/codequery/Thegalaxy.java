package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Thegalaxy extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Thegalaxy";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÒøºÓ(The galaxy)-DVBC2010CB 1
"00e047003582338110291f291f29652820282028202865281f29652965292028662966281f28202966296528652a1f296529202820281f29202820281f28652a1f296529662965296528899c823c8086280000000000000000000000000000000000000000000000000000000000",
};
}