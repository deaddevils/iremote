package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class BaisicLine extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "BaisicLine";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 基本线 (Baisic Line) 1
"00e04b0033821381012761261d2761281d2861271d261d261e288101275a281d285a271d275a271e271d271d278606821a81012662271d2761281d2861281d261d261e278101275a271d285a271d275a271e271d271d270000000000000000000000000000000000000000000000",
};
}