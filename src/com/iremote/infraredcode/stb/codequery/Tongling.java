package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Tongling extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Tongling";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ͭ��(Tongling) 1
"00e037003582338110291f291f291f2963291f281f2863291f2a1f2a1f291f291f2963281f2863291f2964286428642820286328202864281f298bc1823b808729000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ ͭ��(Tongling) 2
"00e03700338235810f2820282028202863282028202863291f291f291f291f29202864281f2863291f296329632863281f296329202963291f288bc1823b808728000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ ͭ��(Tongling) 3
"00e037003382328110281e282028632820286428202863282028202820281f291f2963291f28632820286429632963291f2963281f2863291f298bc2823b808628000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ ͭ��(Tongling) 4
"00e04700358232810f292028202865292028202820281f292029202965281f28652a652965281f28652a1f291f296529662a65291f291f292028682867281f291f2920286528682865298997823b80872a0000000000000000000000000000000000000000000000000000000000",
};
}