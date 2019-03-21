package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Gorenje extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Gorenje";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ Èû¶ûÎ¬ÑÇ(Gorenje) 1
"00e03d0042208097261d251c261d271c251c261c261c261c261c2684e6258097263c271c261c481c263d481d261c269d1e288097251b251c271c271c261c261d261c261c261d26000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}