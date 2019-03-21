package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Goldfunk extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Goldfunk";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Goldfunk(Goldfunk) 1
"00e02f003c0a8205128155128221128221128221128221128220118154128221128155128154128993128204128155138222128220118220128221128220128155128220118154128154130000000000000000000000000000000000000000000000000000000000000000000000",
};
}