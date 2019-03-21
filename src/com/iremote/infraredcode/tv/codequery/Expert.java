package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Expert extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Expert";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 专家(Expert) 1
"00e02f00340c81321181321181d01181d01281d01281d01281d1128133128133128133128134128c421381341181321281d11181d01181d01281d01281d0128133138134138133128133120000000000000000000000000000000000000000000000000000000000000000000000",
};
}