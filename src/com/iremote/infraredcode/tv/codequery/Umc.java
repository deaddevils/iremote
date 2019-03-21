package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Umc extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Umc";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 联华(Umc) 1
"00e04700358233810f2920281f28202820282028202865282028652965296528652965286628202865282028202865291f291f291f2820282028662965291f29652865296628652965298995823b8087280000000000000000000000000000000000000000000000000000000000",
};
}