package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Genexxa extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Genexxa";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Genexxa(Genexxa) 1
"00e02f0036373276323d313d333d333d323d313d333d673d3277323d95c23e3176323e333d323e313d323e323d323e673d3277323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}