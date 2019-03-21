package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Radioette extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Radioette";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Radioette(Radioette) 1
"00e03f00421f8099261c261c261b251c251c271c261c261d251d2684e6258097271d261c253b261d481d271c251c263b269cfd288097271c251b261c261c261c261c261c261b251c2700000000000000000000000000000000000000000000000000000000000000000000000000",
};
}