package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Denver extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Denver";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ µ¤·ð(Denver) 1
"00e02f0037363277323d323e313d323e313d333e323d663d3278323d95c63f3176323e323d323e323d323d323d323e673d3276323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}