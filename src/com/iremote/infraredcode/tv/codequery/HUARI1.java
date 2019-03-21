package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class HUARI1 extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "HUARI1";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 瓦里(HUARI) 1
"00e04700338234811028202820281f28202866291f2965291f2965286428662865291f2965281f2865296528652965291f2865291f2920292028202820281f2864281f286628652a65288993823c8087280000000000000000000000000000000000000000000000000000000000",
//电视 瓦里(HUARI) 2
"00e04700338234810f292028202820281f2965291f2965281f2864286528652965281f2865291f2965286529652965281f29642820281f292028202820281f28652820286529652965288993823b8086290000000000000000000000000000000000000000000000000000000000",
//电视 瓦里(HUARI) 3
"00e049003481188115291e296329642864291f281e291e281e291e286428642863291e291f291e281f291e281e2964291f28642a1e291e291e28632864281e2964291e286428642863298b3e811f8114296328000000000000000000000000000000000000000000000000000000",
//电视 瓦里(HUARI) 4
"00e049003481188114281e296328652864281e291e281f291e281e296429642963281f291e2820281e28642963281f2964281e281f291f281e291f281e2963291f2964286429632963288b3e811f8115296128000000000000000000000000000000000000000000000000000000",
//电视 瓦里(HUARI) 5
"00e049003481138114291f296428642964291e2a1e291e291e281e296428642964291e281e291e291e281e2a1e2863281e2864291f281f281f28642964291e2864281e286428642863288b3e811f8114296228000000000000000000000000000000000000000000000000000000",
};
}