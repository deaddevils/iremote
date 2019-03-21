package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Centrex extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Centrex";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Centrex(Centrex) 1
"00e05b003382348111281e281f286328642863291e281e281e2863291e281e291e291e28642863291e291e291e296228642863291e2863286329632863281e2863291e281e2964281f281e281e2964281e28632863291e2863286228642885bc823a811029000000000000000000",
};
}