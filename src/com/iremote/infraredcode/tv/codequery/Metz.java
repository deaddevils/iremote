package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Metz extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Metz";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” √∑Àπ(Metz) 1
"00e03f00421f8098261c261c261d251c261d271c251c261c261c2684e6258098261c263c481b261c261c261c261d253b259d00288097261c261c251c261d271c251c261c261c261c2600000000000000000000000000000000000000000000000000000000000000000000000000",
};
}