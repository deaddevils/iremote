package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class JVC extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "JVC";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 日本胜利(JVC) 1
"00e04700338234810f281f291f28202820281f281f2965291f28662865296529652865296528202866282028652820281f29652920281f28202866291f29652965281f296529662866288995823b8086290000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 2
"00e047003582328110281f2a1f291f291f291f292029662a1f29652965286428652865296529202866291f2965291f292029662a1f291f291f2965281f286529662820286528642865298994823b80872a0000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 3
"00e045003382108101255e255e251c251c251c251c261d251d265d265d251c255d251c251c251c251c25855a255d255d261c251d251c241d251c251c255d255e251c265d251c251c261d251d26000000000000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 4
"00e04700338232810f291f291f291f291f292028202865291f296528652865296529652965281f29662a1f2965291f281f2865291f292028202965282028642865281f296529652865298994823b8086290000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 5
"00e01b00341c582880d92880d92880d82857295828872f28582980d82880d92880d92a572857280000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 6
"00a051003480cf80d33a303a80963a80983b303a80983a313a303a80963a303a80973a303a303a80973b303b2f3a80983a303b80973a80963a303a80963a313a80973b80973a882480d680d33b2f3a80983b80973b303b80983a303a2f3a80973a303a80963a313a303a80983c00",
//电视 日本胜利(JVC) 7
"00e03700341080d61680d61681d11681d11781d01781331781331681d1168133168133168133168133168133128c431880d61880d61681d01781d01681d01681331681331681d0168133168133168133178133188133120000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 8
"00e04900348115810f28622962281e2962281e291e291e291f29622963291e2a62281e2a1e281e291e291e2962291e2862291e291e291e281e2963291e2963291e2962296229632862288ac8811f8111291e29000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 9
"00e03700331080d71680d71881d01781d01681d11681331681331781d1168133178134178134178134178134128c431680d71680d71881d01781d01681d11681341681331681d1168133168133168133168134168134110000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 10
"00e0450035820e8101255e255e251c251c251c255d251c251c255c265d265d251c251c251c251c255d25855a255d255e251c261d251d265d251c251d265d255d265d251c251c261d251d265d26000000000000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 11
"00e03700340f80d61780d81681d01681d01781d01781331781331781d0178133178133178133178133178133118c421680d71780d61781d01681d11681d01681331681331681d0168133168133168133168133168133120000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 12
"00e01b003421572980d82980d82980d92857285829872e29572880d82880d82880d828572858280000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 13
"00e047003382348110281f2a1f291f291f291f29202866291f29652965286529662866286528202866281f2965291f291f2966291f2a1f291f2965281f286529652820286628652866288994823a8086280000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 14
"00e047003382328110281f291f29202965281f281f286529202866286628652820286529652965281f281f2965282028662820281f281f2920286628202865281f2866286529652965288994823b8086280000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 15
"00e045003382108102255d255d261c251d251c241d251c251c255d255d255d251c255d251d251c251c25855a255d255d261d251d261c251d251c251c255d255d255d251c255d251d251c251c25000000000000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 16
"00e045003382108101265d265d251c251c251c251c251c261d265d255e255d251d255e241c251c251d26855a255d255d251c251c251c251c261d241c255e255d245e261c255d251c251c251c26000000000000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 17
"00e045003382108101255e255d251c251c251c251c251c251c265d255d255d251c255d251c241c251c25855a255d255d261c251d251c251c251c251c255d255d255e251c265d241d251c251c25000000000000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 18
"00e0450033820f8101255d255e251c261d251d261c251d251c245e265e255d251c255d261d251d261c25855a255d245d251e251c251c251c251c251c265d255d265d251c255d251c251c251c25000000000000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 19
"00e0450033820f8101255d255d251d251c251c251c251c251c255d255d265d261d255e251c251c251c25855a255e245d251e251c251c251c251c251c265d255d265d251c255d251c251c251c26000000000000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 20
"00e0450033820f8101255d255d251d261c251c251c251c251c265d255d265d251c255d251c251c251c26855a245d255e251c241d251c251c251c251c255d255d255e251c255d251c251c251c25000000000000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 21
"00e0450033820f8101255e255d251c251c251c251c251d261c265d255d255d251d265d251c251d261c26855a255d255e251c241c251c251c251c261d255e255d255d261c265d251c251c261d25000000000000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 22
"00e045003382118101255d255e251d261c251d251c251c251c255d265d265d251c255d251c251c251c25855b255d255d251d261c251d251c251c251c255d255d265d251c255d251c251c251c25000000000000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 23
"00e045003382108101255d255e251c261d251d261c251d251c255d265d255d251c255d261c251e251c25855b265d265d251c251c251c261d251d261c255e255e255d251c245e261c251c261d25000000000000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 24
"00e0450033820b8101255e245d251d251c251c251c251c251c265d255d265d251c255d251c251c251c26855c245d255d261c251d251c251c251c251c255d255d265d251c255d251c251c251c25000000000000000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 25
"00e05b003382348110291f291e2863296329632863291e281e2963281e291f281e281f28632963281e281f281e281f28632863281e2863296329632864281e2864281e291e2862281f291e281f2963291e29622864291e2963286428632985bd823b810f29000000000000000000",
//电视 日本胜利(JVC) 26
"00e049003481198114281e291e291f2963281f281f291f281e281f281e281f2963291e281f291e281e28642964291e2964281e281f281f281f281e281f2964291e2864286428632964288b40811f8114286129000000000000000000000000000000000000000000000000000000",
//电视 日本胜利(JVC) 27
"00e047003382348110281f2a1f291f291f291f281f29662a1f29652965286529662865296529202866291f2965291f281f2966291f2a1f291f2965281f286529662820286628652866298994823a8086290000000000000000000000000000000000000000000000000000000000",
};
}