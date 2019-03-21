package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class PortofFangcheng extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "PortofFangcheng";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ·À³Ç¸Û(Port of Fangcheng) 1
"00e0470033823481112a1f281f281f281f2a1f291f291f29652865296529652865286428662866281f28202866281f292028652820282028662965291f28662865291f29652865281f298995823b8086290000000000000000000000000000000000000000000000000000000000",
};
}