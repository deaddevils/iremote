package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GuizhouZunyidigital extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GuizhouZunyidigital";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 贵州遵义数字(Guizhou Zunyi digital) 1
"00e0470033823381112920281f29202865281f29202866281f28652965296528202867286628652820291f2965291f29652920281f282028202866281f28652a1f296529662866286528899d823b8086280000000000000000000000000000000000000000000000000000000000",
};
}