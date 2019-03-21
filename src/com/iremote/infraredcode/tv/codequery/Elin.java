package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Elin extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Elin";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//���� ����(Elin) 1
"00e03f0042218098261c261c261c261c261c261d261b251c271c2684e6278098261c261c263c251c491c261c261c263c279cfe288098261c261c261c261d261c261c251c271c261c2500000000000000000000000000000000000000000000000000000000000000000000000000",
//���� ����(Elin) 2
"00e03f004221809a241c261c261c251c261d271c251c261c261c2684e6278099261c261c263c261c471d261c261d263b269cfe288097261c261b251c271c261c261d251d261c261d2600000000000000000000000000000000000000000000000000000000000000000000000000",
};
}