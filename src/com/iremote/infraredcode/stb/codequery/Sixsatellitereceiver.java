package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Sixsatellitereceiver extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Sixsatellitereceiver";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 中六卫星接收机(Six satellite receiver) 1
"00e0470033823481102820281f29652965281f29202865296528652820286628652966291f291f29202820281f292028202820291f291f2a1f2965296628652965286728662865296529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}