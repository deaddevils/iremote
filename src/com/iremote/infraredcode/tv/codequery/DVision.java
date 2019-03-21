package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class DVision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "DVision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” DVision(DVision) 1
"00e02f003737313d3278333d323e323e313e333e313d673d3278323d95c73d313e3277323e313e333d323d323e323d673e3277323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}