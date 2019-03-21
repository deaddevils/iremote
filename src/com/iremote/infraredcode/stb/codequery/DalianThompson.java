package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class DalianThompson extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "DalianThompson";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ´óÁ¬ÌÀÄ·Ñ·(Dalian Thompson) 1
"00e033003c1c81112581122678257826782479247924811125811125811224782581112587d0268111248111257824782578257826782581122681102481122478248110250000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}