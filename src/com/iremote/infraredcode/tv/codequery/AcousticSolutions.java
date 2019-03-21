package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class AcousticSolutions extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "AcousticSolutions";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” …˘Ω‚(Acoustic Solutions) 1
"00e02f0037353276323d333d313e323d323d323e323d673e3276313d95c83d3277323d323d323e313d323e323d323e673d3176323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}