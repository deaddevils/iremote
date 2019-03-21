package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class LinfenHuawei extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "LinfenHuawei";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÁÙ·Ú»ªÎª(Linfen Huawei) 1
"00e0470033823481112920282028202866281f291f2a65291f296628652965281f2866286628652a1f291f29652820286728202820281f291f2966291f2865281f296628652866286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}