package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class DalianHaier extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "DalianHaier";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ��������(Dalian Haier) 1
"00e0470033823281102866291f291f281f2920281f291f2920282028662866286528652965296528652965286728202820281f281f281f2a1f291f292028652867286628652966296528899b823c8086290000000000000000000000000000000000000000000000000000000000",
//������ ��������(Dalian Haier) 2
"00e0470033823481102865291f291f291f281f2820282028202820286529652965296628662865286728662865291f2a1f291f291f292028202821282028662865296629652965296528899c823c8086280000000000000000000000000000000000000000000000000000000000",
};
}