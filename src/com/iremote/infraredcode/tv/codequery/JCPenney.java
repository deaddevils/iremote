package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class JCPenney extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "JCPenney";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” JCPenney(JCPenney) 1
"00e04700338234811028662820282028652866291f291f291f291f29652865291f2920296628652965281f281f291f2a6528202820291f281f286428652865291f2965286529642865298993823b8087290000000000000000000000000000000000000000000000000000000000",
};
}