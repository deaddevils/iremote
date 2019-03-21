package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Panasonic extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Panasonic";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ËÉÏÂ(Panasonic) 1
"00a065003480d468214f2116204f204e20152016201620162016201621162117211621172017214e2116201620162116201620162016201620162015201621172016201621162117204a2115204821482149204a21152016201620492215221522152115214921482191c480db00",
};
}