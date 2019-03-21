package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class AcceleVision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "AcceleVision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” AcceleVision(AcceleVision) 1
"00a065003480cf682116204e20162015201620162016201620162016221620172116204e201620162016201620152016201620162016204d2016201621162117201620162016201620482015204821492049204920162016214920152049204a204820492016204a2091b880db00",
};
}