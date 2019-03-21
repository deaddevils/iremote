package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Xianrailwaysystem extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Xianrailwaysystem";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 西安铁路系统(Xian railway system) 1
"00e0470033823481112866281f281f291f291f291f2920281f28672820286628652a65296529662966281f28202866286628652a1f29652966286529652820282028202866281f291f2a899b823b80862a0000000000000000000000000000000000000000000000000000000000",
};
}