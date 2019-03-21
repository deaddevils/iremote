package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class NingboCoship extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "NingboCoship";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ Äþ²¨Í¬ÖÞ(Ningbo Coship) 1
"00e047003582338110291f291f29202820281f29202820281f28652a65296529652965286628662865281f2920296528652965291f291f281f28662866281f281f291f2a652965286629899b823b8086290000000000000000000000000000000000000000000000000000000000",
};
}