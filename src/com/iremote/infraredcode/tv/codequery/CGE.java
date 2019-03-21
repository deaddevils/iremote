package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class CGE extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "CGE";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 电器公司(CGE) 1
"00e02f00340a81331181d01181d11281d01281d21181d1128133138134128133128133138134128c431281331281d01281d11281d01381d21181d1138133138133128133128133128134120000000000000000000000000000000000000000000000000000000000000000000000",
};
}