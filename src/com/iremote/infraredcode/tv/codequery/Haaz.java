package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Haaz extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Haaz";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Haaz(Haaz) 1
"00e049003481168114281f286329642864291e281f291f281f281f286329632964281e291e2820281e28652864281e2964281e281e281f291e281f291e2865281e2865286429632865288b3e811f8115286229000000000000000000000000000000000000000000000000000000",
};
}