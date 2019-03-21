package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class VideoConcepts extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "VideoConcepts";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 视频的概念(Video Concepts) 1
"00e04700338235811028652820282028652866282028202820281f286529662820281f2865296528662820281f291f29652820291f281f281f28652a652965281f2965286628662864288996823b8087280000000000000000000000000000000000000000000000000000000000",
};
}