package com.iremote.infraredcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseACRemoteControllerCodeFile extends ParseTVRemoteControllerCodeFile 
{
	@Override
	protected String parseCode(String str) 
	{
		Pattern pattern = Pattern.compile("\\([^ \\)]* [^\\)]*\\)" , Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		if ( !matcher.find())
			return null ;
		String s = matcher.group();
		s = s.replace("(", "").replace(")" , "");
		return s.substring(s.lastIndexOf(" ")).trim();
	}

	@Override
	protected String parseProductorName(String str) {
		Pattern pattern = Pattern.compile("\\([^ \\)]* [^\\)]*\\)" , Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		if ( !matcher.find())
			return null ;
		String s = matcher.group();
		s = s.replace("(", "").replace(")" , "");
		return s.substring(0 , s.lastIndexOf(" ")).trim();
	}
	
	@Override
	protected String getCodeid(int index)
	{
		return String.format("AC_%d",index);
	}

}
