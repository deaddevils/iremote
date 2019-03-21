package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Hengyangcable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Hengyangcable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ��������(Hengyang cable) 1
"00e04700338233810f281f291f291f291f282028202866291f291f291f2965286529202965291f29202865291f2966291f2920292028662820282028652820286629652965281f2864288993823a8085280000000000000000000000000000000000000000000000000000000000",
//������ ��������(Hengyang cable) 2
"00e047003382338110291f281f291f291f29202820286628202820281f28662865291f2965281f281f28652920286628202820282028652820281f2965291f29652865296428202865298993823b8086290000000000000000000000000000000000000000000000000000000000",
};
}