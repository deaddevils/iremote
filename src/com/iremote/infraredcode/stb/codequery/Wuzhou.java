package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Wuzhou extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Wuzhou";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÎàÖÝ(Wuzhou) 1
"00e047003382348110281f29202820291f291f281f281f28652a65296528652966286628652866281f291f2965291f29202866291f291f29652865281f2a652965281f286529662820288996823c8086290000000000000000000000000000000000000000000000000000000000",
};
}