package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Etron extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Etron";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ÷«∞Ó(Etron) 1
"00e047003382328110281f291f29202920286628202820281f2864286629652965281f28652966286529652965286529202866282028652866281f281f291f2965291f2966291f2a1f2a8993823c8086280000000000000000000000000000000000000000000000000000000000",
};
}