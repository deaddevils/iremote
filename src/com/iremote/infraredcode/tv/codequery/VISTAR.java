package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class VISTAR extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "VISTAR";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 威斯达(VISTAR) 1
"00e04700338233810f291f29652865291f291f291f291f296529652a65291f2865281f296528662820281f28652920282028662820281f291f29662820286628652820286529662865288997823a8087280000000000000000000000000000000000000000000000000000000000",
};
}