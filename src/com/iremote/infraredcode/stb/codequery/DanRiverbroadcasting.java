package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class DanRiverbroadcasting extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "DanRiverbroadcasting";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 丹江广电(Dan River broadcasting) 1
"00e047003382348110281f2a1f291f291f291f2820282028202866286529662965296629652965286728202820286529652965281f281f281f2966286528202820282028652965296529899c823c8086290000000000000000000000000000000000000000000000000000000000",
};
}