package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class OttoVersand extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "OttoVersand";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ∞¬Õ–” π∫(Otto Versand) 1
"00e03f00421e8098261c261c261c261c261c261d251c261d271c2684e7268097261c253c481c261d271c251b261c263c259cff288099261c261c261b251c271c271c261c251d261c2600000000000000000000000000000000000000000000000000000000000000000000000000",
//µÁ ” ∞¬Õ–” π∫(Otto Versand) 2
"00e03f0042218099261c261c261d261c251b261c261c261c261c2684e5268098261c273c471c261b251c271c261c263c269cff278097261d261c261d271d271c251b261c261c261c2600000000000000000000000000000000000000000000000000000000000000000000000000",
};
}