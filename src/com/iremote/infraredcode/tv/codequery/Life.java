package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Life extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Life";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 人生(Life) 1
"00e02f00376f313e323d323d323e323d323e323e313e673d3377313d947377313d333d323d323e323d313e323d323d673d3276333d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 人生(Life) 2
"00e02b00376d6676313e323e313d323e323d323e673d3176313e9472766878333d333d323d323d323e323d673d3278333d00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 人生(Life) 3
"00e02f00340a81321181331281d11281d01181d11381d01281d01181331181d1128133128133118c431181331281331381d11281d11281d01181d01281d01281331281d0138134128133120000000000000000000000000000000000000000000000000000000000000000000000",
//电视 人生(Life) 4
"00a065003480d4682116214f21162017211620162116211621172016201620162016214e211621172016201620162017201620162116214e2016201620172116201621162116211720492015204a1e492048204b1e152015204b1e16204920482049204b1e1520492091b580db00",
//电视 人生(Life) 5
"00e04b003382138101271d261d2661261d2762271e271d271e268100265a265a2659265a285b251d271e271e268605821a8102271d281d2661261d2661261d261d281d288101265a285a285b255a285a271d261d261e270000000000000000000000000000000000000000000000",
};
}