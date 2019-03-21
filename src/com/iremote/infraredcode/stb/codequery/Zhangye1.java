package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Zhangye1 extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Zhangye1";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 张掖广电 (zhangye)1
"00e04700358233811128202820281f28652a1f291f291f2920281f28662866281f281f296529652966281f292028202820281f2966291f291f28652965296528662866281f2865296528899b823c8086280000000000000000000000000000000000000000000000000000000000",
};
}