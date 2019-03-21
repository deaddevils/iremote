package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Digihome extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Digihome";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Digihome(Digihome) 1
"00e02f003733323e3176313e323d323d323e323d323e673e3176313e95c73e323d3277323e313e323d323d323e323d673e3277323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}