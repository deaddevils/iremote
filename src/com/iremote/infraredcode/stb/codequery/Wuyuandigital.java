package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Wuyuandigital extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Wuyuandigital";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 五原数字(Wuyuan digital) 1
"00e047003382338110291f291f29202820282028202820282028652966296529662966286528672866281f2965291f2965291f281f291f2920286528202866281f28652a652965286629899b823b8087290000000000000000000000000000000000000000000000000000000000",
};
}