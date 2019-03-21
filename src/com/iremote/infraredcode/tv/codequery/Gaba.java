package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Gaba extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Gaba";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ∞±ª˘∂°À·(Gaba) 1
"00e02f003731323e3276313d333d313e323d323d333d663d3377323d95c33d323d3277323e313e333d323e323e313e683e3276323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}