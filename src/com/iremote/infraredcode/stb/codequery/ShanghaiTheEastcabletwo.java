package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ShanghaiTheEastcabletwo extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ShanghaiTheEastcabletwo";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 上海东方有线二(Shanghai The East cable two) 1
"00e02f003731636d303963392f3930382f6c303963382f3831393038944d38636d303863392f3930382f6c303963392f3831393038000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}