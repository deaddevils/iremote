package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class WenzhouHuawei extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "WenzhouHuawei";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 温州华为(Wenzhou Huawei) 1
"00e0470033823481112820291f291f2865281f291f2965281f286628662865292029652965286529202820286628202866281f281f2a1f291f296528202867282028662965296528652a899b823b8086290000000000000000000000000000000000000000000000000000000000",
//机顶盒 温州华为(Wenzhou Huawei) 2
"00e0470033823481102820281f291f2966291f281f2966291f29652866286628202865296629652920281f29662820286628202820291f281f2865291f29652820286728662865296629899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}