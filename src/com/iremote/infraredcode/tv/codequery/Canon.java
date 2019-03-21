package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Canon extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Canon";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” º—ƒ‹(Canon) 1
"00e02b003731636d30382f3863392f39306c636c30392f393038947338636d31383039633830382f6c646c303830382f3800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}