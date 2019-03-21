package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Diamond extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Diamond";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ◊Í Ø(Diamond) 1
"00e049003481168115291e286428642863291e281e291e291f291e286428642863291e281f291e281f29632865291e2863291e281f291e2820281e281f2864291f2864296329642863298b3e81208114286128000000000000000000000000000000000000000000000000000000",
};
}