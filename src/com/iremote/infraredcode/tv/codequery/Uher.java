package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Uher extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Uher";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 博士(Uher) 1
"00e0450033820f8101245d255d261c261d251c251c251c251c255d255e255d251c255d251d251c251c25855a255e255e251c251c251c251c251c251c265d255d255e241c265d251c251c251c25000000000000000000000000000000000000000000000000000000000000000000",
//电视 博士(Uher) 2
"00e045003382108101255d255e251c251c251d261c261d251d245e265d255d251d255d261c261d251d25855a265d255e251c261d251d261c261e251c255d255d255d251d265d261d251d261c25000000000000000000000000000000000000000000000000000000000000000000",
};
}