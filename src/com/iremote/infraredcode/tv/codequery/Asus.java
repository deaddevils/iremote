package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Asus extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Asus";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ »ªË¶(Asus) 1
"00e0470033823381102865296628652920286529662965282028202820281f28642820282028202866282028652966291f296628202866281f2866281f281f2965291f2965281f2965288994823b8086290000000000000000000000000000000000000000000000000000000000",
};
}