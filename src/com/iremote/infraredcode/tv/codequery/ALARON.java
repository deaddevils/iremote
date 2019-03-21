package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ALARON extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ALARON";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ °¢À­Â×(ALARON) 1
"00e04700358233810f28672767271f281f282028202820286727202867271f2864286529672767271f28202820281f28662820281f2820282028662866286528202867286727652965298997823b8086280000000000000000000000000000000000000000000000000000000000",
};
}