package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GuizhouZunyiHuawei extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GuizhouZunyiHuawei";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 贵州遵义华为(Guizhou Zunyi Huawei) 1
"00e047003382338110291f291f29202865282028202866281f28652965296528202866286528662820291f28652a1f2965291f2820282028202866281f28652a1f296529662966286528899d823b8086280000000000000000000000000000000000000000000000000000000000",
};
}