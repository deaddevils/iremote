package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Enping extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Enping";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¶÷Æ½(Enping) 1
"00e02b003731646e30382f3864383038306d646c30392f383139948038636d31393138633930392f6d636d30383038303800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}