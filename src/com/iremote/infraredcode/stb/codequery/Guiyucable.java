package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Guiyucable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Guiyucable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//ª˙∂•∫– πÛ”Ï”–œﬂ(Guiyu cable) 1
"0070230019411a4008140f141014101410141014101410141014321433143314321433143214321433140f140f143214321433140f140f140f14321433140f140f140f14321433143214444a411d4043140000000000000000000000000000000000000000000000000000000000",
};
}