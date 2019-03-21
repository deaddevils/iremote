package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class VectorResearch extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "VectorResearch";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 载体研究(Vector Research) 1
"00e04700338233810f2865281f281f28652966282028202820281f29652865291f29202865286529652820282028202866281f282028202820286529652965281f2965286628662865288994823a8086290000000000000000000000000000000000000000000000000000000000",
};
}