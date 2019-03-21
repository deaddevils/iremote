package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Aristona extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Aristona";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 阿里斯顿(Aristona) 1
"00e02f0037363177333e313d323e333d323d323e323d683d3277323e95c93d3277323d313e323e313d333e323e313d683d3377323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}