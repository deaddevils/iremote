package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Hubeiradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Hubeiradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ �������(Hubei radio and television) 1
"00e0470035823281102865281f291f291f291f291f292028202866291f2965296528652b6428652965291f281f28652965286628202865286629652966282028202820286528202820288994823b8086280000000000000000000000000000000000000000000000000000000000",
//������ �������(Hubei radio and television) 2
"00e0470033822f81102965281f2820281f282028202820282028652820286629652965286528652866291f291f2965286529652820286628652965286529202920282028662820281f288994823a8086280000000000000000000000000000000000000000000000000000000000",
//������ �������(Hubei radio and television) 3
"00e04700338232810f291f28202820281f281f291f2965291f281f292028652a6528202866281f28202866281f2965291f291f281f2966291f2a1f296528202865286628652a1f2965288993823b8086280000000000000000000000000000000000000000000000000000000000",
//������ �������(Hubei radio and television) 4
"00e04700338234811028652820282028202820281f2820282028662820286528662865296529652864282028202866286528652820286529652965286529202820282028662820281f298995823c8086290000000000000000000000000000000000000000000000000000000000",
};
}