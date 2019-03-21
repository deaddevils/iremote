package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Brionvega extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Brionvega";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Brionvega(Brionvega) 1
"00e02f0037353376323d323e323e313e323d323d323e673d3277323e95c63e3276323d323e313e323d323e323e313e673e3277313d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}