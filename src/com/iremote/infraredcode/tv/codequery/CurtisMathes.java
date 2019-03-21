package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class CurtisMathes extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "CurtisMathes";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 柯蒂斯马特斯(Curtis Mathes) 1
"00a063003481138115286329622862281e291e291f291e281e296229622862291e281e291e2a1e291e291e2963291e291e291e291e291f291e2863291e286229622a62286228622862288ac7811e8114296228622862281f281e281f281f281f286228622863291e2820281e2800",
//电视 柯蒂斯马特斯(Curtis Mathes) 2
"00a063003481188115286228632962281e291e281e281f291e286329622962291e291f281e291f281e291e2862291e291f281e2a1e281e291e2862281f296328622862286229622862288ac781208114296228632862291e2820281e281f281f286228632962281e291e291e2900",
};
}