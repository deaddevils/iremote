package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Bester extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Bester";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//���� ����(Bester) 1
"00e04b0033821381012760261d2661271e2762271e261c261d288101275a281d275a261d265b271d271d271d278606821a81012761281d2861271d2661261d261e281d288101275a271d275a271e275a271d271d271d270000000000000000000000000000000000000000000000",
//���� ����(Bester) 2
"00e04b0033820f81012761281d2761261d2662271d281d281d268101275a271d275a271d265a261d271e271e268605821a81012761261e2861281d2861261d261e271d288101275a271d275b261d275a271d271d271e260000000000000000000000000000000000000000000000",
};
}