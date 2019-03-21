package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Minerva extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Minerva";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” √‹ƒ˘Õﬂ(Minerva) 1
"00e03f0042218099261c261c261c261b251c271c261c261d251d2684e6278097261d253b471c261c261d261c261d263d269cff288098261c261c261c261d251c261d271c261c251d2600000000000000000000000000000000000000000000000000000000000000000000000000",
};
}