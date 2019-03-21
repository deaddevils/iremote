package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class RFT extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "RFT";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” …‰∆µ±‰—π∆˜(RFT) 1
"00e03f0042218097261c261c261c251c271d271c261c261d251d2584e5268097271d271c261c251d261c263b471c263c279dfc278097261d251d261c261d271d251c251b261c261c2600000000000000000000000000000000000000000000000000000000000000000000000000",
};
}