package com.iremote.infraredcode.tv.codequery;

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
//电视 乐声(Panasonic)  1
"00a065003480d4682016204e20162016201620152016201620162016201520162016204f201721162016201621162016211621162017214e2016201621162116211720172116211720492015204820482049204920152015204821152148214821482149201520492091bf80db00",
//电视 乐声(Panasonic)  2
"00a065003480d4682116204e20162016211621172016201620162016201620152016204e201620162016201620162116201620162016204e2117211620162116211720172116201621482116214920492049204822152215214821152148214820482049201620492091bc80db00",
//电视 乐声(Panasonic)  3
"00a065003480d4682116214e20162117201620162016211621172016201620162016204e201620152016201620162016201620162016204e20162116211620172116201720162216204a2116204920492048214920152015204920162049204a21492049211520482191be80db00",
//电视 乐声(Panasonic)  4
"00e04700338235810f28202820281f281f291f29202a65291f286529652965286529652965281f2865291f2a65291f281f2966291f291f29202865282028652965281f286628652965288999823b8087280000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  5
"00e047003382328110286528662920282028652821286628202820281f29652866281f2865291f2a65296528652966291f2865281f291f291f291f291f282028662820286628652866298998823c8086290000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  6
"00a052003480cf80d23a303a303a303a2f3a303a303b303b2f3a313b303a80983a80973b80983b80973a80963a80983a80973a80973a80973c80973a80973a303a89e380d680d23a2f3a303a303a303a303a313b303b303b303b303a80993a80983a80983b80973a80973b809800",
//电视 乐声(Panasonic)  7
"00a052003480d080d23a303b80973a80963a80973b303b313a303b303b303a303b80973a80973b313a313b303b80973a80973a80973a80973a80973a80973b303b89e480d680d23a303a80973a80973b80983a303b303a303a303a303a2f3a80983a80983a303a303b313a809700",
//电视 乐声(Panasonic)  8
"00a052003480ca80d23b303b303b303a2f3b303a2f3a303a303a303a303a80973b80983a80973a80973a80973a80993a80973a80973c80973a80983a80973a2f3a89e280d780d23b303b2f3a313b303a303b303a303b303a303a2f3b80983a80983b80983a80973a80973b809700",
//电视 乐声(Panasonic)  9
"00a065003480d4682116204e201621162116211720172016211620162016201520162016201620162015201620161f16201620162016204e2116201621162116211721162016211622482115204920482049204920172017204920152049204920482148211521482091bb80db00",
//电视 乐声(Panasonic)  10
"00a065003480d4682016204f201720162016201621162016201621162017211620162116221620172116201621162017201621162116214f201620162016201620162116201620162048201520482049204a204920162016214920152049204820492049201720492091bd80db00",
//电视 乐声(Panasonic)  11
"00a065003480d4682015204f201622162017211620162016211620162016211620172116201720162216201721162016201621162016214e2116211621172016201620162116211720492015204820482049204a21162016204920162149204820482049201620492091be80db00",
//电视 乐声(Panasonic)  12
"00a052003480cf80d23a303a303a303a2f3a303a303b303b2f3a313a313a80983a80973a80973b80983a80973a80973b80983b80973a80963a80973b80973a303a89e380d780d23a303b303a2f3b303a303a303a2f3a303a303a303a80983a80983a80973a80973b80983a809700",
//电视 乐声(Panasonic)  13
"00e04700338234810f2820281f28202820282028202866281f286428652966286528662965291f2965281f2a65291f291f2965281f291f2a1f2965291f28652965291f296528652965288996823b8087290000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  14
"00e04700358233811028662766282028202865281f2867271f28202820286628672720286728202866286528682768281f2965291f292029202820281f291f29672720286528662967278996823c8087280000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  15
"00a065003480cf682015204e21162116211721162016211620162016201621162117204f201620162016201621162016201620162016214e2017201621162116211720172016211621492016204920482049204920172017204a20152049204920482148211521482191bc80db00",
//电视 乐声(Panasonic)  16
"00a052003480cf80d23b303a303a2f3b303a303a303a2f3a303a303a2f3a80983a80973b80973a80973b80983b80983a80963a80973b80973a80983a80973a303a89e180d680d23a303a303a303a2f3a303a2f3a303a303a313a303a80973c80973a80973b80973b80983a809800",
//电视 乐声(Panasonic)  17
"00a052003480cf80d33b303b303b303a303b313a2f3a303a303a2f3b303a80973c80973a80973a80973a80973b80983a80963a80973b80973a80973a80983a313b89e280d780d23a2f3a303a2f3a303a303a313a303a313a303b2f3a80993a80973a80973b80973a80973a809800",
//电视 乐声(Panasonic)  18
"00a065003480d4682015204e21162116201721162016211621162117201721162117204f201620162015201621162017201620162016224e2116211720172116211720162016201520492016204920482148214820152015204820152049204821492049201620492091bb80dc00",
//电视 乐声(Panasonic)  19
"00a065003480d4672016204e20152016211620172016201620162216201721162117204f201620162016201520162016201620162016204f2016221620172116211621172017201621482116204820492049204921152015204920162049204921492049201520482191ba80db00",
//电视 乐声(Panasonic)  20
"00e05b00338234810f28632963296329632864281e281e291f2963281f281e281f281f281e281f291e281f291e286329622863281f29632963286328632864291f291e281e281e281e281e281f291e28642963296328632863296328642885be823c811028000000000000000000",
//电视 乐声(Panasonic)  21
"00e02b00376f6676323d323e323d313e323e323d673d3277323d947a776878333d333d323d333d333d323d673e3277323e00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  22
"00e047003582338110281f29202820286528662867281f291f2967276529662a1f2a1f291f29672667261f291f2a6529652965281f281f281f296528662820281f281f296626662866288996823c8087280000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  23
"00e049003481158114281e286528642965281f281e281f291e281e2a6428642964291e281e291e291e2820281e28642963281f291e2820281f28642863281f281f2864286328642863288b4181208115296228000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  24
"00e049003481198115281f281f29632964281e281f291e281e2a1e281e29632863291e281e291e281e2865296328642964291e291e2820281f291f281e281f281e2963286529632865298b4281208115286128000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  25
"00a065003480d4682017204e20162116211720162016201620162016201520162016204f201721162016211621172017211621162117204f2016201620152016211620172116201720492016214920492049204821162116214920162049204821482048201520482091ba80db00",
//电视 乐声(Panasonic)  26
"00e049003481178115281e286429652864291e291e281f291e2820286429642963281e281f291f281e281f281e29632865281f291e291e2a1e28632864281f291f2863286428632964288b42811f8115296229000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  27
"00a065003480d3672016204e21162017211620162116211621172016201621162016204e201620152016211620162016201620162216204f211720162016201620162016201520162048201520492049204a214920162016204920152048214920492048201520482191ba80db00",
//电视 乐声(Panasonic)  28
"00e05b00338234811029632963286428632964281e281f281e2863291e281f291e291f291e281e291e291e281e2863286428632a1e28632863286428632963291f281e2820281e281e291e2a1e291e29632863286329632864286329632985bd823a810f28000000000000000000",
//电视 乐声(Panasonic)  29
"00e049003481188115281f2863296428642a1e281e291e281f291e286428632965281e281f281f291f281e281f28632964281e281f291e291e2a642864291e281e2965286429642963288b41811f8116296128000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  30
"00a065003480d4682016204e20162016201621162015201621162017211620172016224e211621172016201620162016201620152016204e211620172016201620162216201720162148211521482148214920492016201620492016204a214920492048201522482191bc80db00",
//电视 乐声(Panasonic)  31
"00a065003480d4682016204f20172016201620162216201620162116201721162016204e201621162117201620162016211621172016204e2016211620162016201520162116201620492016204920492149204920152015204822152248214821492049201520492091bd80db00",
//电视 乐声(Panasonic)  32
"00a065003480d4682016204f20172116201621162117201721162016211620162016204e201520162016201620162016201622162017214e2016201620162016201620152016201620492015204822482148214920152015204820152048214920492049201520482191ba80dc00",
//电视 乐声(Panasonic)  33
"00a052003380cd80d23a303a303a2f3a303a303a303a2f3a313a303a2f3a80993a80973a80973a80983a80983a80983a80963a80983b80973a80963a80973a2f3a89e280d680d23a303a2f3a303a303a303b2f3a313a303a2f3a313a80983a80973a80973a80973b80973b809700",
//电视 乐声(Panasonic)  34
"00a065003480d4672117204f20162016201520162016201620162016201622162017214e201620162016201620162015201621162017204e2016201620162016211620162016201620492015204821492049204820152115224821152049204820482148211621492091b980db00",
//电视 乐声(Panasonic)  35
"00a052003480cf80d23a313a303a2f3a303b303b303b2f3a313b303b303b80983a80973b80973b80983a80973a80973a80973b80973b80973a80973b80973b303b89e180d680d33b2f3a313a303a2f3a323a303b303b303a313b303b80973b80973b80983a80983a80983a809700",
//电视 乐声(Panasonic)  36
"00e02f00376e323e333d323d323e323d323e323e313e683e3277323e947776313d323e323d323d333e323d323d333d683d3276313e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  37
"00a065003480d0682016224e21172017211621162117201620162116211621172016204e201620162016221620172116211620172116204e2016201520162016201620162016201621482015214821492049204920162017204a20162049204920482148211521482091ba80db00",
//电视 乐声(Panasonic)  38
"00a052003480cc80d23a2f3b80973a80973b80983a303b303a303b303a303a2f3b80983a80983b303a303b303b80973a80973b80973a80983a80973b80983b313a89e480d680d23a303a80973b80983b80983a303a303a2f3a303a303a303a80983a80983a303a303a303a809700",
//电视 乐声(Panasonic)  39
"00a052003480d080d23a2f3a303a303a303a303a313a303a2f3a313b2f3a80983a80973a80973b80983a80973b80973a80973b80983a80973a80973c80973a313b89e280d580d23a2f3a303a303a2f3a303a313b303b2f3a313a313a80983a80973a80973b80973b80973a809700",
//电视 乐声(Panasonic)  40
"00a052003480cf80d23a303a303a303a303a303a303b2f3a313a303a303b80983a80973a80973a80983a80983a80973a80963a80973b80973a80963a80973a303a89e280d680d33a313a303a313b303b303b303b303a303b313a303b80973a80973a80973b80983a80973a809700",
//电视 乐声(Panasonic)  41
"005029001a406540691d171d404b1d404b1d404c1d181d171d181d181d181d181d404b1d404c1d181d181d181d404c1d404b1d404b1d404c1d404c1d404b1d181d4470406b40691d181d404b1d404b1d404b1d181d181d171d181d181d181d404b1d404c1d171d181d181d404b00",
//电视 乐声(Panasonic)  42
"00a052003480d080d23a303b2f3a313b303b303b313a303b2f3b303a303b80973a80973a80983b80973a80963a80983a80983a80983a80973a80983b80983a303b89e180d780d23a2f3b303a303b2f3b303a303a303a2f3a303a303a80973c80973a80973a80973a80983a809800",
//电视 乐声(Panasonic)  43
"00a065003480d4692116204e20162016201621162016201620162016201620152016214e211621172016201620162016211620162016204e2116201721162016211621162117201720492016204920492148214820152015204820152148214821482048201520492091bb80db00",
//电视 乐声(Panasonic)  44
"00e049003481188114281e281f28642964291e281e2820281e281f281f28642963281f281f281f291e2963296528642863281f281f281f291f281e291f281e291e2863296428632964288b41811f8114296228000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  45
"00a065003480d4692016204e20162016201620162015201620162016201620162016224e211720172116211621172016201620162116214f2016201620162016201620162015201621482115214821482149204920162017204920162049214920492048211522482191bc80db00",
//电视 乐声(Panasonic)  46
"00e05b0033823281102863286428632a632964281f281e291e2864291f291e281e291e291e291e291f281e281f286329632863281e28642864286329632963281e281f281e281f281e281e281f291e28642963296328642863296329632985be823b811029000000000000000000",
//电视 乐声(Panasonic)  47
"00e02b0037706777323e313d333e323d323d333d683d3276313e9478776877333e323d323d333d333d323d683f3277313d00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  48
"00e0470033823381102920281f281f291f2920291f2965281f286529662866286529662866282028652820286628202820286528212820282028662820286428662820286528662865298996823b8086280000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  49
"00e049003481188115291e286528642964291f281e281f281e291e2a6428642963291e2820281f281f281f281f29642963281e281f291f281e28642865281f281e2963286528642965288b4281208115286229000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  50
"00e04700338234810f28652866291f2920286528202866291f291f292028652866291f2965291f296629652a652965281f296528202820291f281f281f281f2a65291f296528652965288996823b8086290000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  51
"00a052003480d080d23a303a2f3b303a2f3a303a2f3a303a303a313a303a80973a80983a80973a80973b80983a80983a80973b80983b80983a80973b80973b303a89e280d580d23b303a2f3a303b2f3a313b2f3a313b303b2f3a313b80973a80963a80973a80973a80983a809700",
//电视 乐声(Panasonic)  52
"00a054003480cc80d33a313b303b303b303b303a303b303a2f3a303a303a303a80983a80983b80973a80973b80983a80983b303a80963a80973b80973a80973a80983a313b882580d680d33a303a303b2f3a313a303a313b303a303b303b303b2f3b80973b80983b80983a809700",
//电视 乐声(Panasonic)  53
"00e04700338232811029202820281f281f2920282028652820286629652965286629652965281f286529202a65291f281f286529202a1f291f2965281f28652966291f286529652866288997823b8086290000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  54
"00e047003382338110281f281f2920286628662864282028202866286528662820282028202865286628202820286528652866291f2920281f28652866291f291f291f286528662965298995823b80872a0000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  55
"00e049003481188115291f291e28642864281e291e281e2820281e282028642a64281e281f281f281f2963296428632964281f281f281e281f291f281e2a1e281e2963286529632865298b4181208115286229000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  56
"00a065003480d1682117204f21172016201620152016211720162016201620162016204e201520162116201721162017201622162017214e2016201620162016201620152016201620492015204821482049204a20152015204820152049204920482148211521482191bd80da00",
//电视 乐声(Panasonic)  57
"00e04700338234811028652966282028202864281f29662820282028202865296628202866281f286528652965296528202865291f291f291f28202820282028662820286528662965298996823b8087280000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  58
"00e049003481158115291e281e296428642a1e281e291e281e291e281e28652963281e291e2820281e2864296328652864281f291e2820281e281f281f281e281f2863286528642863288b4281208115286329000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  59
"0070230019411a40081410140f140f143214331433140f140f143214331432140f1410141014331432140f14101433143314321410141014101433143214101410141014321432143314444b411d4043140000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  60
"00e04700338235811028202820281f286529652866281f281f286529662965281f291f2a1f2965296528652a652965281f2965291f291f291f29202820281f29652920286528662965298997823a8086290000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  61
"00e04700338234811028662865281f2920296628202865291f2920292029652865291f296628202866286529662866282028652820282028202820281f281f29652820286628652864288996823b80872a0000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  62
"00e047003382348110291f291f291f296528662865291f291f286528662965291f2820282028662866281f282129662866286528202820282028662865292028202820296528652966298997823c8086280000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  63
"00e04700338230811028662865281f291f2966291f2865281f2a1f291f29652865291f2965291f296528662965296529202865291f291f291f28202820282028662820286528662866288997823b8087290000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  64
"00e05b00358232811028632963296328632862281e291e281f2963291e281e291e291e281e291f281e281f281e286329632a63291e29622863286329632963291e291e291d281f281e281f281e281f2963296328632862286428642a632985bf823c810f29000000000000000000",
//电视 乐声(Panasonic)  65
"00e05b003382328111281f281e291e281f291e2964281e291e291e2864281e281f281e281f281e291e291f291e281e2a1e281e281f281e281e281e281e2864281f2963296328632862281f291e2864281e2963286328622864281f29632985be823c811028000000000000000000",
//电视 乐声(Panasonic)  66
"00e049003481188115281e2963286429632820281f281f281e291e296329642963291e2820281f281f281e291e29642964291e281e2a1e291f29642963291e281f2864296429642964298b4381208115286229000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  67
"00a065003480d4672016214e201620162016201520162016201620162015201620162016201620162016201620152016201620162016204e201621162216201721162016211621162049211621492049204820482015201520492017204a204921492048201520482091bd80dc00",
//电视 乐声(Panasonic)  68
"00e04700338234811028202820291f281f281f281f2a65291f296528652965296528652865282028662820286528212820286628202820281f29662820286628652820286628662865288997823c8087280000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  69
"00e049003481128115281e291e2a1e2965281f281e281f291f281e2a1e281e2963281f281f291f281e29632865281f2863291e2820281e281f281f281f2963291e2865286429652864298b44811f8115286228000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  70
"00e049003481198115281e281f291f2863281e281f291e281f281f281f281e2963281f291e2820281f28642863281f2864291e291f291e281f281e281e2865291e2964286428642864288b43811f8115296228000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  71
"00a065003480d4692116214f20162015201620162016201620152016201620162016204e201620162116201520162116201720162016204e21162117201721162116211720172016214821162149204920492048211521152148211521482148214a2149201620492091bd80dc00",
//电视 乐声(Panasonic)  72
"007017001b36181e191f191e191e191f191e333b191e191e191f191e4a3d3b191f181e191f191f181e191f333b191e191e191e191e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  73
"00a065003480d4682016204e20162116201721162016201621162017211620162116224e211621172016201620162016201620152016204e2116201721162017201622162017211621482115204920482148204920172016214820152048204920492048211522482191bb80db00",
//电视 乐声(Panasonic)  74
"00e0470033823481102820291f291f291f281f28202865291f296528662865296528652965291f2965281f2966291f291f296529202820281f2965291f286528662920286528662865298997823b8087280000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  75
"00e04700338233811028672866281f2820286529202866281f2820282028662866281f2866281f2966286528662865291f29652920281f281f29202820282028652820286529652866288997823b8087290000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  76
"00e02b00376a6777323e323d323d323e6777323e323d323d323e947a766777323d323d333d323d6778333e323e313e333e00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  77
"00a052003480cf80d33b2f3a313b303a303b313a303c303a303a2f3b303a80983b80983a80973a80973a80973a80993a80973a80973c80973a80983a80973a2f3a89e380d680d23b303a303b313a303b303a303a2f3b303a2f3a303a80983b80973b80973a80973b80973b809800",
//电视 乐声(Panasonic)  78
"00a065003480d4682116204f20172116211620172116201621162117201721162016214e201620162016201520162016201620162015204f2016221620172116201621162116201621482116214920492049204821152116214920162049204820482049201621482091bc80db00",
//电视 乐声(Panasonic)  79
"00a065003480d1682117204f21172016201620162116211720162016211720162016204e201520162016201620162016201620162015204e2116211621172116201621162117201721482116214820482049204920162016204921162149204920482049201620492091bd80db00",
//电视 乐声(Panasonic)  80
"00a065003480d1692116204e20162016211621172016201621172016201620152016204e201620162016201620162016201520162116204f21172116201621162116211721162016214820152048204920492048211522152248211520492048204920492017204a2191be80db00",
//电视 乐声(Panasonic)  81
"00a065003480d4682016204f20162216201520162116201721162016211621162017214e201620162016201620162015201620162016204e2116201620162216201721162017201621482115214821492049204920162017204a21162149204920482248211521482191bc80db00",
//电视 乐声(Panasonic)  82
"00a065003480d4682116204f20172116211720172116201621162116211721162016214e201620162016201520162016201620162015204e2116221620172116201621162016201621482116204820492049204a21162115204920162049204821482049201520482091bb80dc00",
//电视 乐声(Panasonic)  83
"00a065003480d4682016224e21162117211621162117201620162015201621172016204e201620162016221620172016211620172116204e20162016211621172016201620162016214920152049204a2049214920162016204920152048214821482149201520492091bb80db00",
//电视 乐声(Panasonic)  84
"00a065003480d4672116214e20162016201620162016201621162016201620162016204e211620162116211621172116201621162116214f2016201520162016201620162015201620482015204821482048204920162016204920162049214920492048201520482191bb80db00",
//电视 乐声(Panasonic)  85
"00e05b0035823281112a1e2a1e286328622864281f291e291e2963281e291f281d281f28632863291f291e281e296328642863291e28632a6328642863291e28642a1e291e2a63281f281d281f2863281f29632963291e2963286328632a85be823c810f28000000000000000000",
//电视 乐声(Panasonic)  86
"00e04b003382148101271c2661281d271d261e281d28612761278102281d275a2659275a281d271d261e271d288608821b8102261d2861271e261d261e281d28612661278101281d275b265a285a281d261d261e271d280000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  87
"00e02f003770313d323e323d323d323f323d323d323f673e3277323e947a77323d313e323e313d323e323e313d323e673e3278333d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  88
"00a065003480d4682016204f20162116201520162116201721162016201622162017214e201620162016201620162015201620162016204e2116201620162216201721162116211721482115204820482049204920162016204a21162149204920482248211521482091ba80db00",
//电视 乐声(Panasonic)  89
"00e0430034106c176b166a172c172c172d176b162d162c176b182c172c162b162c162d172c17868a166b166c176b172c172d162c176b172c182c166b162b162c172c172c162b162c1600000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  90
"00e04300340c6b166b166c162b162d162c176b162c172c166b172c172d182c162d162c172d17868b176b166a166b172c172d182c166c162c162d166b172c162b182b162c172c172c1600000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  91
"00e04b003382148102261d271d2761271e26612661271e271e268101271d285a275b265a261d271e271e261d268608821c8101271d271d2662271d27612762261d271d278101271c265a285a275a271e271d271d271d270000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  92
"00e049003481198115281e286428652864291f291e281f281f281e286429632863291e291e291e281e281f291e2864291e2a64281f281f281e29632865281f2863291e286529632865298b4381208115286229000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  93
"00e049003481198115281f286329642864291e291f291e2820281e286428632864281e291f281e291e291e291e2964281e2864281f291e282028642963291e2865291e2a6428642963298b42811f8116296229000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  94
"00e049003481138115291f296429632964291e291e2a1e291f291e286529632865291e291e281e291e281f291e2864281e2a64281f281e281e28632864281e2965281e286428632864288b43811f8114286228000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  95
"00e049003481188114281f296329642963291e2820281e281e281f286429652864281e291e281e291e29642863281e2865281f281e291e2a1e291f291e2865281f2963296428632964288b42811f8115286229000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  96
"00e049003481188115281e281e281f2964291e281e281e291e2820281f281f2863291e291f291e2820286429632964291e281f291f281e2a1e281e291e281e29642964296328652963288b43811f8115286229000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  97
"00e05b003382348111281e2a1e286328622864291f291e281e2963281e2820281e281f29632963281e281f281e286329632963291e29632963286329632a1e2863281e281e2864281f291e281e2963281e28632863291e2863286328632885bf823b811028000000000000000000",
//电视 乐声(Panasonic)  98
"00e05b003582338110291e28622862286428632a1e281e2a1e2863281d281e281f291e2864291e291e291e291e296228632863291e29642863296228632863291e281e2a1e281e281f281e281f281d28632963296329632864286329642885be823b810f28000000000000000000",
//电视 乐声(Panasonic)  99
"00e02b00376a6776323e323d323d323e323d323d673d3377323d9477776777323e323d323e323e313e323e673d3176333d00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  100
"00e047003382348111291f281f281f2a1f291f291f29652920286629652965296629652965281f2966291f2965291f29202866291f291f291f28652820286628652820286629652965288998823c8087280000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  101
"00e04b003382128102261e2761281d271d271e271d271d271d278102281d275a275a265a281d281d271d261e278608821a8101281d2762261d261d271e271e261d261d268101271e265a275a275a271d271e261d271d270000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  102
"00e04b003382118102271d2762261d261e281d281d261d261e278101271d275b265b265a271e271e261c261d288607821a8101271e2660261d281d271e261d261d281d278102271d265b275a275a271d271e261d261d270000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  103
"00e02b00376f323e323e313e323e313d313e6777323d6876673e944476323d313d333e323d313d333e6777323e6778673e00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  104
"00e049003481188115291e286428642863291e2820281e281f281f286429652864291e291e291f291e28652963281f2964291e281f291f281e291e281e296328202864296329642863298b42811f8115296228000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  105
"00e02d0034814a81d21281d21281d21281d11281d11381341181d11281d11381d2128133128c471281331181d01281d11281d11281d11281d11281331281d21281d11281d11281331200000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  106
"00e04b0033821481012761261d271d271d28612761281d271d278101261e265a275b285a271d261e271d281d278608821b81022661261d271e271e27602661281d271e268102271d285a275a275b261d271e271e271d260000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  107
"00e04b00338212810127612662271d271d27612761271e271d278101281d275b2659265a281d281d261d261e288607821b810127622762261d261e28612861271d261e278101271d275a275b265a261d271e271e261c260000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  108
"00e04b003382148101271d2762271d271d271d271e271d271d278101261d265a275b275b261e271d271d271d278608821b8102261d2761271e271e261d261d271e271d268101271d285a275a285a271d271d271e261d260000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  109
"00e02f003770313d323e323d323e323e323d323e323e673d3177323d947676333d323e323d313d333e323d313d333e673d3276313d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  110
"00e02b00376a6777323d333d323d323e323d323d683d3278333e9479786777323e313d333e323d313d333e673e3176323d00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  111
"00e0490034811881162a1e296329642963291e2820281e281f281f286428632864281e2a1e281e291e281e291e2964281e2864291f291e282028642963291e2865281f296329632863298b41811f8115296329000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  112
"00e0490034811981152820286429642964291e281e291f281e291e296428642863291e2820281f281f281e281f2963291e2865281f291e281e29642964291f2863281f286429642964298b42811f8115286229000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  113
"00e05b00338234810f281e281e296328632862281e281e281f2963291e281e291e291e28642863291e291e291e2a6328642863291e2964286329632864281e2963281e281f2862281e291e281f2963291e28632963281f2863296328632885bd823b811029000000000000000000",
//电视 乐声(Panasonic)  114
"00a0650034116d182c182c182c182c182c186d186e182d186e182c182c182c186e182c198a6c196c182e182f182e182e186b182f192e196c182e186c196c186c192e196c188997186e182b182b182b182b192b196d196d192b186d182c182c182d186e182c188a6c186c182e1800",
//电视 乐声(Panasonic)  115
"00a0650034116e182c182c182c182b182b186e186f182c186e182c182c182c186e192b198a6d196c182e192e182e182d186c192f182f186c182f186c186c196c182e186c188998186e192b182b182c182c182d186e186e182c186e182c182c182c196d182b188a6e186c182e1900",
//电视 乐声(Panasonic)  116
"00a0650034116d182b182b192b192b192b196d196d182b186f192d192c182c186e182c188a6d186b182f192e192e182e186b182e192f186b182f186c196c186d192d196c188998186e182c192c192c192b182b176d176e182c186e182c182c182c186e192c198a6c186c192e1800",
//电视 乐声(Panasonic)  117
"00a06500340f6e182c192c192c192b182b186d176e182c186e182c182c182c186e192c198a6e186c182e182e192e182e186b172e192f186d182e186c196c186c192e186c188999186e192b182b182b182b182b196e186e182c186e182c182c192c196d182b178a6d186b182f1900",
//电视 乐声(Panasonic)  118
"00a06500340f6f192c192b182b182b182b186d196d192b196d182b182b182c186f182c188a6d186c182e182e182e172d186d182d182e186c192e186c186c196c182e196b198997196d192b192b182b182b182c186f186e182c186e182c182c182c196e192b188a6c186d182e1800",
//电视 乐声(Panasonic)  119
"00a0650034116d182c182c182d182c182c186e186e182c196d192b182b182b186e182d188a6e186c182d182f192e192e186c192d182f196b172e186d186b186d192d196c188999186f182c182c182b192b192b196d196d182b186e192d192d192c186d182b188a6d196c182e1800",
//电视 乐声(Panasonic)  120
"00e03700341e5c2580da2580d92580da255c265b25876c245b2580da2580d92580d9265b245b25876d255b2580d92680d92680d9255a255b26876c255b2680da2580da2580d9255c255c250000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  121
"00e033003380932050202c2050202b214f202b202b2150202c202c202c202c85e3809b204f212c2050202c2050202b212b204f212c202c202c202c000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  122
"00e03300348093204f202b2150202c2050202c202b2150202b212c202b202b85e3809a204f212b204f212b204f212c202b204f212b202b202c202c000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  123
"00e033003480942050202c2050202b214f202b212c2050202c202c202c202c85e4809a2050202c2151202c2050202c202d2150202c212c202c202b000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  124
"00e033003480932050202c2050202c204f202c202c2050202c202c212c202b85e3809a204f202b2050202c2050202c202b224f202b202c202b202b000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  125
"00e033003480932050202c204f202b204f212b202b2050202c202c202c202c85e3809a2050202c2150202c2050202c202c2050202c202d202c202b000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  126
"00e033003480932050202c204f202b204f202b202c2050202c202c202d202c85e3809a2050202c2050202c204f202b212c2050202c202d212c202c000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  127
"00e03300348093204f202b2050202c204f212c202c2050202c202c212b202b85e3809a2050202c2050202c2050202c202c2050202c202d202c202c000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  128
"00e0330034808f204f202c214f202b214f202b202c204f202c212b202b202c85e4809a2050202c2050202c2050202c202c2050202c202c212c202b000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  129
"00e03300348090204f202c214f202b2150202c212c204f202c212b202b202c85e2809a2050202d2050202d2150202c202c2050202d202c202d212c000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  130
"00e03700341e5b2580da2580d92580d9255b255b26876d255b2580d92580d92680da255b255b25876d255b2580d92580d92680da255c255b25876d265a2580d92680da2680d9255b255c250000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  131
"00e0330034808c204f202b1f4f212b204f212c202b204f202b212b212c202c85e3809a2050202c2050202c204f202c202c2050202c202c212c202b000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  132
"00e033003480932050202c2051212c204f202c202c2050202c202b212c202b85e4809a204f202b2050202c2050202c202b224f202b202c202b202b000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  133
"00e03300348093204f202b204f202b2050202c202b214f202b202c202c202c85e3809a2050202c204f202c204f202c202c2050202c202c212c202b000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  134
"00e03300348093214f202b2150202c2050202c202b224f202b202b202b202b85e4809a2050202c204f202c204f202b202c2050202c202c212c202b000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  135
"00e03300348093204f202b204f212b204f212c202b204f202b202b202c202d85e480992050202c204f202b204f212b202b2050202c202c202c212c000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  136
"00e033003480932050202c204f212c204f202c202c2050202c202b212c202b85e3809b2050202c2050202c2050202b212c2050202c202c202c202c000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  137
"00e0330034808e204f202b214f202b2050202b202c214f202b212c202c202c85e3809a2050202c204f202b214f202b202c2050202c202c202c202c000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  138
"00e0330033808f2050202c2050202b214f202b202b2150202c202c202c202c85e3809b204f212c204f202c2150202b212c204f212c202b202b212c000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  139
"00e04100341e61261d256126612561251e251d261d251d261e2583962662251e256025612561251d261d251e251e251d2583972561251d266125612561251c251e251e261d251d260000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  140
"00e02f00376c313d333e323d323d333d323d323e333d673d3276313d947976323d313d333e323d323d333d323d323d683f3377323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  141
"00e049003381188115281e2963286529632820281e2820281f281f286329642864291e281e291e281f29642964291e2864281f291f281e291e281e291e2964281f2864296328652963288b43811f8115286229000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  142
"00e05b003382328110291e281e296328642863291e281f291e2963291f281e281f281e28632963291f281e281f286328642964281e2963286428632964281e2963281e281e2863291e281f291e2963291f28632863291f2963296328642885be823c811028000000000000000000",
//电视 乐声(Panasonic)  143
"00e05b003382348110291e291e2a6329642864281e281f291e2864281e291e2a1e291e2a632864281e281f291e286428632a63291f2863286328642863291e2863281e281f2862281f291e281f2963291e2a632964281f2863286428642a85be823b811128000000000000000000",
//电视 乐声(Panasonic)  144
"00e0490034811881152a1e296428642964281f281f281e291e291e2863286529632820281f281f281e28642865281f2963291e281f281e281e2820281f2964291e2864286429642864288b42811f8115296228000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  145
"00e049003481188115281e296428632864281f281f281e291e2a1e296328642963281f281f281f281e291e291e2863281f2864291e291f291e2865296328202864291e286328642863288b4481208114286228000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  146
"00e02b0037706776323d323e323d313d323e323d673e3177333d947a766777323d323e323e323d323e323e673d3277313d00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  147
"00e02f00376f323e313d333e323d323d333e323d323d683e3277323e947776323d323e323d313d333e323d323d333d683d3276313e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  148
"00e0470033822f8110281f2920281f296529652865291f2a1f29652965286529202820291f296528652920282029652865296528202820282028652965282028202820286428652866288996823c8086290000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  149
"00e0470033823481102820282128202820282028202865292028662866286528662965296529202866291f2965291f281f29662a1f291f291f2965281f29652a65291f286529652866288996823b8086290000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  150
"00e047003382348111291f281f28652920291f291f281f2920286529652965296629652965281f28652a1f291f291f2966281f291f291f292028652866296529202865286629652965288998823b8087280000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  151
"00e0470033823281102920281f281f281f296528202965281f28652966286628652820296628202866286528662965291f2866281f281f292028202820281f28662820286628652866288996823b8085280000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  152
"00e04b003382128101261e271d2761271d271d2761271d271e268101271d275b265b275a271d271d271e261d278608821b8101271e261d2661281d281d2661271d281d288101261e275a275b275b261d271d281d271e260000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  153
"00e04b003382138101281e2761261d261d271e271e261c261d288101271d265b275a275a271e261d261d271e278609821a8102271d2761271d271d271d271d271d271e268101271e275b265a285a281d271e271d271d270000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  154
"00e0430034106b176b166b162d162c172c176c172c162c166b172c172c162c162b162c172c17868b186b176b166b162d172c172c166a162c172c176b182c162d162d162c172d182c1700000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  155
"00e0430034106b176b166b162c172c172c166b172c162d166b172c172c162c162b162d172c17868a176b176b176c162c172c172d186b162c162c166c172c162b162b162d172c172c1600000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  156
"00e05b003382358110281e281e286228642863291e281e2a1e2863281e281e281f291e28642863291e281e2a1e286328622863281f2963296328632863291f2864281e281e2963281e2a1e281e2864281e28622864281e2963286328622885be823c811028000000000000000000",
//电视 乐声(Panasonic)  157
"00a051003480cd80d23a303a80973b80983a313c80973a303a303a80963a303b80973a303b303a80973a303a303a80973b313b80983a80963a303a80973a303b80973a80973a882680d580d23a303a80973c80973a303b80973a303a303a80973b303b80973a303b303a80973a00",
//电视 乐声(Panasonic)  158
"00e05b003382348110281e281e296328632864281e291e281f2963291e281e291e291e28642864281e281e291f296329632864281e286428632a632864281e2862281f291e28642a1e291e291f2863281e28632963291e2863296228632885be823a810f28000000000000000000",
//电视 乐声(Panasonic)  159
"00e0470033823481112a65281f291f29202a65291f28652a1f291f296529652965291f2965281f2966291f291f291f29652820281f291f292028652866286529202865286628652965298998823c8086280000000000000000000000000000000000000000000000000000000000",
//电视 乐声(Panasonic)  160
"0070230019411a40081432140f140f140f1433140f1432140f141014331432143214101433140f1432140f14101410143314101410140f140f1433143314321410143314331432143314444c411d4043140000000000000000000000000000000000000000000000000000000000",
};
}