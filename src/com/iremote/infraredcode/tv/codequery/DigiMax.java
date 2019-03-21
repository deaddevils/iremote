package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class DigiMax extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "DigiMax";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ µÑ¼«Âê(DigiMax) 1
"00e05b0033823281112a1e291f286428632963281e281f281e2863291e281e291f281e286328632a1e281e291f286428632863281e2863296329642863291e2964281e281f2863281f291e281e2963281e28632964281e2863296329632885bd823b811028000000000000000000",
};
}