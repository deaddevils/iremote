package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Kogan extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Kogan";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ¿Â¸Ô(Kogan) 1
"00e04700338234810f281f292028202820281f2965291f291f29662865296628662865282028662965291f291f292029662a1f291f2965281f286529652866282028652866281f2965298994823b8087280000000000000000000000000000000000000000000000000000000000",
};
}