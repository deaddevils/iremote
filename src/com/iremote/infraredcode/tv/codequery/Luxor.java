package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Luxor extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Luxor";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 卢克索(Luxor) 1
"00e03f0042218099261c261c261d261c251b261c261c261c261c2684e6268097251b261c261c261c261d263c481c263b259cfc288098261d251c261d271c261c261d251d261c261d2700000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 卢克索(Luxor) 2
"00e03f0042218098261c261c261c261d261c251b261c261c261c2684e6268099261c261d271c251c261c263b481c263c269cfc278098261c251c251d261c261c261d261c251b261c2600000000000000000000000000000000000000000000000000000000000000000000000000",
};
}