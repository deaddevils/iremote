package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Puningcable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Puningcable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÆÕÄþÓÐÏß(Puning cable) 1
"00e03700358233810f291f28202863281f2963291f2963291f281f281f281f291f2964282028642820286428632964291f2a63292029632820288bbf823b808629000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}