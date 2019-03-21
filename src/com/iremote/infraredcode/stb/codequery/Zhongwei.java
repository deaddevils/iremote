package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Zhongwei extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Zhongwei";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÖÐÎÀ(Zhongwei) 1
"00e047003382348111296628202820281f291f2920291f291f2965281f296628652866286628652966291f281f29662966286528202866286529652965281f2920281f29662820281f28899c823b8087290000000000000000000000000000000000000000000000000000000000",
};
}