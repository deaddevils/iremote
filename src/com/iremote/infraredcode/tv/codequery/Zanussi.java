package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Zanussi extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Zanussi";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ÔúÅ¬Î÷(Zanussi) 1
"00e02f00340e81301281331181d11281d01181d11181d11381d2128133138134128133118134118c451281331381341281d11281d11181d11281d21281d0128134128133138133128133110000000000000000000000000000000000000000000000000000000000000000000000",
};
}