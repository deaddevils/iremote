package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class PatheMarconi extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "PatheMarconi";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 百代马可尼(Pathe Marconi) 1
"00e02d00422080a7253b471d251c251c271c2686132a80a5261c261c263b251c271c261c269f262a80a4261c261d273d261c261c261d2700000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}