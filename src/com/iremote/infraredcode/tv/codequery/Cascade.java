package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Cascade extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Cascade";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” “∂’§(Cascade) 1
"00e02f0037373276323d323e313e323d323d323e313e673e3276323d95c43d3277323e313e333d323e313d323e333d683d3277323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}