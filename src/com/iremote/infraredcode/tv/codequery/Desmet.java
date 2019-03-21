package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Desmet extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Desmet";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 迪斯美特(Desmet) 1
"00e02f003735323e3177323e313d323e323d323e323e663d3377323d95c53d323d3277323e313e333e313d323e323d683e3277313d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}