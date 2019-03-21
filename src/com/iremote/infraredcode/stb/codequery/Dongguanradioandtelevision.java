package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Dongguanradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Dongguanradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 东莞广电(Dongguan radio and television) 1
"00e037003382358110291f291f2964281f286328202864292028202820281f281f286428202864282028642863296428202963281f2863291f298bc6823d808628000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}