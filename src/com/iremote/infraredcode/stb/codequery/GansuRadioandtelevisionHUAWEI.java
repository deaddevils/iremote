package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GansuRadioandtelevisionHUAWEI extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GansuRadioandtelevisionHUAWEI";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 甘肃广电华为(Gansu Radio and television HUAWEI) 1
"00e04700338233811128662820281f281f291f291f291f2920281f28672866286529652965286529652965286728202820281f281f291f2a1f291f291f29652867286628652866286529899b823b80862a0000000000000000000000000000000000000000000000000000000000",
};
}