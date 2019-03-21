package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Astra extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Astra";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 阿斯特拉(Astra) 1
"00e02f003735323e3177323e323d323d323d333d323d663d3277333e95c73e323e3176313d333e323d323e323d323e673e3176313e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}