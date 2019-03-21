package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class SyntaxOlevia extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "SyntaxOlevia";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 语法奥丽维亚(Syntax Olevia) 1
"00e04700358232811129202820286428202820281f282028202865292028202866296529652820286428202820281f2820282028202820281f286628652a6529652865286628652965298993823a8086280000000000000000000000000000000000000000000000000000000000",
};
}