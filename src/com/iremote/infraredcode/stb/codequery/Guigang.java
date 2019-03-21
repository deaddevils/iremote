package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Guigang extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Guigang";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¹ó¸Û(Guigang) 1
"00e04700338233810f291f281f2920281f281f291f291f296528652964286529652965286529652820282028662820281f2866281f291f29652965281f28642865281f29652965281f288994823a8085280000000000000000000000000000000000000000000000000000000000",
};
}