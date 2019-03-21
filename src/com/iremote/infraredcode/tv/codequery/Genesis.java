package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Genesis extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Genesis";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 创世纪(Genesis) 1
"00e02f003736323d3176323e323d323e313d333e323d683d3277313d95c43f313d3277323d323e323d323e323d323e673d3277323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}