package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Hankook extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Hankook";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ º«Ì©(Hankook) 1
"00e04700358232810f281f28202866281f291f291f291f291f296528642820286629652965286428652820281f291f2965291f281f281f281f296428652965291f29652864286628652a8992823b8086280000000000000000000000000000000000000000000000000000000000",
};
}