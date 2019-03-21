package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Luandigital extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Luandigital";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ Áù°²Êý×Ö(Luan digital) 1
"00e047003382348110291f291f29652820281f291f29202820281f2865282028662865286628202866281f291f2966286628642820292028202965286529202920291f296528652a65298997823b8086280000000000000000000000000000000000000000000000000000000000",
};
}