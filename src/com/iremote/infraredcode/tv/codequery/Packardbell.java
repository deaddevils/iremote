package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Packardbell extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Packardbell";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ÅÁ¿¨µÂ-±´¶û(Packard bell) 1
"00e02b00376b6777323d323d333d333d323d313d683e3377323e9473786777313d323e323d323e323e313e673e3277323d00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}