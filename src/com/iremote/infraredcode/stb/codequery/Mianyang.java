package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Mianyang extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Mianyang";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ����(Mianyang) 1
"00e047003382348110281f2a1f291f291f291f28662820286628652920296528652a65291f2965282028202866281f2965291f291f291f2920286629202865281f296629652865296529899b823c8086290000000000000000000000000000000000000000000000000000000000",
//������ ����(Mianyang) 2
"00e0470033823481112920282028202820281f2965291f29652966291f2965286528662820286529202a1f29652920286629202820281f281f2966291f2865291f296529652866296628899c823b8087280000000000000000000000000000000000000000000000000000000000",
//������ ����(Mianyang) 3
"00e047003382358110281f291f2820282028202866281f281f2965291f2965286629202820281f286528202965281f296529652820282028202866281f28652a1f291f29652866296628899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}