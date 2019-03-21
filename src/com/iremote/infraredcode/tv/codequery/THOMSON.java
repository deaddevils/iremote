package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class THOMSON extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "THOMSON";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//���� ��ķѷ(THOMSON) 1
"00e033003c2275287529810d28810e28810e2876287628810d29810d28810d297528810f2887a62975287528810d28810d29810e2976287529810d28810e28810e287628810e29000000000000000000000000000000000000000000000000000000000000000000000000000000",
//���� ��ķѷ(THOMSON) 2
"00e033003c2275297528810e28810e28810d2875287529810d28810e2a810d2a7529810f2887a828752a7529810d28810e28810e297528752a810d28810d28810e287628810d29000000000000000000000000000000000000000000000000000000000000000000000000000000",
//���� ��ķѷ(THOMSON) 3
"00e02f00340a81331181d11281d01381d11281d11381341281d11181d11281d01281331281d0118c441281331181d11281d01281d01181d11281331281d01181d11181d11281331381d1120000000000000000000000000000000000000000000000000000000000000000000000",
//���� ��ķѷ(THOMSON) 4
"00e02f00340b81341381341181d01281331281d01381341281d01281d01281d01281341281d0128c421281341281331181d11281331281d01281331281d01281d01281d11281331281d0110000000000000000000000000000000000000000000000000000000000000000000000",
//���� ��ķѷ(THOMSON) 5
"00e0450035820e8101255d255d251c261d251d261c251d261c255d255e255d251c255c251d261c261d25855b255d255d251c251c251c251c251c251c265d255d255e251c255d251c251c251c25000000000000000000000000000000000000000000000000000000000000000000",
//���� ��ķѷ(THOMSON) 6
"00e00f00332201165329271400179c453f532929290000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//���� ��ķѷ(THOMSON) 7
"00e00f00341d01165229122a9c57290017522a132a0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}