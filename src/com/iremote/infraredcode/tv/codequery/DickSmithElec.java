package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class DickSmithElec extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "DickSmithElec";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//���� �Ͽ�ʷ��˹��(Dick Smith Elec) 1
"00e02f0037373176323e333d323d323e313e333e313d673d3278323d95c73d3277323e313e323d323e323e313e333e673d3276323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//���� �Ͽ�ʷ��˹��(Dick Smith Elec) 2
"00e02f003736323e3177323e313d323e313d333e323d673e3176323e95c53f323d3276313d333d323d323e323d313e673d3277323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}