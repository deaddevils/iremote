package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Weltblick extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Weltblick";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Weltblick(Weltblick) 1
"00e02b00376f6776313d333e323d313d333e323d673d3277323e9478766676313d323e323e313d333e323d673d3277323e00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}