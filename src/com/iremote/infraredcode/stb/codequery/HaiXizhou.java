package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class HaiXizhou extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "HaiXizhou";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 海西州(Hai Xizhou) 1
"00e047003382348110281f2a1f291f291f29202820282128202866286529662965296529652865286728202820286529662965291f291f291f2965286728202820281f28652a65296528899c823c8086280000000000000000000000000000000000000000000000000000000000",
};
}