package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Guyuan extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Guyuan";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¹ÌÔ­(Guyuan) 1
"00e04700338235811029662820281f281f29202820291f291f2865281f296628652866286628652966291f281f29662966286528202866286529652965281f291f281f2966281f282028899c823b8087290000000000000000000000000000000000000000000000000000000000",
};
}