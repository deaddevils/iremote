package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Furichi extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Furichi";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ¸»Èð³Û(Furichi) 1
"00e05b003382348110291f291e286328632864281e281f281e2863291e281e2a1e281e28632963291e281e291e286228642863281e2863296329632863291e2864281e281e2862281f291e281e2963281e28642964291e2863296329632885bc823a811028000000000000000000",
};
}