package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Wuzhong extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Wuzhong";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//ª˙∂•∫– Œ‚÷“(Wuzhong) 1
"00e0470033823481112866281f281f291f291f291f2920281f28672820286628652a65296529652965281f28202866286628652a1f29652966296529652820282028202866281f291f2a899b823b8086290000000000000000000000000000000000000000000000000000000000",
};
}