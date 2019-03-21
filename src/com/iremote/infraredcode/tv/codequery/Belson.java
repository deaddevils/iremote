package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Belson extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Belson";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ±¥∂˚…≠(Belson) 1
"00e049003381188115291e296328642963281f291e2820281f281f28642a642864291f281e291e281e29632865291e2863291e2820281e281f281f281e2863291e2863296428632864288b41811f81152a6228000000000000000000000000000000000000000000000000000000",
};
}