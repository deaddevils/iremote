package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class AsuKa extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "AsuKa";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 明日香(AsuKa) 1
"00e049003481188115281f2863296428642a1e291e291e281f291e286528642863281e281f281e281f281f281f2863291e2966271f281f281f28632964281e2864281e296429632864298b40811f8115286229000000000000000000000000000000000000000000000000000000",
};
}