package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ATD extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ATD";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ATD(ATD) 1
"00e049003481178114291e296328652864291f291e281e291e281e2965286429632820281e2820281e28642864291e29632820281e281f281f281f281f2864281e2963286528642963288b40811f8114286128000000000000000000000000000000000000000000000000000000",
};
}