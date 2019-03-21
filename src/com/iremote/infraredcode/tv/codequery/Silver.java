package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Silver extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Silver";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ Îý¶û¸¥(Silver) 1
"00e0370033175b2680d92580da2580d9255c255b25876b255c2580d92580d92580d8255b255b25876a265b2580d92580d92580d9255b265b25876a265b2580da2780d92580da275a255b250000000000000000000000000000000000000000000000000000000000000000000000",
};
}