package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class DalianHisense extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "DalianHisense";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 大连海信(Dalian Hisense) 1
"00e0470033823581102a652920281f292028202820281f291f2920296528652865296528662866286629652965281f291f281f291f29202820281f282029662965286529652966286529899b823b8087280000000000000000000000000000000000000000000000000000000000",
//机顶盒 大连海信(Dalian Hisense) 2
"00e0470033823481102865291f291f291f28202820282028202820286529652965296628662865286728662865291f2a1f291f291f292028202820282028662865296629652965296529899b823c8086280000000000000000000000000000000000000000000000000000000000",
};
}