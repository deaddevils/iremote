package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Polaroid extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Polaroid";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ±¶¿ˆ¿¥(Polaroid) 1
"00e04700338232810f291f291f291f2965281f281f291f291f29662866286528202866296529652866286528202865291f291f291f291f281f281f2965292028662866286528662a65288994823b8086280000000000000000000000000000000000000000000000000000000000",
};
}