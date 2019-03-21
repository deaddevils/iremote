package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Hauppauge extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Hauppauge";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ªÙ≤®∏Ò(Hauppauge) 1
"00e02f0037363376323d323e313d323e323d323e323e673d3377323d95c43f3176323e323d323e313d323e323d323e673e3176323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}