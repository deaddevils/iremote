package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class DongguanradioandTVone extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "DongguanradioandTVone";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 东莞广电一(Dongguan radio and TV one) 1
"00e04700338234810f281f291f281f2820282028202820281f28652965286628652865286628662864282028202866286529662820281f281f28652a65291f281f281f29652a652965288996823b8086290000000000000000000000000000000000000000000000000000000000",
//机顶盒 东莞广电一(Dongguan radio and TV one) 2
"00e0470033823281102a1f281f281f291f291f291f2920281f286528662965296528652a6529652865291f2a1f29652865286529202820291f28652865292028202820286528652966288996823b8086280000000000000000000000000000000000000000000000000000000000",
};
}