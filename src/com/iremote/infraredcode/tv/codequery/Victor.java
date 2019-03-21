package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Victor extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Victor";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 维克托(Victor) 1
"00e03700341e5b2580d92580d92580da255b265b25876b265b2680d92580d92580d9255c265a25876b255b2580da2580da2580da255b255b25876a255b2680d92580d92580d9265c255b250000000000000000000000000000000000000000000000000000000000000000000000",
//电视 维克托(Victor) 2
"00e03700341a5b2580d92580d82580d9255a255b26876b245b2580d92680d92580d9255b255c25876b255b2580d82580d92580d9255b265b25876a255b2580da2580da2680d9255c255a250000000000000000000000000000000000000000000000000000000000000000000000",
};
}