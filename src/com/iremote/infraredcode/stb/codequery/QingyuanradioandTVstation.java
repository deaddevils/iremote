package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class QingyuanradioandTVstation extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "QingyuanradioandTVstation";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 清远广播电视台(Qingyuan radio and TV station) 1
"00e0470033823481112920282028202820281f281f281f2a6529652866296628662966286628652a65291f2966281f29202865281f292028662865291f29652965282028672866281f29899c823b8086290000000000000000000000000000000000000000000000000000000000",
};
}