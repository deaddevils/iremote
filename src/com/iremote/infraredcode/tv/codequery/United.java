package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class United extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "United";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 联合(United) 1
"00e02b00376c6676313e323e313d323d323d323e673e3276323d9474776776333d323d323d323e323d323d683e3377323d00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}