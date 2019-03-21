package com.iremote.infraredcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.file.SerializeHelper;
import com.iremote.infraredcode.tv.TvCodeLiberay;
import com.iremote.infraredcode.vo.CodeLiberayVo;
@SuppressWarnings("unused")
public class ParseInfraredcodeFile {
	
	private static Log log = LogFactory.getLog(ParseInfraredcodeFile.class);
	
	public static final String basedir = "E:\\temp\\infrared\\";
	public static final String STORE_PATH = "E:\\temp\\infrared\\";
	public static final String CODE_FILE_NAME = "TV_stb_DVD_arc_data_table_V8.8-e.c";
	private static FileOutputStream fosql ;

	public static void main(String arg[]) throws IOException
	{
		if ( test() )
			return ;
		fosql = new FileOutputStream(basedir + "infraredliberay.sql");
		
		//matchDuplicate("�¿�˹ S101060137B-0207(AUX S101060137B-0207)@18");
		//prcessProductorModel("STB" , basedir +"remote_stb_2_info.txt");
		//prcessACProductorModel("AC" ,  basedir +"g_remote_arc_2_info.txt");
		
		System.out.println("delete \"};\" remote_IPTV_table followed remote_IPTV_table");
		System.out.println("delete /* ;wjs;2013-11-14 17:06     */");
		System.out.println("delete //;wjs,2018/4/16 16:58");
		splitFile();
		
		//STB
		CodeLiberayVo stbvo = createCodeLiberay("STB" , "stb_data_table.txt" ,"remote_stb_info.txt" , "remote_stb_2_info.txt");
		writefile("stb_parsed_data" , JSON.toJSONString(stbvo, true));
		SerializeHelper.saveObject(basedir +"stb_parsed_data.ser", stbvo);
		printCodeLiberayStatisticData("STB",stbvo);
		
		//TV
		CodeLiberayVo tvvo = createCodeLiberay("TV" ,"tv_table.txt" ,"TV_info.txt" , "remote_tv_info.txt" );
		ParseTVRemoteControllerCodeFile pr = new ParseTVRemoteControllerCodeFile();
		pr.processRemoteControllerCode(tvvo, basedir + "remote_tv_info.txt");
		writefile("tv_parsed_data" , JSON.toJSONString(tvvo, true));
		
		SerializeHelper.saveObject(basedir +"tv_parsed_data.ser", tvvo);
		printCodeLiberayStatisticData("TV",tvvo);
		
		
		//AC
		CodeLiberayVo acvo = createCodeLiberay("AC" ,"arc_table.txt" ,"g_remote_arc_info.txt" , "g_remote_arc_2_info.txt" );
		
		ParseACRemoteControllerCodeFile prac = new ParseACRemoteControllerCodeFile();
		prac.processRemoteControllerCode(acvo, basedir + "g_remote_arc_2_info.txt");
		writefile("arc_parsed_data" , JSON.toJSONString(acvo, true));

		
		SerializeHelper.saveObject(basedir +"ac_parsed_data.ser", acvo);
		printCodeLiberayStatisticData("AC",acvo);
		
		fosql.close();

	}
	
	private static void printCodeLiberayStatisticData(String prfex ,CodeLiberayVo vo)
	{
		System.out.println(prfex);
		System.out.println(String.format("Code liberays number : %d", vo.getCodeliberay().length));
		System.out.println(String.format("Productors number : %d", vo.getProductorIndexMap().size()));
		System.out.println(String.format("Productor maps : %d", vo.getProductorindex().length));
		//System.out.println(String.format("Remote Controller : %d", vo.getProductorRemoteCode().size()));
		
		System.out.println();
		System.out.println();
	}
	
	
	private static void print(CodeLiberayVo vo)
	{
		for ( String name : vo.getProductorIndexMap().keySet())
		{
			int index = vo.getProductorIndexMap().get(name);
			int[] pi = vo.getProductorindex()[index];
			
			System.out.printf("%s:", name);
			for ( int i = 0 ; i < pi.length ; i ++ )
				System.out.printf("%d ", pi[i]);
			System.out.println();
		}
	}
	
