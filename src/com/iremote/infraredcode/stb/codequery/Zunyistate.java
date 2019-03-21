package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Zunyistate extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Zunyistate";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 遵义同州(Zunyi state) 1
"00e047003382348110282028202820286529202820281f281f291f296528662820281f286628652966286529652965281f291f29202965291f291f291f291f286528652965281f2965298992823b8087280000000000000000000000000000000000000000000000000000000000",
};
}