package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class HaidongPrefecture extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "HaidongPrefecture";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 海东州(Haidong Prefecture) 1
"00e047003582338110291f291f29202820281e28202920291f29652966296529652867286628652866281f281f29652965296528202820282028652965291f291f281f29662966286528899b823b8086280000000000000000000000000000000000000000000000000000000000",
};
}