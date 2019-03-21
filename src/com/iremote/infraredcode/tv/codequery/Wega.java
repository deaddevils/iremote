package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Wega extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Wega";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Œ¨ºŒ(Wega) 1
"00e03700341e5b2580da2580d92680d9255c265b25876c255b2580d92580d92580d9255b255b26876c255b2580d92580d92680d9265c255b25876b265b2580da2680d92580d9265c255b250000000000000000000000000000000000000000000000000000000000000000000000",
};
}