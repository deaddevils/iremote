package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Axxon extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Axxon";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Axxon(Axxon) 1
"00e02f0037363376323d323e313d333e323d6876323d333d313e323e95c73d3176333e323d323e313d323e6777323d323d333d323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}