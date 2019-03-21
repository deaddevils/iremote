package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Okano extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Okano";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ °Â¿¨ÅµºÓ(Okano) 1
"00e03900421f8097251c271c261c261c261d253b251c261c4784e6268099253b261c251d471c263b481c261c269d1d288098261c261c261c261d261b253b261c261c4800000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}