	private static CodeLiberayVo createCodeLiberay(String devicetype , String codefile , String producotrfile , String modelfile ) throws IOException
	{
		CodeLiberayVo vo = new CodeLiberayVo();
		if ( "AC".equals(devicetype))
			processACcode(devicetype ,vo , basedir + codefile);
		else 
			processTVSTBcode(devicetype ,vo , basedir + codefile);
		
		processProductorMap(devicetype,vo , basedir + producotrfile);
		
		if ( "AC".equals(devicetype))
			prcessACProductorModel(devicetype, basedir + modelfile);
		else 
			prcessProductorModel(devicetype, basedir + modelfile);
		return vo ;
	}
	
	private static void prcessProductorModel(String devicetype , String file) throws UnsupportedEncodingException, IOException
	{
		String str = readfile(file);
		Pattern pattern = Pattern.compile("\\{[^\\}]*\\}[ ,]*/\\*[^{]*" , Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) 
		{
			// s = {1,5452},/* �����������(Guangxi radio and television networks)-GX-013@1 */
			String s = matcher.group();
			
			List<Integer> lst = matchNumber(matchBrace(s));
			String cs = matchComment(s);

			String name_en = formatString(matchParentheses(cs));
			String name = formatString(matchBeforParentheses(cs));
			String model = formatString(matchAfterParentheses(cs));
						
			if ( StringUtils.isBlank(name_en) || StringUtils.isBlank(model) || lst.size() < 2)
			{
				log.error("prcessProductorModel:parse failed" + s);
				continue;
			}
			
			if ( model.startsWith("-"))
				model = model.substring(1);
			model = model.trim();
			
			fosql.write(String.format("insert into infreredcodeliberaymodel(productor,devicetype,model,codeid) values ( '%s','%s','%s',%d);\n",name_en,devicetype , model , lst.get(1) ).getBytes("UTF-8"));
		}
	}
	
	private static void prcessACProductorModel(String devicetype , String file) throws UnsupportedEncodingException, IOException
	{
		String str = readfile(file);
		Pattern pattern = Pattern.compile("\\{[^\\}]*\\}[ ,]*/\\*[^{]*" , Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) 
		{
			// s = {1,
			//   527},/* �¿�˹ S101060137B-0207(AUX S101060137B-0207)@18 */
			String s = matcher.group();
			
			List<Integer> lst = matchNumber(matchBrace(s));
			String cs = matchComment(s);
			String model = matchDuplicate(cs);
			if ( StringUtils.isBlank(model))
			{
				log.info(String.format("prcessACProductorModel: failed %s" , s));
				continue;
			}

			String pm = model;
			cs = cs.replace(pm, "");
			
			String name_ch = formatString(matchBeforParentheses(cs));
			String name_en = formatString(matchParentheses(cs));

			if ( StringUtils.isBlank(name_ch) || StringUtils.isBlank(name_en))
			{
				String sa[] = model.split(" ");
				if ( sa.length >= 2)
				{
					name_ch = formatString(sa[0]) ;
					name_en = formatString(sa[0]);
					model =  model.replace(name_ch, "");
				}
				else 
				{
					log.info(String.format("prcessACProductorModel: failed %s" , s));
					continue;
				}
			}

			model = formatString(model);
			fosql.write(String.format("insert into infreredcodeliberaymodel(productor,devicetype,model,codeid) values ( '%s','%s','%s',%d);\n",name_en,devicetype , model , lst.get(1) ).getBytes("UTF-8"));
		}
	}
	
	private static String matchDuplicate(String str)
	{
		Pattern pattern=Pattern.compile("(.{3,})(.*\\1){1,}");
		return matchOne(str , pattern , 1);
	}
	
	private static String matchAfterParentheses(String str) // ).......
	{		
		return matchOne(str ,  "\\)([^\\n]*)" , 1);
	}
	
