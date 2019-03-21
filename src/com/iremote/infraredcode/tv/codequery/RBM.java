package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class RBM extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "RBM";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 国际化(RBM) 1
"00e02d00422280a7253b471c261c251b261c2686132a80a5261c261c263b251b261c261c269f272a80a4251b261c263b251c251d261c2600000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}