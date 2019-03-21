package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ShandongYangxin extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ShandongYangxin";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 山东阳信(Shandong Yangxin) 1
"00e047003382348110286628202820291f281f281f291f291f2965281f286728662865296529652865291f291f296528672866281f28652a6529652965291f2920281f28672820282028899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}