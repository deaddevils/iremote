package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Pingxiang extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Pingxiang";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ Ƽ��(Pingxiang) 1
"00e0470033823481102865291f291f291f28202820282028202820281f291f29652965296629662865286728202820281f281f2a1f291f291f291f2866286628662965286628652a6529899b823d8086290000000000000000000000000000000000000000000000000000000000",
//������ Ƽ��(Pingxiang) 2
"00e04700338235811029662820281f282029202820291f281f281f291f281f296628652866286628652a65291f29202820281f292028202820281f296529652865296529652866296628899c823b8087280000000000000000000000000000000000000000000000000000000000",
};
}