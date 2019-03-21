package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ZhengzhouBeijingTaiyuan extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ZhengzhouBeijingTaiyuan";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 郑州/北京/太原(Zhengzhou Beijing Taiyuan) 1
"00e047003382358110291f292028652821292028202965281f296529652920286628662820281f296529652865291f2965281f2820282028202820281f28652a1f296529662866286528899c823b8086280000000000000000000000000000000000000000000000000000000000",
};
}