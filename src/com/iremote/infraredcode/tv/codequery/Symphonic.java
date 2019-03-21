package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Symphonic extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Symphonic";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ΩªœÏ¿÷(Symphonic) 1
"00a052003480cf80d23a80973a2f3a303a80973b313a80983a313b303b2f3a313b303b80973a2f3b80973a80973b303a80973a303a80973b80973a80963a80973a80973a303a882480d680d23a80963a303a303a80963a303a80973a303b303a313b303b2f3a80983a303a809700",
};
}