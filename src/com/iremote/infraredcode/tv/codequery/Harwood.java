package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Harwood extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Harwood";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ¹þÎéµÂ(Harwood) 1
"00e02f003736333d3277323d313d333d313e323e313d683c3276333d95c43d323e3176313e323e313d323e323d323e673d3277323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}