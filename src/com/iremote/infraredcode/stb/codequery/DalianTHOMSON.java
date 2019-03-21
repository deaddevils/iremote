package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class DalianTHOMSON extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "DalianTHOMSON";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ´óÁ¬THOMSON(Dalian THOMSON) 1
"00e033003c1c81112681112477247825782578257824811225811226811024792481102587d0248110258111257825782478247825782581112481112581112578258111250000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}