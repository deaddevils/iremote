package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Zibodigital extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Zibodigital";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ×Í²©Êý×Ö(Zibo digital) 1
"00e04700358233811028202820281f281f2a1f2a1f291f291f2865286628662865286628652a652965292028202866286528652820291f281f29662965291f281f281f2866286628652a899b823c80862a0000000000000000000000000000000000000000000000000000000000",
};
}