package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Baysonic extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Baysonic";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Baysonic(Baysonic) 1
"00a063003382348110281f2866286628202820281f29202966286628202865282028202820282028202864286628662865282028202820282028202820281f281f2966286628652866288996823b8110291f29652865291f2920291f291f29652865292028662820281f281f2900",
};
}