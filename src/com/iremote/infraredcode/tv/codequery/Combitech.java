package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Combitech extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Combitech";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 康比特(Combitech) 1
"00e04700338234810f281f29202820281f28202820282028662820282028652820281f29652965281f2966291f2a1f2965281f281f28652920282028662865282028662865291f2965288994823b8086290000000000000000000000000000000000000000000000000000000000",
};
}