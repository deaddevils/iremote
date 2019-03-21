package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GansuJiesaiTechnology extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GansuJiesaiTechnology";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 甘肃杰赛科技(Gansu Jiesai Technology) 1
"00e047003382338110291f281f29652820291f291f2865291f29652865292028652865281f291f2a652866286528202866291f291f291f291f291f292028662a1f2965296528652866288994823b8086280000000000000000000000000000000000000000000000000000000000",
};
}