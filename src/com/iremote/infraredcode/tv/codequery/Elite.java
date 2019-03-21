package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Elite extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Elite";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ¾«Ó¢(Elite) 1
"00e02f0037343277323d313e323d323e313d323e323d673e3277313d95c33e3376333e323d323d313d333d333d323d663d3277333d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}