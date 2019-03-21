package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class PanasonicHD extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "PanasonicHD";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 松下高清(Panasonic HD) 1
"00e047003382338110291f282028662866281f291f2a6529652966291f296528672866281f281f291f2a1f291f291f291f28202820282028202865296529652865286529652867286628899c823b8087280000000000000000000000000000000000000000000000000000000000",
};
}