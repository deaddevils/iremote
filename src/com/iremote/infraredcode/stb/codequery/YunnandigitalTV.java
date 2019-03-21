package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class YunnandigitalTV extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "YunnandigitalTV";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 云南数字电视(Yunnan digital TV) 1
"00e047003382348111292028202866281f291f29202a1f291f291f2966281f296628662966291f28652a1f291f29652866286628202820291f29662965281f291f291f29662865286628899c823b8087280000000000000000000000000000000000000000000000000000000000",
};
}