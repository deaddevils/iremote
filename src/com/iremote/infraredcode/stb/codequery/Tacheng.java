package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Tacheng extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Tacheng";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ����(Tacheng) 1
"00e0470033823381102820281f28652820291f281f2865291f296528652821286628662920292029652865296529202865282028202820281f281f281f2a65291f296528672866286628899b823b8086280000000000000000000000000000000000000000000000000000000000",
//������ ����(Tacheng) 2
"00e047003382348110281f291f2965291f282028202866282028652966291f28652a65291f291f286628662866291f2966291f281f2920281f281f29202865281f2966296528652a6529899b823c8086290000000000000000000000000000000000000000000000000000000000",
//������ ����(Tacheng) 3
"00e0470033823481112820291f291f2865291f291f2965282028662866286529202965286528652920281f286728202866281f291f2a1f291f296528202867282028662965286628652a899b823b80862a0000000000000000000000000000000000000000000000000000000000",
};
}