package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class TianjinRadioandtelevisionnetwork extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "TianjinRadioandtelevisionnetwork";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 天津广电网络(Tianjin Radio and television network) 1
"00e047003382348110291f291f281f2966281f282028202820282029652865291f291f29652867286628662866281f281f281f281f2965291f282028212866286629652965281f296629899b823c8087290000000000000000000000000000000000000000000000000000000000",
//机顶盒 天津广电网络(Tianjin Radio and television network) 2
"00e0470033823481102820281f291f2966291f291f29202820281f296628662920282029652865296529652866292028202820291f2966291f291f292028662966286528662820286529899b823b8086290000000000000000000000000000000000000000000000000000000000",
};
}