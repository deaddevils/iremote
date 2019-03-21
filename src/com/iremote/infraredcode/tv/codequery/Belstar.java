package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Belstar extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Belstar";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 博尔斯达(Belstar) 1
"00e02f0037313176333e323d323e313d333d323d323e673e3176323e95c53d3276323d323e313e323e313d323e323d673e3277313d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}