package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Shangqiuradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Shangqiuradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 商丘广电(Shangqiu radio and television) 1
"00e047003382348110281f292028202820282028202864281f292028202866286528202866281f291f2965291f2965281f291f2a1f2965291f281f28652920296529652965292029662a8994823b8086280000000000000000000000000000000000000000000000000000000000",
//机顶盒 商丘广电(Shangqiu radio and television) 2
"00e04700338234810f281f282028202820282028202865281f2820282028662865281f2866281f29202865281f2866281f291f291f2965292028202865291f29652865286529202866288995823b8086290000000000000000000000000000000000000000000000000000000000",
};
}