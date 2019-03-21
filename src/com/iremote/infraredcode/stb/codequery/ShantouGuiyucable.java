package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ShantouGuiyucable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ShantouGuiyucable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 汕头贵屿有线(Shantou Guiyu cable) 1
"00e047003382348110281f2a1f291f291f29202820282028202866286529662965296529652965286728202820286529662965291f291f291f2965286728202820281f28652a65296529899c823c8086290000000000000000000000000000000000000000000000000000000000",
};
}