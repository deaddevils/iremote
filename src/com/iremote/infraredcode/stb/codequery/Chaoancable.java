package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Chaoancable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Chaoancable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 潮安有线(Chaoan cable) 1
"0050300019411a40081410140f140f141014101410143214101410140f14321432140f1432140f140f1432140f14321410140f14101433140f1410143314101433143214331410143214444b411d404314567a411d4008140f140f140f1410140f140f143314101410140f143200",
};
}