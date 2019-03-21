package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Broksonic extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Broksonic";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 Broksonic(Broksonic) 1
"00e0470033823481102965291f2920292029652865296528202820286628652866281f291f291f296529202866291f2965291f281f2865291f2a65291f2865291f2965286628202865288996823c8086280000000000000000000000000000000000000000000000000000000000",
//电视 Broksonic(Broksonic) 2
"00e04700338232811029662820281f281f296528652965281f292028652a6528662820281e281f296628202866281f28652820281f2965291f2965281f2865292028662866281f2866288994823b8086280000000000000000000000000000000000000000000000000000000000",
//电视 Broksonic(Broksonic) 3
"00e047003382348110286628202820281f2865296628662820281f286628652965291f281f29202865291f2965281f2865291f2a1f2965281f2865291f296628202866286528202865298995823b8086290000000000000000000000000000000000000000000000000000000000",
};
}