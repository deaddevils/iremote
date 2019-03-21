package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Dux extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Dux";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 达克斯(Dux) 1
"00e02f0036333277323d313e323e313d323e323d323e673d3176333e95c53e3276323d323e313e323e313d323e323d673e3277323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}