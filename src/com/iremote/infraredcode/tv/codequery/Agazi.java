package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Agazi extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Agazi";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ∞¢º”∆Î(Agazi) 1
"00e049003481188114291e2863296428632a1e291e291e281f291e286528642863281e281f281e281f281f281f2863291e2863281f281f281e28632a64281e2864281e296429632864298b40811f8115286229000000000000000000000000000000000000000000000000000000",
};
}