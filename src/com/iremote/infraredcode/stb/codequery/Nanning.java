package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Nanning extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Nanning";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÄÏÄþ(Nanning) 1
"00e047003582328110291f291f291f291f29202920282028652965296528652966286628652966291f291f2965291f291f2865291f2a1f2a652965281f28642865292028652966291f288994823a8085280000000000000000000000000000000000000000000000000000000000",
};
}