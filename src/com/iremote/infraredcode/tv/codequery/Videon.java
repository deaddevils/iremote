package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Videon extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Videon";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Videon(Videon) 1
"00e04700338234811029202820281f292028202820281f2865282028202866281f291f29662965281f29652820291f2965281f291f2a65291f291f28652a65291f29652865291f2a65298996823c8086280000000000000000000000000000000000000000000000000000000000",
};
}