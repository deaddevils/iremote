package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Galaxi extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Galaxi";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 加拉克赛(Galaxi) 1
"00e02f0037363176333e323d323d313d333d333d323d663d3277333d95c43d3276323d323e313e323d323d323e313e673e3276323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}