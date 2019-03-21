package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Zhanjiangradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Zhanjiangradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 湛江广电(Zhanjiang radio and television) 1
"00e03700338234810f281f291f2963281f2863291f296328202820282028202820286328202864281f286329632963291f2863281f29632920288bc0823b808628000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//机顶盒 湛江广电(Zhanjiang radio and television) 2
"00e037003382348110282028202863291f2963281f2863281f291f292028202820286428202863281f286428642a6329202864282028642820288bbe823a808728000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}