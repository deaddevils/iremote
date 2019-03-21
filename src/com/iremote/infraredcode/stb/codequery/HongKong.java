package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class HongKong extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "HongKong";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ Ïã¸Û(Hongkong)-NOWTV 1
"007023001a4119400814101410140f1410150f140f14321410140f141014101410140f140f1433140f140f1433140f143214101410141014101432140f1532140f143214331433143214444d411d4043140000000000000000000000000000000000000000000000000000000000",
};
}