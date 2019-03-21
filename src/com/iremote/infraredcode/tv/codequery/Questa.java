package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Questa extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Questa";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Questa(Questa) 1
"00e03700341e5c2580d92580d92680d9255b255b26876a255a2580d92580d92580d9265b255b25876b255c2680d92580d82580d9255b265a25876a255c2580d92580d82580d9255a265b260000000000000000000000000000000000000000000000000000000000000000000000",
};
}