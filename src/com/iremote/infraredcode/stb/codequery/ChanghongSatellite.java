package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ChanghongSatellite extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ChanghongSatellite";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ³¤ºçÎÀÐÇ(Changhong Satellite)dvb-c8000b1 1
"00e047003382328110281f2920291f291f291f2966291f29202865282029662965281f2a1f291f2965282028672820286629652820291f291f2966291f2966281f28212966286628652a899b823c80862a0000000000000000000000000000000000000000000000000000000000",
};
}