package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Binzhouradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Binzhouradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 滨州广电(Binzhou radio and television) 1
"00e055003481667c26422641262128202742272026432620272026422721274126422720274127202721262027422743272028202641272080e580da2720262128422720284227212742272127422742272127202742264126202641280000000000000000000000000000000000",
};
}