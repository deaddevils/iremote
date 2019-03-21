package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Centrurion extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Centrurion";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Centrurion(Centrurion) 1
"00e02f003736323e3177323d323e313e323e313d313e673d3376323d95c83d323e3176313e323e313d323e323d323e673d3176313e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}