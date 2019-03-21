package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Avol extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Avol";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Avol(Avol) 1
"00e04700338232810f281f291f291f291f28202820282028652965296528642865286529652965281f29652866286628652866296528662866281f282028202820281f291f29202820288995823b8086280000000000000000000000000000000000000000000000000000000000",
};
}