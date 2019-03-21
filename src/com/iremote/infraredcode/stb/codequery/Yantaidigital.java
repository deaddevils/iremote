package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Yantaidigital extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Yantaidigital";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÑÌÌ¨Êý×Ö(Yantai digital) 1
"00e047003382348110281f281f29202820291f291f281f292028652965286728662865296529652865291f291f2965282028662820281f281f2865296529202866292028662865286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}