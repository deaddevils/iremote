package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Hainanradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Hainanradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 海南广电(Hainan radio and television) 1
"00e02b003731636d3038636c2f372f383138636d303830382f37947036636c3038626c303831383038636c2f383138303900000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}