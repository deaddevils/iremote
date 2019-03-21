package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Highline extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Highline";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ∫£¿º(Highline) 1
"00e02f003737323d3277333d313e323e313d333e323d673e3278333d95c63d323e3177323d323e313e323d323d323e673d3277323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}