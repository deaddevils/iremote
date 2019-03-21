package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Simao extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Simao";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ˼é(Simao) 1
"00e0470033823481112820291f281f281f291f291f2920282028652866286628652a6529652865296528202820286628662965291f291f291f296628662820281f281f2966296528652a899b823b8086290000000000000000000000000000000000000000000000000000000000",
//������ ˼é(Simao) 2
"00e047003382358110291f292028202820281f29202920291f29652966296628652866286628652866281f281f29652965286528202820282028652965291f291f292028662966286528899b823b8086280000000000000000000000000000000000000000000000000000000000",
//������ ˼é(Simao) 3
"00e02b00372c646c626c64383039306d3038633930382f383039944d38646c646c63382f38306c3039643831383038303900000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ ˼é(Simao) 4
"00e0470035823381102865282028202820281f281f281f2a1f291f29652866286628662966296528652a6529652820281f28202820281f281f291f2a1f29652966286529652867286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}