package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class YantaidigitalTV extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "YantaidigitalTV";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 烟台数字电视(Yantai digital TV) 1
"00e0470034823481112720282128202820291f281f281f291f29652965286728662865296629652865291f291f2965282028662820281f291f2965296529202865292028652866286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}