package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Autovox extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Autovox";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 奥特华 (Autovox) 1
"00e02d0034814881331281d01181d11181d01281d01181d0128133128133138134128133128c431181341281331281d01181d11181d11281d01181d11281331281331281331281331200000000000000000000000000000000000000000000000000000000000000000000000000",
};
}