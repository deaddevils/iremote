package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Grandin extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Grandin";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//���� ������(Grandin) 1
"00e04b0033821381022761271d2761271d2761261e271d271d288101275a281d275a271d275b271d271d271d278605821981012661261d2661271d2761271d271d271d268100265a261d275a271e275a271e271e261d260000000000000000000000000000000000000000000000",
//���� ������(Grandin) 2
"00e04b0033821381012661261e2761281d2861281d261d261d268102265b271d285a271d275a271d271d261e278605821b81012861271d2761271e2761271d271d271d278101275a271d275a271e265a261d271d271d270000000000000000000000000000000000000000000000",
};
}