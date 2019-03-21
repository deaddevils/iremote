package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Casio extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Casio";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ¿¨Î÷Å·(Casio) 1
"00e02f003736333d3177323e323e313e323d323d323e673d3277323e95c63c313e3176313d333d333d323e323d323e673d3277323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}