package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Digatron extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Digatron";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Digatron(Digatron) 1
"00e02f0037333276323d333d323e323d313d333e323d673d3278333d95c63d3277313d333e323d323e313d313e323e673d3276333d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}