package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Yanan extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Yanan";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ �Ӱ�(Yanan) 1
"00e047003382348110286629202820291f281f281f291f281f291f2965286728662865296529652865296529652820282028202820281f281f281f2a1f29652965286728662866286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
//������ �Ӱ�(Yanan) 2
"00e0470033823581102965291f2820282128202820291f281f281f296529652866286628652865286628652a65291f291f28202820282028202820281f28652a65296528652965286528899b823c8086280000000000000000000000000000000000000000000000000000000000",
//������ �Ӱ�(Yanan) 3
"00e0470035823381112966292028202820281f29202920291f29652920286529652867286628652965291f291f29662865296528202866286628652a65291f2920292028652920281f28899b823b8086280000000000000000000000000000000000000000000000000000000000",
//������ �Ӱ�(Yanan) 4
"00e0470033823381102a1f281f292028652920282028662820286628652965291f296528652965281f28202866281f28652a1f291f291f291f286628202866281f296529652865286529899b823c8086290000000000000000000000000000000000000000000000000000000000",
//������ �Ӱ�(Yanan) 5
"00e0470033823581102a1f291f2966291f2966281f282029202866281f281f291f2965291f281f282028202820286628652a65291f296628662865286728202820281f28652a1f291f29899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}