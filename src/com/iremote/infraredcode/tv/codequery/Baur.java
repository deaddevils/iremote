package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Baur extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Baur";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//���� ����(Baur) 1
"00e03f0042218097251c251b261c261c261c261c261c261d271c2684e7258099233b473b251c261c261c261c261d261b259cff288099261c261c261b251b261c271c261c261d251d2600000000000000000000000000000000000000000000000000000000000000000000000000",
//���� ����(Baur) 2
"00e03f00421f8098271d271c251c261c261c261c261d261b251c2784e6258099243b483b261b251c271c261c261c261d259d00288097261c261c251c271d271c261c251d261c261c2600000000000000000000000000000000000000000000000000000000000000000000000000",
};
}