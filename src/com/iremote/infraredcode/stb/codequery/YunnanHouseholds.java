package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class YunnanHouseholds extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "YunnanHouseholds";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 云南户户通(Yunnan  Households) 1
"00e047003382358110291f2920286528662820281f28652a652965292028662966286628202820291f281f281f296529652820282028202820286628652a1f291f296528662966286628899b823b8087290000000000000000000000000000000000000000000000000000000000",
};
}