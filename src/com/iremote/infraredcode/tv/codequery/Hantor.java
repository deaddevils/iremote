package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Hantor extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Hantor";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Hantor(Hantor) 1
"00e02f0037363276323d323e313d323e313e323e313d673d3176333e95c13d3176333d333d323d323c323d333e323d673e3276313d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}