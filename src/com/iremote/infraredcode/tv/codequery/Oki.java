package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Oki extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Oki";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 冲电气工业公司(Oki) 1
"00e02f00376f323e313e333d323d323e313d333e323d673d3278323d947276313e333d323d323e323d333e313d323e683e3276323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}