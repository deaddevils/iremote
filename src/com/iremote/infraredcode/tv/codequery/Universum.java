package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Universum extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Universum";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 优信咨询(Universum) 1
"00e03d0042218098261c261d251d261c261d261d261b251b261c2684e6268098263b261b251c481c263b481d271c259d1e288097261b251b251c271c261c261c261d251c261d27000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 优信咨询(Universum) 2
"00e03d00421f8098261c261b251c271d271c261c261c261c261c2684e6268097263c271d251c471c263c491c271c269d21288098261c261c261c261c251c261d271c261c251d26000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 优信咨询(Universum) 3
"00e0450033820f8101245d255d261c251d251c251c251c251c255d255e255d251c255d251d251c251c258559255e255e251c251c251c251c261d251c265d255d255d251d265d251c251d261c26000000000000000000000000000000000000000000000000000000000000000000",
//电视 优信咨询(Universum) 4
"00e0330042218094261a261b271a261a2638261a261b47372683662a8094263848374738271a4837261a269a5c298095273947384837261a4738261a2600000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}