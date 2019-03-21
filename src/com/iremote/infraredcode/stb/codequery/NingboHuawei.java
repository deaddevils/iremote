package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class NingboHuawei extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "NingboHuawei";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ������Ϊ(Ningbo Huawei) 1
"00e0470033823381102a1f281f292028652920282028662820286628652965291f296528662966281f2821296628202865291f2a1f291f291f2865282128662820286529662965296529899b823c8086290000000000000000000000000000000000000000000000000000000000",
//������ ������Ϊ(Ningbo Huawei) 2
"00e047003382358110291f292028202866292028202965281f2965296528662820286628662966291f281f2865291f296528202820282028202866281f2965291f296528662966286528899b823b8086280000000000000000000000000000000000000000000000000000000000",
};
}