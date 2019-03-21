package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Dumont extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Dumont";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 杜蒙特(Dumont) 1
"00e03f00421f8097261c261c261c261c261d261b251b261c261c2684e6258097261c261b253b261c481d271c251b263b269dfc288098251d261c261c261d261d251c251b261c261c2600000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 杜蒙特(Dumont) 2
"00e03f00421f8098261c261c261c261d261c251b261c261c261c2684e7268097251b251b263b261c481c251c261c263b269cfb288097261d261b251b261c271c261c261c261d251c2600000000000000000000000000000000000000000000000000000000000000000000000000",
};
}