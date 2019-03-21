package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Electron extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Electron";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ °@×¿ËÌ(Electron) 1
"00e04700338234811028202820282028202820281f281f281f2965286628652965296629652866282028202864281f286529202820281f28202866281f2965291f2965286529662866288994823c8086280000000000000000000000000000000000000000000000000000000000",
};
}