package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Yancheng extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Yancheng";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ �γ�(Yancheng) 1
"00e04700338232811028652820291f291f29202820281f29202865281f29662965286529652965286629202820286629662965281f2a652965286628662820281f281f2966291f281f28899b823c8086290000000000000000000000000000000000000000000000000000000000",
//������ �γ�(Yancheng) 2
"00e04700338235811029652820281f282028202820281f291f2966291f296528652965286628662866291f2920296528652865292028652866286628652920291f291f2965281f291f29899b823b8087280000000000000000000000000000000000000000000000000000000000",
//������ �γ�(Yancheng) 3
"00e03700338235811029632863281f291f2963291f2963292029202820281f291f2963291f28632820286429642864281f2864282028642820288bc6823b808629000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ �γ�(Yancheng) 4
"00e02700372f636e636d63382f38316d636d313830393039948039636d636c63382f38306d646d3038303831390000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}