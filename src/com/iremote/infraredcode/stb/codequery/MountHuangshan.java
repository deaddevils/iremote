package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class MountHuangshan extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "MountHuangshan";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ��ɽ(Mount Huangshan) 1
"00e0370033823381102920291f2a1f2963291f291f2963281f281f291f292029202864282028642820286328642863291f2963291f2963281f288bbf823b808728000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ ��ɽ(Mount Huangshan) 2
"00e03700358232810f2820281f28202864281f291f2963291f291f29202920292029632a1f2a63291f29632863286328202864281f2963291f298bbf823c808629000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ ��ɽ(Mount Huangshan) 3
"00e037003382328110291f291f2963281f2863291f296328202820282028202820286228202864281f286329632963291f2863281f29632920288bc0823b808628000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//������ ��ɽ(Mount Huangshan) 4
"00e047003382348110291f291f2965281f281f291f281f291f291f29652920286629652965281f28652920291f2965286529652820291f281f2865296528202820281f286529652866288996823c8085280000000000000000000000000000000000000000000000000000000000",
};
}