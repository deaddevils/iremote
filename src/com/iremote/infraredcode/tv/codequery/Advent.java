package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Advent extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Advent";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ª˘∂ΩΩµ¡Ÿ(Advent) 1
"00a06500340d6d182b192b192b192b192b176d176d182b196d192b192b182c186d182b188a69186d182e182d182f182e186c192e182e186c192e186c186c186c192d186c198995196d182c182c182c192c192c196d186d182c186e182c192c192c196d182c188a6a186c182f1800",
};
}