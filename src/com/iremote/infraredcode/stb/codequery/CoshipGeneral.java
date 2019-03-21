package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class CoshipGeneral extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "CoshipGeneral";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 同洲通用(Coship  General) 1
"00e0470033823381102820281f281f29202820291f2965291f291f291f2965286728202866281f291f2a65291f2966281f292028202866292029202965281f2966296628652821286628899c823b8087280000000000000000000000000000000000000000000000000000000000",
};
}