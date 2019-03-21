package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Osaki extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Osaki";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ¥Û∆È(Osaki) 1
"00e049003381188115281f286428632964281f281f281e281f291e296329652864281e281f281e291e291e291e2964281e2864281e291f281e29632864291f2863291e296328642963288b41811f8116296229000000000000000000000000000000000000000000000000000000",
};
}