package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class AnWeiHaier extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "AnWeiHaier";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ °²Î¢º£¶û(An Wei Haier) 1
"00e0370033823381102820281f28642820286428202864281f281e282028202820286428202863291f296428642963281f2863291f2a63291f298bc5823c808628000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}