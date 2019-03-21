package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Leyco extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Leyco";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Leyco(Leyco) 1
"00e02f00340b81331281d01281d01281d01281d11181331281331281331281d0128133128133138c421281331381d11381d11281d01181d11281331281331281331281d0128133128133120000000000000000000000000000000000000000000000000000000000000000000000",
};
}