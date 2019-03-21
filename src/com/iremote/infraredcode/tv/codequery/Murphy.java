package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Murphy extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Murphy";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ Ä«·Æ(Murphy) 1
"00e03f00421f8098261c261c261c261c261c251c271c261c251d2684e6268098261c261c263b251c481d261c261c263d279d00278097261c261c261d271c251b261c261c261c261c2600000000000000000000000000000000000000000000000000000000000000000000000000",
};
}