	private static String matchBeforParentheses(String str) // .......(
	{		
		return matchOne(str ,  "([^(]*)" , 1);
	}

	
	private static String matchParentheses(String str) // (......)
	{		
		return matchOne(str , "\\(([^)]*)\\)" , 1);
	}
	
	private static String matchComment(String str ) // /*.....*/
	{
		return matchComment(str , 1,  1);
	}
	
	private static String matchComment(String str ,int groupid, int index) // /*.....*/
	{
		return matchOne(str , "\\/\\*([^\\*]*)\\*\\/"  ,groupid, index);
	}
	
	private static String matchBrace(String str) //{.....}
	{
		return matchOne(str , "[\\{\\r\\n \\t]*([^\\}]*)\\}" , 1);
	}
	
	private static String matchBrace(String str ,int groupid, int index) //{.....}
	{
		return matchOne(str , "[\\{\\r\\n \\t]*([^\\}]*)\\}" ,groupid, index);
	}
	
	private static List<Integer> matchNumber(String str)
	{
		if ( StringUtils.isBlank(str))
			return null ;
		
		Pattern pattern = Pattern.compile("([0-9]+)" , Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		
		List<Integer> lst = new ArrayList<Integer>();
		for ( ;matcher.find() ;)
			lst.add( Integer.valueOf(matcher.group(1)));
		return lst ;
	}
	
	private static List<String> matchline(String str)
	{
		if ( StringUtils.isBlank(str))
			return null ;
		
		Pattern pattern = Pattern.compile("[^\\r\\n]*" , Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		
		List<String> lst = new ArrayList<String>();
		for ( ;matcher.find() ;)
		{
			String s = matcher.group();
			if ( ! StringUtils.isBlank(s))
				lst.add(s );
		}
		return lst ;
	}
	
	private static String matchOne(String str , String pattern , int groupindex , int matchindex)
	{
		if ( StringUtils.isBlank(str) ||  StringUtils.isBlank(pattern))
			return null ;
		Pattern p = Pattern.compile(pattern , Pattern.DOTALL);
		return matchOne(str , p,groupindex,matchindex);
	}
	
	private static String matchOne(String str , String pattern , int groupindex)
	{
		if ( StringUtils.isBlank(str) ||  StringUtils.isBlank(pattern))
			return null ;
		Pattern p = Pattern.compile(pattern , Pattern.DOTALL);
		return matchOne(str , p,groupindex);
	}

	private static String matchOne(String str , Pattern pattern , int groupindex)
	{
		return matchOne(str , pattern , groupindex , 1);
	}
	
	private static String matchOne(String str , Pattern pattern , int groupindex, int matchindex)
	{
		if ( StringUtils.isBlank(str) || pattern == null )
			return null ;
		Matcher matcher = pattern.matcher(str);
		
		for ( int i = 0 ; i < matchindex - 1 && matcher.find() ; i ++)
			matcher.group(groupindex); // do nothing 
		if ( matcher.find())
			return matcher.group(groupindex);
		return null ;
	}
	
	private static void processProductorMap(String devicetype ,CodeLiberayVo vo , String file) throws UnsupportedEncodingException, IOException
	{
		String str = readfile(file);
		
		Pattern pattern = Pattern.compile("\\{[^\\}]*\\}[ ,]*/\\*[^\\r\\n]*" , Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		
		List<Integer[]> lst = new ArrayList<Integer[]>(1200);
		Map<String , Integer> productorIndexMap = new HashMap<String , Integer>();
		int i = 0 ;
		while (matcher.find()) 
		{
			// s = 
			//{
			//    {67,5770,5771,5772,5773,5774,5775,5776,5777,5778,5779,5566,5567,5568,5569,5570,5571,5572,5573,4962,4963,4964,4965,4966,4967,4968,4969,4970,4971,4972,4973,4974,4975,
			//    1107,1019,4671,4608,4609,4610,4611,4612,4613,4614,1105,1106,1019,0,1,2,
			//    461,462,576,577,3,4,342,343,344,345,346,347,348,349,350,351,352,353,354}, /* ����(Beijing)@5(+13 */
			String s = matcher.group();
			
			//System.out.println(s);
			List<Integer> lstci = matchNumber(matchBrace(s));
			if ( lstci == null || lstci.size() == 0 )
			{
				log.error(String.format("processProductorMap: failed %s", s));
				continue;
			}
			lstci.remove(0);
			Integer[] index = lstci.toArray(new Integer[0]);
			String sc = null ,enname = null ,cnname = null ;

			for ( int matchindex = 1 ; ; matchindex ++ )
			{
				sc = matchComment(s  ,1, matchindex);
				if ( sc == null)
					break;
				enname = formatString(processProductorEnName(sc));
				cnname = formatString(processProductorCnName(sc));
				
				if ( StringUtils.isNotBlank(enname) && StringUtils.isNotBlank(cnname))
					break;
			}
			
			if ( enname == null || index == null || enname.length() == 0 || index.length == 0 )
			{
				log.error(String.format("processProductorMap: failed %s", s));
				continue;
			}
			
			lst.add(index);
			enname = enname.toLowerCase();
			productorIndexMap.put(enname, i++);
			//System.out.println("\n------------------");
			fosql.write(String.format("insert into infrereddeviceproductor(productor,devicetype,name,name_en,codeids) values('%s','%s','%s','%s','%s');\n", enname,devicetype,cnname,enname,JSON.toJSONString(index)).getBytes("UTF-8"));
		}
		
		//vo.setProductorindex(lst.toArray(new Integer[0][0]));
		vo.setProductorIndexMap(productorIndexMap);
	}
	
	private static String formatString(String str)
	{
		if ( StringUtils.isBlank(str))
			return str ;
		return str.trim().replace("'", "''");
	}
	
	// s = /* ����(Beijing)@5(+13 */
	// ==> beijing
	private static String processProductorEnName(String str)
	{
		return matchParentheses(str);
	}
	
	// str = /* ����(Beijing)@5(+13 */
	// ==> ����
	private static String processProductorCnName(String str)
	{
		return matchBeforParentheses(str);
	}
	
	// str = 
	//{
	//    {67,5770,5771,5772,5773,5774,5775,5776,5777,5778,5779,5566,5567,5568,5569,5570,5571,5572,5573,4962,4963,4964,4965,4966,4967,4968,4969,4970,4971,4972,4973,4974,4975,
	//    1107,1019,4671,4608,4609,4610,4611,4612,4613,4614,1105,1106,1019,0,1,2,
	//    461,462,576,577,3,4,342,343,344,345,346,347,348,349,350,351,352,353,354}, /* ����(Beijing)@5(+13 */
	
	// return : [67, 5770, 5771, 5772, 5773, 5774, 5775, 5776, 5777, 5778, 5779, 5566, 5567, 5568, 5569, 5570, 5571, 5572, 5573, 4962, 4963, 4964, 4965, 4966, 4967, 4968, 4969, 4970, 4971, 4972, 4973, 4974, 4975, 1107, 1019, 4671, 4608, 4609, 4610, 4611, 4612, 4613, 4614, 1105, 1106, 1019, 0, 1, 2, 461, 462, 576, 577, 3, 4, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 354]
	private static Integer[] processProductorIndex(String str)
	{
		//Pattern pattern = Pattern.compile("\\{[0-9, \\r\\n]*\\}" , Pattern.DOTALL);
		Pattern pattern = Pattern.compile("\\{*([^\\}]*)\\}" , Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		
		if ( !matcher.find())
			return null ;
		
		String s = matcher.group(1);
		pattern = Pattern.compile("([0-9]+)" , Pattern.DOTALL);
		matcher = pattern.matcher(s);

		List<Integer> lst = new ArrayList<Integer>();
		for ( ;matcher.find() ;)
			lst.add( Integer.valueOf(matcher.group(1)));
		return lst.toArray(new Integer[0]);
	}
	
	private static void processACcode(String devicetype ,CodeLiberayVo vo , String file) throws IOException
	{
		String str = readfile(file);
		
		Pattern pattern = Pattern.compile("\\{[0-9xXa-fA-F,\\r\\n ]*\\}," , Pattern.DOTALL);

		List<int[]> lst = new ArrayList<int[]>(12000);
		int i = 0 ;

		Matcher matcher = pattern.matcher(str);
		for (;matcher.find();) 
		{
			//s = {0x01,0x0B,0xF4,0x1B,0xE4,0x0F,0xF0,0x03,0xFC,0x19,0xE6,0x11,0xEE,0x01,0xFE,0x09,0xF6,0x1D,0xE2,0x0D,0xF2,0x4C,0xB3,0x0C,0xF3,0x10,0xEF,0x0E,0xF1,0x1A,0xE5,0x06,0xF9,0x12,0xED,0x0A,0xF5,0x12,0xED,0x1A,0xE5,0x0E,0xF1,0x0A,0xF5,0x1F,0xE0,0x44,0x9B,0x00,0x00},
			String s = matcher.group();

			//code = [1, 11, 244, 27, 228, 15, 240, 3, 252, 25, 230, 17, 238, 1, 254, 9, 246, 29, 226, 13, 242, 76, 179, 12, 243, 16, 239, 14, 241, 26, 229, 6, 249, 18, 237, 10, 245, 18, 237, 26, 229, 14, 241, 10, 245, 31, 224, 68, 155, 0, 0]
			int code[] = parseline(s);
			int[] encrypt = encrypt(code,i,devicetype);
			
			lst.add(encrypt);
		
			fosql.write(String.format("insert into infreredcodeliberay(devicetype,codeid,code) values('%s','%s','%s');\n", devicetype, i++ , JSON.toJSONString(encrypt) ).getBytes("UTF-8"));
		}

		vo.setCodeliberay(lst.toArray(new int[0][0]));
		}
		
	private static void processTVSTBcode(String devicetype ,CodeLiberayVo vo , String file) throws IOException
	{
		String str = readfile(file);
		List<String> lsts = matchline(str);

		Pattern pattern = Pattern.compile("\\{[0-9xXa-fA-F,\\r\\n ]*\\}," , Pattern.DOTALL);

		List<int[]> lst = new ArrayList<int[]>(12000);
		int i = 0 ;
		for ( String sl : lsts)
		{
			Matcher matcher = pattern.matcher(sl);
			if (matcher.find()) 
			{
				//s = {0x01,0x0B,0xF4,0x1B,0xE4,0x0F,0xF0,0x03,0xFC,0x19,0xE6,0x11,0xEE,0x01,0xFE,0x09,0xF6,0x1D,0xE2,0x0D,0xF2,0x4C,0xB3,0x0C,0xF3,0x10,0xEF,0x0E,0xF1,0x1A,0xE5,0x06,0xF9,0x12,0xED,0x0A,0xF5,0x12,0xED,0x1A,0xE5,0x0E,0xF1,0x0A,0xF5,0x1F,0xE0,0x44,0x9B,0x00,0x00},
				String s = matcher.group();
	
				//code = [1, 11, 244, 27, 228, 15, 240, 3, 252, 25, 230, 17, 238, 1, 254, 9, 246, 29, 226, 13, 242, 76, 179, 12, 243, 16, 239, 14, 241, 26, 229, 6, 249, 18, 237, 10, 245, 18, 237, 26, 229, 14, 241, 10, 245, 31, 224, 68, 155, 0, 0]
				int code[] = parseline(s);
				int[] encrypt = encrypt(code,i,devicetype);
				String codeid = matchComment(sl);
				
				lst.add(encrypt);
				
				fosql.write(String.format("insert into infreredcodeliberay(devicetype,codeid,code) values('%s','%s','%s');\n", devicetype, codeid , JSON.toJSONString(encrypt) ).getBytes("UTF-8"));
			}
			else 
				System.out.println(sl);
		}
		vo.setCodeliberay(lst.toArray(new int[0][0]));
	}
	
	public static int[] parseline(String line)
	{
		line = line.trim().replace("{", "").replace("},", "").replace("}", "").replaceAll("0x", "").replaceAll("0X", "");
		String[] c = line.split(",");
		//if ( c == null || ( c.length != 55 && c.length != 51 ) )
		//	return null ;
		int[] code = new int[c.length];
		for ( int i = 0 ; i < c.length ; i ++)
			code[i] = Integer.valueOf(c[i].trim() , 16);
		return code ;
	}
	
	private static int[] encrypt(int[] icl,int codeid,String devicetype){
		if ( IRemoteConstantDefine.DEVICE_TYPE_AC.equals(devicetype))
			icl = CodeLiberayHelper.composeACCodeLiberay(codeid, icl);
		else if ( IRemoteConstantDefine.DEVICE_TYPE_STB.equals(devicetype))
			icl = CodeLiberayHelper.composeSTBCodeLiberay(icl);
		else if ( IRemoteConstantDefine.DEVICE_TYPE_TV.equals(devicetype))
			icl = CodeLiberayHelper.composeTVCodeLiberay(icl);
		return icl;
	}
	
	private static void splitFile()
	{
		String str = readfile(basedir + "\\" + CODE_FILE_NAME);
		System.out.println(str.length());
		
		
		Pattern pattern = Pattern.compile("const[^;]*;" , Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		
		
		while (matcher.find()) 
		{
			String s = matcher.group();
			String name = getname(s);
			writefile(name , s);
//			System.out.println(name);
//			System.out.println(s);
//			System.out.println("------------------");
		}
		
	}
	
	public static String readfile(String filename)
	{
		File f =new File(filename);
		
		try {
			InputStream is = new FileInputStream(f);
			byte b[] = new byte[is.available()];
			is.read(b);
			is.close();
			return new String(b);
		} catch (Throwable e) {
			e.printStackTrace();
		} 
		return null ;
	}
	
	private static String getname(String content)
	{
		Pattern pattern = Pattern.compile("const[^\\[]*");
		Matcher matcher = pattern.matcher(content);
		
		if ( matcher.find() )
		{
			String str = matcher.group();
			String[] s = str.split(" ");
			return s[s.length -1];
		}
		return "";
	}
	
	private static void writefile(String name , String content)
	{
		File f = new File(basedir + name + ".txt");
		if ( f.exists() )
			f.delete();
		try {
			OutputStream os = new FileOutputStream(f);
			os.write(content.getBytes());
			
			os.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void writeSerializedFile(String filename , Object obj)
	{
		try
		{
	        String fn = filename ;
	        
	        FileOutputStream fos = new FileOutputStream(STORE_PATH+fn);
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(obj);
	        oos.close();
	        fos.close();
		}
		catch(Throwable t)
		{
			log.error("", t);
		}
	}
	
	public static Object readSerializedFile(String filename)
	{
		try
		{
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream in = loader.getResourceAsStream(filename);
			if ( in == null )
				return null;
	        
	        ObjectInputStream ois = new ObjectInputStream(in);
	        Object obj = ois.readObject();
	        ois.close();
	        in.close();
	        return obj ;
		}
		catch(InvalidClassException e)
		{
			log.info("Ignore exception", e);
		}
		catch(Throwable t)
		{
			log.error("", t);
		}
		return null ;
	}
	
	private static boolean test()
	{
		String str = "{0X01,0X10,0XEF,0X18,0XE7,0X0A,0XF5,0X1C,0XE3,0X0C,0XF3,0X01,0XFE,0X04,0XFB,0X15,0XEA,0X16,0XE9,0X17,0XE8,0X19,0XE6,0X1A,0XE5,0X1B,0XE4,0X1D,0XE2,0X1E,0XE1,0X1F,0XE0,0X4B,0XB4,0X41,0XBE,0X08,0XF7,0X44,0XBB,0X0D,0XF2,0X0B,0XF4,0X49,0XB6,0X4A,0XB5,0X0F,0XF0,0X00,0Xbd,0X00,0X00},/*10138*/";
		Pattern pattern = Pattern.compile("\\{[0-9xXa-fA-F,\\r\\n ]*\\}," , Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		System.out.println(matcher.find());
		return false;
}
}
