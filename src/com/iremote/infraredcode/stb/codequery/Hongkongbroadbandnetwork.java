package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Hongkongbroadbandnetwork extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Hongkongbroadbandnetwork";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ Ïã¸Û¿íÆµ(Hongkong broadband network) 1
"00e0470033823581102a1f29202820281f292028202820281f2965296528652965296528662966286629202820296528652965291f282028672866286629202820291f29652965291f29899b823c8086290000000000000000000000000000000000000000000000000000000000",
//»ú¶¥ºÐ Ïã¸Û¿íÆµ(Hongkong broadband network) 2
"00e0470033823581102a1f29202820281f292028202820291f2965296528652965296528672866286629202820296528652965291f282028672866286529202820291f29652865291f29899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}