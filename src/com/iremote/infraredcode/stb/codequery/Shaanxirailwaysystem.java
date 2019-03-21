package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Shaanxirailwaysystem extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Shaanxirailwaysystem";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 陕西铁路系统(Shaanxi railway system) 1
"00e04700358233811029652920281f281f292028202820281f2966291f296529652965286628662865281f2920296528652965291f29652867286628652920291f291f2965291f291f29899b823b8086290000000000000000000000000000000000000000000000000000000000",
};
}