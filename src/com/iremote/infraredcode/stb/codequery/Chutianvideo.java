package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Chutianvideo extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Chutianvideo";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 楚天视讯(Chutian video) 1
"00e047003382358110291f2920281f281f292028202820291f2966296528652965296528672866286629202820291f281f2920281f281f29202865286528662865296529652966286529899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}