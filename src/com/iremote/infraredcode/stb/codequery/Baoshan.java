package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Baoshan extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Baoshan";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 保山(Baoshan) 1
"00e047003382358110281f2920281f2820282028202820281f28652965296528662966286629662866281f291f2a652965286629202820282028652866281f281f291f28652965286728899b823b8088280000000000000000000000000000000000000000000000000000000000",
//机顶盒 保山(Baoshan) 2
"00e047003382348110281f2a1f291f291f291f2820282028202866286529662965296629652965286728202820286529662965291f281f291f2965286728202820281f28652a65296529899c823c8086290000000000000000000000000000000000000000000000000000000000",
//机顶盒 保山(Baoshan) 3
"00e02b003732636c636d63383038306d31386438303730393039944d39636e636d63393039306d303963392f3930382f3800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//机顶盒 保山(Baoshan) 4
"00e047003382358110296529202820282029202920291f291f281f296629662865286728662865296629652965291f2920281f2820282028202820291f28652a65296528662866286528899c823b8086280000000000000000000000000000000000000000000000000000000000",
};
}