package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class AudioDynamics extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "AudioDynamics";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 音频动力学 (Audio Dynamics) 1
"00e04700338234811028652820281f29652965281f281f291f2a1f2965286628202820286528662965291f291f291f2865291f292029202820286628652866291f2965296528662865288994823b8086280000000000000000000000000000000000000000000000000000000000",
};
}