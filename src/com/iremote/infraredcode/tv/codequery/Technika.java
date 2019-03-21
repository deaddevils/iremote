package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Technika extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Technika";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Technika(Technika) 1
"00e0470033823281102865291f2866281f2a652820291f291f281f2865291f29652820286529652865291f2966282028202866281f282028202866291f29652965281f296428652965298992823b8086280000000000000000000000000000000000000000000000000000000000",
};
}