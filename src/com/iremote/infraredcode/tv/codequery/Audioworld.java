package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Audioworld extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Audioworld";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Audioworld(Audioworld) 1
"00e049003481188114291e286328642863281f281e291e2820281e286529632865281f291e291e291e28632864281f2964291e291e291e291e281f291e2865281f2963296428642964298b40811e8114286228000000000000000000000000000000000000000000000000000000",
};
}