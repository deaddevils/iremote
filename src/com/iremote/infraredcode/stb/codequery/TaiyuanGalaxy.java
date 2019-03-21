package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class TaiyuanGalaxy extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "TaiyuanGalaxy";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 太原银河(Taiyuan Galaxy) 1
"00e047003382348110281f2a1f2965291f282028202866282028652965291f29652965291f2920286528662866281f2965291f291f29202820281f28202866281f296529652865296529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}