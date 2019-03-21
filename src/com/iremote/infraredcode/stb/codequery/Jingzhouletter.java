package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Jingzhouletter extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Jingzhouletter";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 荆州视信(Jingzhou letter) 1
"00e047003582338111296529202820281f281f2920291f291f291f2966296529652867286628652966291f2865291f291f2965281f281f28202866281f28652a65291f29662866286528899c823b8086280000000000000000000000000000000000000000000000000000000000",
};
}