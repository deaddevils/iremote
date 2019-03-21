package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ADL extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ADL";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ADL(ADL) 1
"00e0470033823481102a1f291f281f281f281f2a1f291f291f29652865291f296628202866281f281f28202820282028202820282028652866286529652865286528662965291f291f298995823b8087290000000000000000000000000000000000000000000000000000000000",
};
}