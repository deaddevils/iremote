package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Capsonic extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Capsonic";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Capsonic(Capsonic) 1
"00e049003481178114291f296428642964291e281e291f291e281f296428642a63291e281f281e281e281f291e2865281f2963291e291f291e28642864281e2963281f296428632963298b3f811f81152a6129000000000000000000000000000000000000000000000000000000",
};
}