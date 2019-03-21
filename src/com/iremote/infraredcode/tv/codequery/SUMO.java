package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class SUMO extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "SUMO";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 SUMO(SUMO) 1
"00e04b003381ee80f223582459231c231d231b2358231b231b2380f2241c235823582458241c241c241c231b2385c781f780f224582458231b231b231b2458251b251b2580f2231b245925582358231b231b231b231c240000000000000000000000000000000000000000000000",
//电视 SUMO(SUMO) 2
"00e04b003381ee80f224592358241b241b241b2458231c231b2380f2241c245924582358241b241b241b241c2485c881f680f223572359231c231c231b2358231b231b2380f2231c235923582358231b231b241b241c230000000000000000000000000000000000000000000000",
//电视 SUMO(SUMO) 3
"00e02f00376b313d323e323d323e323e323d323e323e663d3377323e947578323d323d323d323e323d323d323e323d663d3277323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 SUMO(SUMO) 4
"00e02b00376f6776333e323d323d333d333d323d673e3276333d9475776777323d323d323e323d323e323e673d3277323e00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 SUMO(SUMO) 5
"00e049003481188115291e296429642964291e2a1e291e291e281f296428632964281f281e281e281f2964296328202864281e291e291e291e291e281f2964281e2864296528642863288b4081208114296329000000000000000000000000000000000000000000000000000000",
//电视 SUMO(SUMO) 6
"00e04100311e5a2719255a265a265b261927192719261a271a2685fa265a2719275a265b265a271925192719271926192685fc265b2619265b265a265a26192719261a26192719270000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 SUMO(SUMO) 7
"00e047003382348110282028202820286528662865291f291f296529662a65291f291f281f2865296628202820286628652866291f291f291f29652966291f2a1f291f296528652964288994823a8086280000000000000000000000000000000000000000000000000000000000",
//电视 SUMO(SUMO) 8
"00e02b00366f313d333d313e6777323d323d323e673e3278333d947576323e323e313e6777323e323d313e673e3277323d00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 SUMO(SUMO) 9
"00a0650034116d182b182b182c182c182d186e186e182c186e182d192d192d196e182c188a6b186c182e172e182f182e196c182e182e176c182e186b186c186c182e186c188995186d192b192b182b182b182c186f186e182c186e182c182c182d196f182c188a6a186c182e1800",
};
}