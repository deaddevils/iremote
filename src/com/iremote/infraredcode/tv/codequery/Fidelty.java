package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Fidelty extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Fidelty";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 保真度(Fidelty) 1
"00e049003481158115281e2864296329642820281e281f281e281e286528642963281f291e2820281e281e281e2964281f2864291e2a1e281e29642864291f2864291e296328642864288b3d811f8114296128000000000000000000000000000000000000000000000000000000",
};
}