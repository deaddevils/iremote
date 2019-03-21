package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Tangshancable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Tangshancable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 唐山有线(Tangshan cable) 1
"00e047003382348110281f2920291f291f29202820282028202866286529662965286529652965286728202820286529662965281f291f291f2965286728202820281f28652a65296529899c823c8086290000000000000000000000000000000000000000000000000000000000",
};
}