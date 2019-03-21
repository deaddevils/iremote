package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Carena extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Carena";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ¿¨Â×ÄÉ(Carena) 1
"00e02f003737313d3278333d323e323e313e333e313d673d3278323d95c73d323d3277323e323d323e313d323e333d683d3277323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}