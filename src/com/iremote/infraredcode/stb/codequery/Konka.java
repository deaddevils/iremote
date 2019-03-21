package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Konka extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Konka";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¿µ¼Ñ(Konka) 1
"00e047003382348110281f291f2a1f29652920282028672820286629652965281f296529652965282028202866281f2965291f291f281f29202865292028652820286628652865296529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}