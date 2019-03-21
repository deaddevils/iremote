package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Zunyidigital extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Zunyidigital";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ×ñÒåÊý×Ö(Zunyi digital) 1
"00e04700338234810f281f281f281f296528202820286628202865286529642820286529652865281f291f2965281f2965291f291f281f281f286529202865291f2965296528642866288992823a8085280000000000000000000000000000000000000000000000000000000000",
};
}