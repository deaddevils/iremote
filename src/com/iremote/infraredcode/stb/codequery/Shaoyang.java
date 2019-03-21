package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Shaoyang extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Shaoyang";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ����(Shaoyang) 1
"00e04700338234810f281f291f291f291f282028202866291f291f291f29652865291f296628202820286528202866281f291f291f2965281f281f28652920286628652965281f2965298994823a8086290000000000000000000000000000000000000000000000000000000000",
//������ ����(Shaoyang) 2
"00e04700338233810f281f291f291f281f281f281f296528202820282028652865281f286529202820286628202865281f2820281f29652820291f2865281f28652866296628202865298992823b8086280000000000000000000000000000000000000000000000000000000000",
//������ ����(Shaoyang) 3
"00e04700338233810f282028202820281f282028202866291f291f291f2965286428202866281f291f296528202866281f282028202866291f292028662820286528662866261f2965288992823a8085280000000000000000000000000000000000000000000000000000000000",
};
}