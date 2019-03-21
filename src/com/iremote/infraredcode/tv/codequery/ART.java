package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ART extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ART";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 艺术(ART) 1
"00e02f003731323e3177323d323e313e333e323e313e683e3277313d95c93d323e3176313e323e313d323e323d323e673d3176313e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}