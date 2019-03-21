package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Techwood extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Techwood";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Techwood(Techwood) 1
"00e02b00376c323d323d323d323e323d6676323d673e3277323e947377313d323e323d323e323e6676323e673e3277323d00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}