package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Galaxy extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Galaxy";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 银河系(Galaxy) 1
"00e02d0034814a81331281d01281d01181d21281d0128133138134128133128133138134128c421181321281331281d01181d01181d01281d01181321281341181331181321181341100000000000000000000000000000000000000000000000000000000000000000000000000",
};
}