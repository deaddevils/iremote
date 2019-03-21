package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GansuRadioandtelevisionstate extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GansuRadioandtelevisionstate";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 甘肃广电同州(Gansu Radio and television state) 1
"00e0470033823381102a65281f281f291f29202820281f281f2966291f286529652965286628662865281f2920286628652a65291f296528672866286629202820291f2865291f291f29899b823b8086290000000000000000000000000000000000000000000000000000000000",
};
}