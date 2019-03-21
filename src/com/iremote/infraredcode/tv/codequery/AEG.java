package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class AEG extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "AEG";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 通用电力(AEG) 1
"00e02f003737313d3278333d323e323d313d333e323d673d3278333d95c93d313e3277323e313e333e313d323e333d683d3277323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}