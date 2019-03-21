package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class WEIPAI extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "WEIPAI";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 威派(WEIPAI) 1
"00e04b003382138101271d2861271d271e271d271d281d271d268101271d275a275a275a271e261d261d261d278607821b8101271e2762271e261d261d271e271e271d268100271d265a285a285a281d261d261e271d280000000000000000000000000000000000000000000000",
//电视 威派(WEIPAI) 2
"00e04b003582128101271e2761261c261d281d271e271d261c268101271d285a285a275a271e271d271d271d278606821b8102271d2761271d271d271d271d271e271d278101281d275a275a275a261c261d271e271e270000000000000000000000000000000000000000000000",
};
}