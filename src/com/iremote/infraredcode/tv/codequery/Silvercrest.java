package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Silvercrest extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Silvercrest";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ¹Ú(Silvercrest) 1
"00e02b0037706777323d323d323d313e6777323d323d323e323d9472766777323e323e313e333e6777323e323e323d323d00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}