package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Euroman extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Euroman";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ Ö÷½Ç(Euroman) 1
"00e02f003736313e3277313d323e333d323e323e313e683e3276323d95c53d323e3177323d333d323e323d323d333d673d3277323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}