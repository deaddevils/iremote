package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Dawa extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Dawa";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ¥ÔÕﬂ(Dawa) 1
"00e02f0037373276323d323e313d323e323d323d323e663d3377323e95c63d3277313d323e323d323e323e313e323e673d3276333e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}