package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class CllL extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "CllL";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” cllL(cllL) 1
"00e047003382328110281f29202965281f281f281f291f291f29662866282028652866296529652866286528662965296528202820281f291f29202820282028202866286528662965298993823b8086290000000000000000000000000000000000000000000000000000000000",
};
}