package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Kapsch extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Kapsch";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ø®∆’ ≤(Kapsch) 1
"00e02f00340b81331281d01381d11281d11281d01281d21181d1128133118133128133128133128c431181321281d11281d01281d01281d01181d01181d0128133128133138134128133120000000000000000000000000000000000000000000000000000000000000000000000",
};
}