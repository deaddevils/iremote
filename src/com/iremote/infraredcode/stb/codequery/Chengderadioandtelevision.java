package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Chengderadioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Chengderadioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 承德广电(Chengde radio and television) 1
"00e047003382348110281f281f2965291f291f291f29202820281f2966281f286528662866291f2966291f291f28652965296529202820281f29662865282028202820296528652966298998823c8086290000000000000000000000000000000000000000000000000000000000",
//机顶盒 承德广电(Chengde radio and television) 2
"00e047003382348111291f29202865291f291f291f28202820282028662820286529662965281f2965291f291f296529652965291f2920282028652965281f28202820286628652865288998823c8086290000000000000000000000000000000000000000000000000000000000",
};
}