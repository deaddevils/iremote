package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Nanchong extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Nanchong";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ �ϳ�(Nanchong) 1
"00e047003382358110291f29202820282028202866281f29652965281f296629652965282028662820281f2965291f29652920281f281f29202865281f2966291f28652a652965286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
//������ �ϳ�(Nanchong) 2
"00e04700338233811128202820281f281f281f2a65291f296528672820286629652866281f2865291f291f2965282028662820281f291f291f2a65291f2966281f296628662866286528899b823b8086280000000000000000000000000000000000000000000000000000000000",
//������ �ϳ�(Nanchong) 3
"00e047003382358110281f291f2820282028202866281f281f2965291f2965286629202820281f286528202965281f296529652820282028202866281f28652a1f291f29652866296628899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}