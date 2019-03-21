package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Optimus extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Optimus";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” «ÊÃÏ÷˘(Optimus) 1
"00e04700338233810f291f291f291f29662a6528662820281f2864286528652920281f281f28662865291f2965281f281f286529202820291f2865281f2965286529202865286629652a8994823c8085280000000000000000000000000000000000000000000000000000000000",
};
}