package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Heyuanradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Heyuanradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ��Դ���(Heyuan radio and television) 1
"00e04700338234810f281f282028202820282028202820282028642865296628652866296529652865291f291f29662866286428202820282028662865282028202820286628652866288995823b8087280000000000000000000000000000000000000000000000000000000000",
//������ ��Դ���(Heyuan radio and television) 2
"00e047003382328110281f291f2a1f291f291f291f2920282028652965296528652966286628652866281f281f296529652865291f2a1f291f29652865291f2920282029652864286428899682398086290000000000000000000000000000000000000000000000000000000000",
};
}