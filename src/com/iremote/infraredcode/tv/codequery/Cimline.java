package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Cimline extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Cimline";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” –¡ƒ∑¿≥ (Cimline) 1
"00e047003382338110281f291f281f291f291f291f291f29662865296628652965281f29662965282028202866281f28652820281f291f29202865281f2966291f2966286628652866298993823b8087280000000000000000000000000000000000000000000000000000000000",
};
}