package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ShantouChaoyangcable1 extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ShantouChaoyangcable1";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 汕头朝阳有线1(Shantou Chaoyang cable 1) 1
"00e047003382358110291f29202820281f282029202820291f28652965296528662866286629652866281f281f2a652965286628202820282028652966291f281f292028652965286528899b823b8088280000000000000000000000000000000000000000000000000000000000",
};
}