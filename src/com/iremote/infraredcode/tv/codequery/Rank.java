package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Rank extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Rank";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ¿ºøÀ(Rank) 1
"00e02d00412380a8253b481c261c261d271c2686122a80a4261c261d263c261c261c261d279f232a80a4261d271d273c261c261d271d2700000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}