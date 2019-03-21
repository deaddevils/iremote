package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Loewe extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Loewe";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ¬Â“¡(Loewe) 1
"00e02b00376f6776323e323d323e323e313e333e673e3176313e9473776877323d323e323d313d333e323d683d3278333d00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}