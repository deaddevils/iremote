package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Carrefour extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Carrefour";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 家乐福(Carrefour) 1
"00e03700341e5b2580d92580d92580d9255b265b25876a255b2580d92680d92580d9265b255b25876c255b2580d92580d92580d9255b265b25876a255b2580d92680d92580d8255b255c250000000000000000000000000000000000000000000000000000000000000000000000",
//电视 家乐福(Carrefour) 2
"00e03700341f5b2680d92580d82580d9255a265b26876b255b2680d92680d92580da265a2659258769265b2580d92580d92580d9265b255c258769255c2580da2580d92680d9255b255b260000000000000000000000000000000000000000000000000000000000000000000000",
};
}