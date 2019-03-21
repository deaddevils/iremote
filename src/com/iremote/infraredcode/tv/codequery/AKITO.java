package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class AKITO extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "AKITO";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 普联(AKITO) 1
"00e02f0037373376333d323d333d323e323e323d323e673e3176313e95cb3e3277323d323e323e313e323e313d313e673e3277313d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 普联(AKITO) 2
"00e02f003734323e3276323d323e323d323e333d323d673e3277323d95c93d313e3377323e313d333d333d323e323d673e3277323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 普联(AKITO) 3
"00e03700340c80d71780d71681d21581d01681d11781331681331681341681d11681341681d11681d11681d0118c461780d61680d71781d11881d01781d11781331781331681331681d11781341781d11881d01781d1120000000000000000000000000000000000000000000000",
//电视 普联(AKITO) 4
"00e02f0037363277323e313e323e313d323f323d323d673e3276323d95c93d3277323e313d333d323d323e323d313e673e3276313d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}