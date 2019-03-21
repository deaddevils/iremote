package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class SoundVision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "SoundVision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 声音与视觉(Sound&Vision) 1
"00a051003480cf80d23a2f3b80973a80973a313a80973b303b303b80973a303b80973a303a303a80973a303a2f3a80973a303a80973b80973b303a80983b303a80973a80963a882380d780d33c2f3980973a80973a303a80973a303a303a80963a303a80973a2f3a303b80973a00",
};
}