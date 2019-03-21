package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Neckermann extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Neckermann";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ÄÍ¿¨¶ûÂü(Neckermann) 1
"00e03f0042218098261c261d251d261c261d271c251c261c261c2684e6258097261c263b481c261c261d261b251b263b269cfe278098261c261d251d261c261d271d251c251b261c2600000000000000000000000000000000000000000000000000000000000000000000000000",
//µçÊÓ ÄÍ¿¨¶ûÂü(Neckermann) 2
"00e03f0041218098261c261c261c261c261c251c251c271c261c2684e7268097251b263b481d261c251b261c261c263c269d01278098261c251b261c261c261d261c261b251c271c2600000000000000000000000000000000000000000000000000000000000000000000000000",
};
}