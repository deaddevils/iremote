package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Jinanradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Jinanradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ���Ϲ��(Jinan radio and television) 1
"00e0470033823381102965291f28202820282028202820281f28652a65296528652965286528662866291f29202a6529652865292028652867286628652920291f291f2965281f281f29899b823b8088280000000000000000000000000000000000000000000000000000000000",
//������ ���Ϲ��(Jinan radio and television) 2
"00e04700338233811128662820281f291f2a1f291f291f29202866286628662966286628652a6529652820281f29662866296628202865296529652966281f2920282028662920282029899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}