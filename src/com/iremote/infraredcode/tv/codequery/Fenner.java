package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Fenner extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Fenner";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ∑“ƒ…(Fenner) 1
"00e04b0033820f8101271d2762261d261d261d281d281d261d268102261d265a285a275a271d271d271e271d278606821a8100271d2761271d271d271d271e271d271d278101271d275a275b265a261d271e271d271e260000000000000000000000000000000000000000000000",
};
}