package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Tielingskycable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Tielingskycable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 铁岭天光有线(Tieling sky cable) 1
"00e04700338233811028662820291f291f2a1f291f291f29202866292028662865286628652965296529202820286628652865282029652865296529652820281f29202866281f292029899c823c80862a0000000000000000000000000000000000000000000000000000000000",
};
}