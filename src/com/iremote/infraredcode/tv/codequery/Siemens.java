package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Siemens extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Siemens";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 西门子(Siemens) 1
"00e03f00421f8098261c261c261d251c261d271c261c251d251d2684e5268098271d273c471d261b251c271c261c263c269cfd288099261c261c261d261c251b261c261c261c261d2600000000000000000000000000000000000000000000000000000000000000000000000000",
};
}