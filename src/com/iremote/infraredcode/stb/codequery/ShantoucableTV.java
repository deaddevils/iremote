package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ShantoucableTV extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ShantoucableTV";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 汕头有线电视(Shantou cable TV)-AR180 1
"00e02b003731636d30383038633930392f6c646c303831393138948138636c3039303863383039306c636c2f3930382f3800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}