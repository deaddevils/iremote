package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Dixi extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Dixi";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 迪希(Dixi) 1
"00e04b003382148101271d2861281d271e261d261c261d271e278102271e275b265a265a261d261e281d281d278607821b8101271d2762261d261d271d271d271d271e268100261e275a265a265a271e271e271e261d260000000000000000000000000000000000000000000000",
//电视 迪希(Dixi) 2
"00e04b003382128101281d2761271e261d261d271d271d271e268100261d265a275b275b271e261d261d281d278607821b8102261d2661271e271e271e261d261d271e278102271e275b265a265b271d281d281d271d260000000000000000000000000000000000000000000000",
};
}