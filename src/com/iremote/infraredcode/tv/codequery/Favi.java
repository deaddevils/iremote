package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Favi extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Favi";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ¡˘Ω«Õﬂ(Favi) 1
"00e04700358232810f29652820281f281f291f291f29202820291f296528642865286529652865286428652965296528652965282028202820281f281f281f291f29202a6528662865288995823b8086290000000000000000000000000000000000000000000000000000000000",
};
}