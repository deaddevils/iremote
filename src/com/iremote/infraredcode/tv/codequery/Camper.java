package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Camper extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Camper";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ Â¶Óª(Camper) 1
"00e02f003733313e3277313d323e323d323e323e313e673d3376323d95c93d323e3277313d333d323d323e323d313e673e3276313d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}