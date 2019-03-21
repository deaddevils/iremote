package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Qingyuanradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Qingyuanradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 清远广电(Qingyuan radio and television) 1
"00e047003382328110291f291f29202820281f291f29202865286628652965296528652a6529652865292029662820281f28652920292029652865281f296628662820286528662820288996823c8086290000000000000000000000000000000000000000000000000000000000",
};
}