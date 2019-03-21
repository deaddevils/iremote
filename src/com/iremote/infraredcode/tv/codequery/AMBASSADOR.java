package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class AMBASSADOR extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "AMBASSADOR";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 大使(AMBASSADOR) 1
"00e0470033823481112965281f2920292028662866286428202820286628652866282028202820286528202820282028662820281f2820292028662866286528202865296529652865298996823b8086280000000000000000000000000000000000000000000000000000000000",
};
}