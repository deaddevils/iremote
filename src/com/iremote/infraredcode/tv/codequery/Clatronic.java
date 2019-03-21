package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Clatronic extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Clatronic";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 clatronic(clatronic) 1
"00e04100340f6c162e176c172e166a162f172e172d162d162e178262176b162e166c172e166a162e172e172d162d162e178262176b162e166c182e176a162f172e172e162d162f170000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 clatronic(clatronic) 2
"00e04100340f6a162e176b172e166b172e172e162f162e172f188262166a162e176b182e166c162e172f172e162e162f188262166a162e176b162e166b172e162e162e172e172f170000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}