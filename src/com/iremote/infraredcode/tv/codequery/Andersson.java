package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Andersson extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Andersson";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 安德松(Andersson) 1
"00e02f0036353377323e313d333d333d323d323d333d673e3277323d95c93d3277323e313e323d323d323e323d323e673e3176313e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}