package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class HPC extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "HPC";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 惠浦(HPC) 1
"00e0470033823481102920281f291f291f291f29652820281f28652965286528652966291f286529652820282028202865281f292029662820286628652866291f29652866281f2865298995823a8086280000000000000000000000000000000000000000000000000000000000",
//电视 惠浦(HPC) 2
"00e0470033823281102920281f281f281f291f2a65291f291f28652965286628662865292028662866281f2820282028662820282028652820286529652965292028652965291f2865298996823b8086280000000000000000000000000000000000000000000000000000000000",
//电视 惠浦(HPC) 3
"00e04700338234810f291f291f2920286528202820281f2920286528652866291f2965296528652966291f281f281f28652920291f291f291f286529652866281f2865296528662865288996823b8086290000000000000000000000000000000000000000000000000000000000",
//电视 惠浦(HPC) 4
"00e04700338234810f2920281f291f29202820281f28652820281f292028202820281f28202865281f2920286528202866281f291f291f2920286528202865291f2965286529652866288995823b80862a0000000000000000000000000000000000000000000000000000000000",
};
}