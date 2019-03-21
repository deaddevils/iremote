package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Appe extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Appe";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ Æ»¹û(Appe) 1
"00e04700338234810f2920281f296628652866281f291f2965296528652920291f291f296528652920291f2965286529652820286628652866286529652965281f291f291f291f2965288a30823b8087280000000000000000000000000000000000000000000000000000000000",
};
}