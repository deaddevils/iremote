package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Anhuiradio extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Anhuiradio";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ���չ��(Anhui radio) 1
"00e0370033823381112820281e281f29642820282028642820281f281f2820281f2863291f2963291f2963296429632a1f2a63291f2963281f288bc0823c808628000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ ���չ��(Anhui radio) 2
"00e037003582328110291f291f291f2963281f281f2963292028202820282028202864281f2864282028632a63296328202864282028632820288bc0823b808728000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ ���չ��(Anhui radio) 3
"00e037003382358111281f281f296228202863281f2963291f291f2920292028202863291f2963291f29632863286328202864281f2963291f298bc18239808629000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ ���չ��(Anhui radio) 4
"00e0470033823281112820282028662820281f291f29202820291f2965281f296529652865291f29652820291f2865296528662820281f281f29652866291f281f281f28652a652965288996823b8086290000000000000000000000000000000000000000000000000000000000",
};
}