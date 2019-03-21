package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class LinyiHuawei extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "LinyiHuawei";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÁÙÒÊ»ªÎª(Linyi Huawei) 1
"00e047003582338110291f291f292028662920282028652920296528652965291f296528662966281f281f2966291f2965291f291f29202820286528202866281f28652a652965286629899b823b8087290000000000000000000000000000000000000000000000000000000000",
};
}