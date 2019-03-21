package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Hanseatic extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Hanseatic";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 汉萨(Hanseatic) 1
"00e03f0042218098261c261d261c261c261d261b251b261c271c2684e6268098261c261c261c261d251c263d471c263c269cff278097261d261c261c261d261c251b261c261c261c2600000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 汉萨(Hanseatic) 2
"00e03f00411e8097251c251b261c261c261c261c261d251c251c2784e5268097261c261c261d251c261d273c471c263b269cfd288098261c261c261c251c261d271c261c251d261c2600000000000000000000000000000000000000000000000000000000000000000000000000",
};
}