package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Acme extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Acme";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 极致(Acme) 1
"00e049003481188115281f281e281f281e291e291e281e2a63291e281e281e291e2820281e2820286429632964281e291e2865281f281e291e291e291e296429632820286428632964288b3e811f8114296228000000000000000000000000000000000000000000000000000000",
//电视 极致(Acme) 2
"00e049003481188115291e291f281e291e281e291e2a1e28632820281e281e281e291e2820281e286528642863281f281e2964281e281f281e281f291e29632964281e286528642963288b3f81208114286228000000000000000000000000000000000000000000000000000000",
//电视 极致(Acme) 3
"00e04b0035821181012761261d2661261d2762271e271e261d268100265a261d275b271e275b261d261d271d288605821a81012861271d2761271e2661261d271d271d278101285a271d275b261d265a261d271e271e270000000000000000000000000000000000000000000000",
//电视 极致(Acme) 4
"00e04900348118811028622862281e2962281f291e281f291e286229632a1e2862281f291e291e291e281e2a62281e2862281e281f291f281f2863281f286228202862286329622962288ac9811d8110281e29000000000000000000000000000000000000000000000000000000",
//电视 极致(Acme) 5
"00a0650033116d192b192b192b172b172b176d196d192b186e182c192b182b186d182b188a6a186c172e182e182e192d186d182e182d186d192f196b176b186d182e186b198995186d182b182c172c182c182c186d196d182b186e172b192b192b196d192b178a69186b192e1800",
};
}