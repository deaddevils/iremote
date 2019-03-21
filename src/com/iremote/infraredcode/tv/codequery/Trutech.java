package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Trutech extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Trutech";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Trutech(Trutech) 1
"00e04700338233810f291f2965291f2965281f281f2a1f29652965281f28642820286628652965292028652a652965286528202820286529202820281f281f282028662965291f2965288994823b8086290000000000000000000000000000000000000000000000000000000000",
};
}