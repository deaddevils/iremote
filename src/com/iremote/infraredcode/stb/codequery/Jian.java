package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Jian extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Jian";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ����(Jian) 1
"00e0470033823481102865281f2920291f291f291f29202820281f292028202866296629652865296529652820282028202820281f281f291f2a1f296529662866286528672866286529899b823b80862a0000000000000000000000000000000000000000000000000000000000",
//������ ����(Jian) 2
"00e0470033823481112866281f281f2a1f291f291f2920281f282028202820286628652a65296528662966281f282129202820291f291f281f2920286529652867286628662965296528899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}