package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Changsha extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Changsha";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ��ɳ(Changsha) 1
"00e047003382348110281f281f291f2a1f2a1f291f2965281f281f291f2a652866282028642820282028662820286528202820281f2865291f291f2866281f286529652865281f2965288995823c8087290000000000000000000000000000000000000000000000000000000000",
//������ ��ɳ(Changsha) 2
"00e047003382348110281f291f291f2920291f291f2965281f291f29202a652965281f2965282028202965282028642821282028202866281f28202866291f296529652865291f2966288995823b8087280000000000000000000000000000000000000000000000000000000000",
//������ ��ɳ(Changsha) 3
"00e047003582328110291f2a1f2a1f291f291f291f2865281f291f2a1f29652866281f28642820281f2965291f29652920291f291f2965291f281f2865291f29662866286528202866298994823b8086280000000000000000000000000000000000000000000000000000000000",
};
}