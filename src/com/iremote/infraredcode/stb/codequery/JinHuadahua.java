package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class JinHuadahua extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "JinHuadahua";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 金华大华(Jin Huadahua) 1
"00e047003382358110291f292028202866292029202965281f2966296628652821286628662965291f291f2865281f29662820281f282029202866281f2865291f296528662866286528899b823b8086280000000000000000000000000000000000000000000000000000000000",
//机顶盒 金华大华(Jin Huadahua) 2
"00e047003382348110291f2920281f2966281f281f28662820286528652965291f296629662865282028202866281f2865291f291f291f2820286728202866291f296629652966296529899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}