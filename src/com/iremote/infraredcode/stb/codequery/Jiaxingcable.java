package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Jiaxingcable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Jiaxingcable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 嘉兴有线(Jiaxing cable) 1
"00e047003382338110291f291f2920281f281f28202820282029652865296529652866296628662866281f281f286529652965281f281f2920286528652820291f281f28652965296528899c823c8086280000000000000000000000000000000000000000000000000000000000",
};
}