package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Guilin extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Guilin";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¹ðÁÖ(Guilin) 1
"00e04700338233810f291f291f291f291f291f2920282028652965296528642865286529652965281f291f2a652820291f2865291f291f29662866282028652866291f29652965281f288994823b8086280000000000000000000000000000000000000000000000000000000000",
};
}