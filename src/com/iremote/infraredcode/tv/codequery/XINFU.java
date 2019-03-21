package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class XINFU extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "XINFU";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 新抚(XINFU) 1
"00e04b003382128102271d2861271d261d261e281d281d281d268101271d265a265b275a271d271d271d271d278607821a8102271d2761271d261e271d281d271d261d268102261d265b275a275a271d271d271e271d270000000000000000000000000000000000000000000000",
//电视 新抚(XINFU) 2
"00e04b003382138101281d2761271e261d271d271d271d271e268100261d275a275b265a261e281d281d281d268607821a8100271d2761271d271d271e271d271d271d278101261e265a265a275b271e261d261c261d270000000000000000000000000000000000000000000000",
};
}