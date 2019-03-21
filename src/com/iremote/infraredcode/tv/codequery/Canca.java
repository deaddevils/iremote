package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Canca extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Canca";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 加拿大(Canca) 1
"00e049003381188114291e296328652864281e291e281f291e281e296429642963281f291e2820281e28642863281e29632820281f281f281f281f281f2864281e2963286429642963288b40811f8115286329000000000000000000000000000000000000000000000000000000",
};
}