package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Imperial extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Imperial";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 因皮里尔(Imperial) 1
"00e02f00340b81341281d01281d01181d01281d01281d0128134118133118133118133128133118c421181321281d11281d11281d01281d01281d0118132118133118134118133118132110000000000000000000000000000000000000000000000000000000000000000000000",
};
}