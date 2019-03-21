package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Cybermax extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Cybermax";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 金卫信(cybermax) 1
"00e02f00340c81301281331381d11281d01181d01281d0118133118133118134118133118133118c431281331181331181d01281d01281d11181d0128133128134128134128133128133110000000000000000000000000000000000000000000000000000000000000000000000",
};
}