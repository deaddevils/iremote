package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class AnhuiHaier extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "AnhuiHaier";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ °²»Õº£¶û(Anhui Haier) 1
"00e0370033823481102920281f2863281f286228202863291f291f291f291f28202864291f2a632a1f2963286428642820286328202864281f298bbf823b80872a000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}