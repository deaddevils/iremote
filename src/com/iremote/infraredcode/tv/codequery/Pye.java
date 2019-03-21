package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Pye extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Pye";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ≈…“¡(Pye) 1
"00e04900338118810f29632962281e2962291e281f291f281e28632862291e2863291e281f291e281e291e2962281e2962281e291e291e291e2a62281e2863281f2862286329622862288ac6811f810f2a1d28000000000000000000000000000000000000000000000000000000",
};
}