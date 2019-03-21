package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Audiosonic extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Audiosonic";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Audiosonic(Audiosonic) 1
"00e02f00340b81341281d01281d01181d01181d11281d01181d1118134118132118133118134128c421281331281d01281d01281d01281d11281d01281d0128133128133138134128133120000000000000000000000000000000000000000000000000000000000000000000000",
};
}