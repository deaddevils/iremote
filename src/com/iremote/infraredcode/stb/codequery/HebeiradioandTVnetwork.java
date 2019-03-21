package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class HebeiradioandTVnetwork extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "HebeiradioandTVnetwork";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 河北广电网络(Hebei radio and TV network) 1
"00e047003382348110291f281f291f2965292028202820281f29202866286528202820286628652866286529652866281f291f292028652820282028202820286628652866291f2965298996823c8086280000000000000000000000000000000000000000000000000000000000",
};
}