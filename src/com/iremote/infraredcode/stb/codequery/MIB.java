package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class MIB extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "MIB";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 小米盒子(MIB) 1
"005032001a240e1610152b162b1610162b1510152b1611162b152b15417e280e1510152c162c1610152b1510162b1610162b162b16417e270e1611162b152b1510162b1610162b1610162b152b16417e270d1510162b162b1610162b1610162b1510152b162c16417e270e161000",
//机顶盒 小米盒子(MIB) 2
"00a0640034481b2b202c572c582c212b572c212c572b212c582c572c82fb501c2c212b582b572c212c572b212b582d212b562b562b82fc501d2b212c572c572c202b582c222b562b202c572c572c82fb501c2c202b562b572d222c562b212c572c202b572c572c82fc4f1c2c2100",
};
}