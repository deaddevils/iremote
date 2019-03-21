package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Cytron extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Cytron";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//���� Cytron(Cytron) 1
"00e02b00376e6777313d333e323d323d333d323d673e3278333d9474766676323e323d323d323e323d323e673e3176313e00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//���� Cytron(Cytron) 2
"00e02f003770323d323d323d323e323d323d333d323d673e3278333d947476323d333e313d323e333d323e323e313d683e3277323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//���� Cytron(Cytron) 3
"00e049003481178114291f296428632a64281e281f281f281e281f286428632964281e281f291e291e29632963281f2864281e291e281e291e291e281e2964281f2864286328642864288b3f811f8114296228000000000000000000000000000000000000000000000000000000",
//���� Cytron(Cytron) 4
"00e04700338234811028202820281f282028202820281f2965291f291f2966291f2a1f2965296528202865282028202865291f291f2965281f291f29652866282028652866281f2865288993823b8085280000000000000000000000000000000000000000000000000000000000",
//���� Cytron(Cytron) 5
"00e02f00340a81341181d01181d01281d01281d01181d0128133128133138134128133128133128c431381341281d01281d01181d01281d11281d0118134128133128133118133128134120000000000000000000000000000000000000000000000000000000000000000000000",
//���� Cytron(Cytron) 6
"00e0330042238095271b251a261a261a2639261a261b49372583662a8094253849374838471926372619259a592a8095263748384737481b2738261a2700000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//���� Cytron(Cytron) 7
"00e02f00376f323d313d333d333d323d313d6877333e323d323e323d947276323e323d323d313d333d323d6777323d313d333e323d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//���� Cytron(Cytron) 8
"00e04700338233811028202820281f28662865291f291f291f2965281f291f29662820286628652865291f291f296628202866281f2820282028662965282028662820286528662a65288993823c8086280000000000000000000000000000000000000000000000000000000000",
//���� Cytron(Cytron) 9
"00e02f00340b81321281d01181d01281d11181d11381d01381d11281331281d0128133138134128c421281321181cf1181d11181d11281d01281d11281d01181331181d0128133128133120000000000000000000000000000000000000000000000000000000000000000000000",
//���� Cytron(Cytron) 10
"00e02f00340b81311181331281d01181d01181d01281331281d01281d01281d01281331281d0138c431281321181331181d01181d11281d11281321181cf1181d01181d11281331181cf110000000000000000000000000000000000000000000000000000000000000000000000",
//���� Cytron(Cytron) 11
"00e05b00338234810f2964281e281f28632863281e281f281e2863291f281e281f281e281e29632863281e281f296329632863291e2962286328642962281f291e286429622864291f291e281e28632963291e281e281f2863296428622885bc823b811028000000000000000000",
//���� Cytron(Cytron) 12
"00a065003480d0682116204e21172016201620162016201620152016201620162016204e201621162116211720162016201620162016204e2016211621162117201620162016201621492015204821492049204920162116214920152048214820482049201621492091b580db00",
//���� Cytron(Cytron) 13
"00e047003382338110282028202820282028202865291f29202820281f281f291f291f291f2965281f281f291f29202920282028202820282028652866286529662865296528652964288993823a8086290000000000000000000000000000000000000000000000000000000000",
//���� Cytron(Cytron) 14
"00e04700338232810f28652966291f29202a1f291f291f281f281f281f2964286529652965286528652820281f291f291f291f291f291f281f28652965286529652965286428642865298992823a8086290000000000000000000000000000000000000000000000000000000000",
//���� Cytron(Cytron) 15
"00e04700338234811028202865281f291f291f291f291f28202866291f2a65286628652866286529662820282028202820281f2820281f282028652965296528642865286529652965288993823b8086290000000000000000000000000000000000000000000000000000000000",
//���� Cytron(Cytron) 16
"00e047003382348110271f2866281f291f291f291f291f291f2966291f296528662865296528652965281f291f291f291f291f291f2920282028652a65286628652966286529662865298992823b8086280000000000000000000000000000000000000000000000000000000000",
//���� Cytron(Cytron) 17
"00e047003382338110281f2865291f2920291f291f2965281f2964282028652965296528652920296628662820281f282028202820281f291f29202865286528652966286628652966298993823b8087280000000000000000000000000000000000000000000000000000000000",
//���� Cytron(Cytron) 18
"00e047003382338110281f291f29202820282028662820281f2866286529662866286529202966296528202820282028202820281f281f282028662965286628662865286529652865298992823a8086280000000000000000000000000000000000000000000000000000000000",
//���� Cytron(Cytron) 19
"00e04700338234810f281f2820282028202820282028202820281f2864286629652965286428662865291f291f296528652965282028662820286528662a1f291f291f2965281f2864288994823b8087290000000000000000000000000000000000000000000000000000000000",
//���� Cytron(Cytron) 20
"00e04b003382128101261d2661281d271e271e271d261d261d268100261d265a265a275b271e271e271d261d268605821a8101261d2762261d271e271e261e261d261d278101281d275a275a275b271e271e261d261d260000000000000000000000000000000000000000000000",
//���� Cytron(Cytron) 21
"00e04700338232810f281f281f28202820281f281f291f291f2965286529642865296529652865286528662965291f2965286528202866291f291f291f2965281f281f296528202865298993823a8085280000000000000000000000000000000000000000000000000000000000",
};
}