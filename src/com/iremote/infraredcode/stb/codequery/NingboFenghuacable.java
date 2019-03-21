package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class NingboFenghuacable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "NingboFenghuacable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 宁波奉化有线(Ningbo Fenghua cable) 1
"00e0470033823481112920281f281f281f2a1f291f2920281f2867286628662965296528652965296528202821286628662965291f291f281f29662966281f2820282028662865296529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}