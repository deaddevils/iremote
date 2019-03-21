package com.iremote.infraredcode.stb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileAnalyzer {

	private static Log log = LogFactory.getLog(FileAnalyzer.class);

	private Set<String> nameset = new HashSet<String>();
	private Set<String> lowercasenameset = new HashSet<String>();
	
	private String read(String filename)
	{
		File f = new File(filename);
		if ( !f.exists())
			return "";
		try
		{
			FileInputStream in = new FileInputStream(f);
			byte[] content = new byte[in.available()];
			in.read(content);
			in.close();
			return new String(content , "GBK");
		}
		catch(Throwable t)
		{
		}
		return null ;
	}
	
	private String[] splitProductor(String str)
	{
		Pattern pattern = Pattern.compile("\\{[^\\}]+\\}");
		Matcher matcher = pattern.matcher(str);
		
		List<String> lst = new ArrayList<String>(1000);
		
		while(matcher.find())
		{      
			String s = matcher.group();
			//System.out.println(s);
			lst.add(s);
		}
				
		return lst.toArray(new String[0]);
	}
	
	private String getProductorName(String str)
	{
		Pattern pattern = Pattern.compile("\\([^\\)]+\\)");
		Matcher matcher = pattern.matcher(str);
		
		if ( !matcher.find())
			return "";
		String s = matcher.group();
		
		if ( s == null || s.length() == 0 )
			return "";
		s = s.replaceAll("[^0-9a-zA-Z]","");
		if ( s == null || s.length() == 0 )
			return "";
		return s.substring(0,1).toUpperCase() + s.substring(1);
	}
	
	private String getUniqueProductorName(String name)
	{
		String n = name ;
		for ( int i = 1 ; lowercasenameset.contains(n.toLowerCase()) ; )
		{
			n = name + String.valueOf(i++);
		}
		lowercasenameset.add(n.toLowerCase());
		nameset.add(n);
		return n;
	}
	
	private String generateFileContent(String name , String content)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("package com.iremote.infraredcode.stb.codequery;\n\n");
		sb.append("import com.iremote.infraredcode.tv.codequery.CodeQueryBase;\n\n");	
		sb.append("public class ").append(name).append(" extends CodeQueryBase {\n\n");
		sb.append("		@Override\n");
		sb.append("		public String getProductor() {\n");
		sb.append("			return \"").append(name).append("\";\n");
		sb.append("		}\n");

		sb.append("		@Override\n");
		sb.append("		public String[] getQueryCodeLiberay() {\n");
		sb.append("			return querycode;\n");
		sb.append("		}\n");

		sb.append("		private static String[] querycode = new String[]\n");
		sb.append(content);
		sb.append(";\n");
		sb.append("}");
		return sb.toString();
	}
	
	private void saveFile(String path , String name , String content)
	{
//		if ( "AUX".equalsIgnoreCase(name))
//			System.out.println(name);
		File f = new File(path + "\\" + name + ".java");
		String fc = generateFileContent(name , content);
		try
		{
			FileOutputStream out = new FileOutputStream(f);
			out.write(fc.getBytes());
			out.close();
		}
		catch(Throwable t)
		{
			log.error(t.getMessage() , t);
		}
	}
	
	private void saveListFile(String path , String name )
	{
		File f = new File(path + "\\" + name + ".java");
		StringBuffer sb = new StringBuffer();
		for ( String str : nameset)
			sb.append("registTvCodeQuery(new com.iremote.infraredcode.stb.codequery."+ str +"());\n");
		try
		{
			FileOutputStream out = new FileOutputStream(f);
			out.write(sb.toString().getBytes());
			out.close();
		}
		catch(Throwable t)
		{
		}
	}
	
	public static void main(String arg[])
	{
		FileAnalyzer fa = new FileAnalyzer();
		String str = fa.read("E:\\temp\\1.txt");
		String[] sa = fa.splitProductor(str);
		for ( String s : sa )
		{
			String n = fa.getUniqueProductorName(fa.getProductorName(s));
			if ( n != null && n.length() > 0 )
			{
				fa.saveFile("E:\\temp\\file", n, s);
			}
			else 
				System.out.println(s);
		}
		fa.saveListFile("E:\\temp", "list");
		System.out.println(sa.length);
	}
}
