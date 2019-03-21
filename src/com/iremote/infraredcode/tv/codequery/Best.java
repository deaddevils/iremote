package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Best extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Best";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 贝斯特(Best) 1
"00e02f00340a81321281d11281d01281331281d01181321181d0128133128133138134128133128c421281331281d01281d01281341181d11281331281d0128133128133128133118133120000000000000000000000000000000000000000000000000000000000000000000000",
};
}