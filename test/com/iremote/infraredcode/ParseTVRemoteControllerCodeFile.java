package com.iremote.infraredcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.iremote.infraredcode.vo.CodeLiberayVo;
import com.iremote.infraredcode.vo.RemoteControllerCode;

public class ParseTVRemoteControllerCodeFile {

	public static void main(String arg[])
	{
		CodeLiberayVo vo = new CodeLiberayVo();
		
		ParseTVRemoteControllerCodeFile pr = new ParseTVRemoteControllerCodeFile();
		pr.processRemoteControllerCode(vo , ParseInfraredcodeFile.basedir + "remote_tv_info.txt");
	}
	
	public void processRemoteControllerCode(CodeLiberayVo vo , String file)
	{
		String str = ParseInfraredcodeFile.readfile(file);
		
		Pattern pattern = Pattern.compile("\\{[^\\{\\}]*\\}[ ,]*/\\*[^\\r\\n]*" , Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		
		Map<String , List<RemoteControllerCode>> map = new HashMap<String , List<RemoteControllerCode>>(); ;
		
		while ( matcher.find() )
		{
			String s = matcher.group();
			//System.out.println(s);
			try
			{
				String pn = parseProductorName(s);
				String code = parseCode(s);
				int index = parseIndex(s);
				
				insert(map , pn , code , index);
			}
			catch(Throwable t)
			{
				t.printStackTrace();
				System.out.println(s);
				System.exit(0);
			}
		}
		
		Map<String , RemoteControllerCode[]> productorRemoteCode = new HashMap<String , RemoteControllerCode[]>();
		
		for ( String key : map.keySet())
		{
			List<RemoteControllerCode> lst = map.get(key);
			
			productorRemoteCode.put(key, lst.toArray(new RemoteControllerCode[0]));
		}
		
		vo.setProductorRemoteCode(productorRemoteCode);
	}

	private void insert(Map<String , List<RemoteControllerCode>> map , String productorname , String remotecontrollercode , int index)
	{
		if ( productorname == null )
			return ;
		productorname = productorname.toLowerCase();
		productorname = productorname.replaceAll(" ", "");
		RemoteControllerCode rcc = new RemoteControllerCode(remotecontrollercode , getCodeid(index));
		
		List<RemoteControllerCode> lst = map.get(productorname);
		
		if ( lst == null )
		{
			lst = new ArrayList<RemoteControllerCode>();
			map.put(productorname, lst);
		}
		lst.add(rcc);
	}
	
	protected String getCodeid(int index)
	{
		return String.format("TV_%d",index);
	}
	
	protected String parseProductorName(String str)
	{
		Pattern pattern = Pattern.compile("\\([ a-zA-Z]*\\)" , Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		if ( !matcher.find())
			return null ;
		String s = matcher.group();
		s = s.replace("(", "").replace(")" , "").replaceAll(" ", "");
		return s;
	}
	
	protected String parseCode(String str)
	{
		Pattern pattern = Pattern.compile("\\-[\\-a-zA-Z0-9]*" , Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		if ( !matcher.find())
			return null ;
		String s = matcher.group();
		s = s.replaceFirst("-", "").replace(")" , "").replaceAll(" ", "");
		return s;
	}
	
	protected int parseIndex(String str)
	{
		Pattern pattern = Pattern.compile(",[ \\r\\n]*[0-9]{1,}" , Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		if ( !matcher.find())
			return 0 ;
		String s = matcher.group();
		//System.out.println(s);
		s = s.replaceAll("\\r", "").replaceAll("\\n" , "").replaceAll(" ", "").replaceAll(",", "");
		//System.out.println(s);
		return Integer.valueOf(s) ;
	}
}
