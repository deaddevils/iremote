package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class FoshanSouthChinaSea extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "FoshanSouthChinaSea";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 佛山/南海(Foshan / South China Sea) 1
"00e047003382358110291f291f291f2820282028202820282028652965296529662966286528672866281f291f296529652966291f2920282028662866281f281f281f2a652965286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}