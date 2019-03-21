package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Barco extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Barco";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ∞Õø…(Barco) 1
"00e02f003737313d3278323d323e323e313e333e313d673e3277323d95c73d323d3277323e313e333e313d323e333d673e3277313d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}