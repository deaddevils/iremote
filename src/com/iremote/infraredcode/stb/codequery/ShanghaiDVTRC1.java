package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ShanghaiDVTRC1 extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ShanghaiDVTRC1";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÉÏº£DVT-RC-1(Shanghai DVT-RC-1) 1
"00e02700376c313e6876673e3277323e6777323e313d323e95d678313d6777673e3278333e6777323e333d323d0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}