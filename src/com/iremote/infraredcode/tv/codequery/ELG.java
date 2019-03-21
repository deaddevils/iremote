package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ELG extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ELG";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ °£¶û¸ñ(ELG) 1
"00e02f003736323e3276313d333d323e323d323d333d683d3377323d95c33d323e3176323e323d323e313d323e323d673e3277313d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}