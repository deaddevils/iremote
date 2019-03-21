package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ZhoukouXiangchengcable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ZhoukouXiangchengcable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 周口项城有线(Zhoukou Xiangcheng cable) 1
"00e047003382338110291f291f2966281f282029202866281f28652965291f28662866282028202865286628652a1f2965291f282028202820282028202865291f2a6529652866296628899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}