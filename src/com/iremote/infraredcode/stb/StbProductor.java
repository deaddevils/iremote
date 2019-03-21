package com.iremote.infraredcode.stb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.infraredcode.tv.TvCodeLiberay;

public class StbProductor {

	private static Log log = LogFactory.getLog(StbProductor.class);
	
	private static StbProductor instance = new StbProductor();

	public static StbProductor getInstance()
	{
		return instance;
	}
	
	public Integer[] getProductorSeqence(String productor)
	{
		if ( productor == null )
			return null ;

		List<Integer> lst = new ArrayList<Integer>();
		
		Set<String> set = combineProductor.get(productor.toLowerCase());
		
		if ( set == null )
		{
			for ( int i = 0 ; i < StbProductor.productor.length ; i ++ )
				if ( StbProductor.productor[i].equalsIgnoreCase(productor) )
					lst.add(i);
		}
		else 
		{
			for ( int i = 0 ; i < StbProductor.productor.length ; i ++ )
			{
				if ( set.contains(StbProductor.productor[i]))
					lst.add(i);
			}
		}
		
		return lst.toArray(new Integer[0]);
	}
	
	private StbProductor()
	{
		//init();
	}
	
	private void init()
	{		
		String str = readfile();
		
		Pattern pattern = Pattern.compile("(/.*/)");
		Matcher matcher = pattern.matcher(str);
				
		List<String> lst = new ArrayList<String>();
		//Map<String , Integer> map = new HashMap<String , Integer>();
		//Map<String , Integer> nmap = new HashMap<String , Integer>();
		//int i = 0 ;
		
		while(matcher.find())
		{      
			String s = matcher.group();
			s = s.replace("/*", "").replace("*/", "").trim();
			//String name = s.substring(0 , s.indexOf("("));
			String code = s.substring(s.indexOf("(") + 1 ,s.indexOf(")")).replace(" ", "").replace("/", "");
			
			//System.out.print(s + "   ");
		    //System.out.print(name);
		   // System.out.print("----");
			//System.out.println(code);
			//if ( map.get(code) != null )
			//	System.out.println(code + ":" + s);
			//if ( nmap.get(name) != null )
			//	System.out.println(name + ":" + s);
			//nmap.put(name, i);
			//map.put(code, i++);
			lst.add(code.toLowerCase());
			
			//System.out.println("\"" + code.toLowerCase() + "\"," + "\t\t//" + s);
		}
		//System.out.println("over");
		
		//productor = lst.toArray(new String[0]);
	}
	
	private String readfile()
	{
		try {
			InputStream is = TvCodeLiberay.class.getClassLoader().getResourceAsStream("resource/stbproductor");
			byte b[] = new byte[is.available()];
			is.read(b);
			is.close();
			return new String(b);
		} catch (IOException e) {
			log.error(e.getMessage() , e);
		}
		return "";
	}
	
	public static void main(String arg[])
	{
		StbProductor.getInstance().init();
	}
	
	private static String[] productor = new String[]{
		"beijing",		//北京(Beijing)@5+2+2(+13
		"guangdong",		//广东(Guangdong)@28+65+19+5(+17
		"guangxi",		//广西(Guangxi)@3+3+2+1(+26
		"shanghaiorientalcable",		//上海/东方有线(Shanghai / Oriental Cable)@2+5+2(+14
		"tianjin",		//天津(Tianjin)@4+3+4(+10
		"chongqing",		//重庆(Chongqing)@5+4+4(+16
		"liaoning",		//辽宁(LIAONING)@13+11+11(+17
		"jiangsu",		//江苏(JIANGSU)@17+10+11(+14
		"hubei",		//湖北(Hubei)@7+10+10+(+15
		"sichuan",		//四川(Sichuan)@3+3+3(+12
		"shaanxi",		//陕西(Shaanxi)@15+9+9(+10
		"zhejiang",		//浙江(Zhejiang)@22+14+15(+17
		"hunan",		//湖南(Hunan)@7+1+1(+9
		"shandong",		//山东(Shandong)@16+19+(+21
		"anwei",		//安微(Anwei)@2+18+1(+16
		"guizhou",		//贵州(Guizhou)@5+6+10(+10
		"heilongjiang",		//黑龙江(Heilongjiang)@11+11+11+(+19
		"shanxi",		//山西(Shanxi)@6+7+7+(+12
		"innermongolia",		//内蒙(Inner Mongolia)@3+2+2+(+9
		"yunnan",		//云南(Yunnan)@4+6+8(+8
		"henan",		//河南(Henan)@10+10+10+(+10
		"hainan",		//海南(Hainan)@3+1+1(+12
		"jilin",		//吉林(Jilin)@1+1+1(+17
		"hebei",		//河北(Hebei)@3+8+6+(+18
		"fujian",		//福建(Fujian)@5+11+11(+17
		"xinjiang",		//新疆(Xinjiang)@ 2+1+2+(+11
		"jiangxi",		//江西(Jiangxi)@1+7+1(+18
		"eastpacific",		//东太(East Pacific)@1+(+5
		"coshipstb",		//同洲机顶盒(Coship stb)@    2+3+24(+25
		"otherregions",		//其它城市(Other regions)@ 146,
		"staronthe9th",		//中星九号(Star on the 9th)@    2+(+8
		"gansu",		//甘肃(Gansu)@4+4+(+4
		"hongkong",		//香港(Hong Kong)@3+3(+
		"taiwan",		//台湾(Taiwan)@1+1(+
		"huaweistb",		//华为机顶盒(Huawei STB)@3+24(+
		"changhongstb",		///长虹机顶盒(Changhong STB)@4+9(+
		"konkastb",		//康佳机顶盒(Konka STB)@5+2(+
		"ztestb",		//中兴机顶盒(ZTE STB)@9+(+6
		"jiuzhou",		//九洲机顶盒(九洲机顶盒)@5+14(+
		"motorolastb",		//摩托罗拉机顶盒(Motorola STB)@+6(+
		"skyworthstb",		//创维机顶盒(Skyworth STB)@5+13(+
		"hisensestb",		//海信机顶盒(Hisense STB)@5+7(+
		"haierstb",		//海尔机顶盒(Haier STB)@5+2(+
		"gospellstb",		//高斯贝尔机顶盒(GOSPELL STB)@4+3(+
		"hoimeidistb",		//海美迪机顶盒(Hoi Mei Di STB)@10+2(+
		"openborstb",		//开博尔机顶盒(Open Bor STB)@5+2+7(+
		"dvnstb",		//天柏机顶盒(DVN STB)@4+12(+
		"daxianstb",		//大显机顶盒(Daxian STB)@5+2(+
		"musicasstb",		//乐视机顶盒(Music as STB)@5+(+
		"starcomstb",		//斯达康机顶盒(Starcom STB)@4+4(+
		"galaxystb",		//银河机顶盒(Galaxy STB)@4+4+1(+
		"ninejoint",		//九联(Nine joint)@4+8(+
		"gehuastb",		//歌华高清机顶盒(Gehua STB)@5+4(+
		"tianweistb",		//天威机顶盒(Tianwei STB)@5+2(+
		"huaxiastb",		//夏华机顶盒(Hua Xia STB)@5+2(+
		"jiachongstb",		//佳创机顶盒(Jia Chong STB)@5+1(+
		"jiacaistb",		//佳彩机顶盒(Jia Cai STB)@5+2(+
		"goldnetcomstb",		//金网通机顶盒(Gold Netcom STB)@5+3(+
		"juyounetwork",		//聚友网络(Juyou Network)@5+1(+
		"u.s.picturesquestb",		//美如画机顶盒(U.S. picturesque STB)@5+6(+
		"eastwidesight",		//东方广视(East WideSight)@5+(+
		"panasonicstb",		//松下机顶盒(Panasonic STB)@5+1(+
		"svastb",		//上广电机顶盒(SVA STB)@5+1(+
		"bbefstb",		//北广机顶盒(BBEF STB)@5+5(+
		"panoramicstb",		//全景机顶盒(Panoramic STB)@4+4(+
		"airdivisionstb",		//航科机顶盒(Air Division STB)@4+1(+
		"leaguerstb",		//力合机顶盒(Leaguer STB)@4+1(+
		"yumstb",		//百胜机顶盒(Yum STB)/MATRIX@2+2(+
		"matrix",		//MATRIX(MATRIX)@2+2(+
		"wave",		//浪潮机顶盒(Wave)@3+(+
		"topway",		//深圳天威视讯(TOPWAY)@1+(+
		"gehua",		//北京歌华有线(Gehua)@1+(+
		"huhutong",		//户户通(Huhutong)@1+(+
		"mib",		//小米盒子(MIB)@2+(+
		"anhuiradio",		//安徽广电(Anhui radio)@2
		"anextensivenetwork",		//安广网络(An extensive network)@2
		"anhuihaier",		//安徽海尔(Anhui Haier)@2
		"anqing",		//安庆(Anqing)@2
		"frogbu",		//蛙埠(Frog Bu)@2
		"chizhou",		//池州(Chizhou)@2
		"chizhouradioandtelevision",		//池州广电(Chizhou radio and television)@2
		"chuzhou",		//滁州(Chuzhou)@2
		"fuyang",		//阜阳(Fuyang)@2
		"bozhou",		//毫州(Bozhou)@2
		"hefei",		//合肥(Hefei)@2
		"hefeicable",		//合肥有线(Hefei cable)@2
		"huaibei",		//淮北(Huaibei)@2
		"huainan",		//淮南(Huainan)@2
		"huainanradioandtelevision",		//淮南广电(Huainan radio and television)@2
		"mounthuangshan",		//黄山(Mount Huangshan)@2
		"luan",		//六安(Lu'an)@2
		"luandigital",		//六安数字(Lu'an digital)@2
		"maanshan",		//马鞍山(Ma'anshan)@2
		"suzhou",		//宿州(Suzhou)@2
		"suzhouradioandtelevision",		//宿州广电(Suzhou radio and television)@2
		"tongling",		//铜陵(Tongling)@2
		"tonglingradioandtelevision",		//铜陵广电(Tongling radio and television)@2
		"wuhu",		//芜湖(Wuhu)@2
		"xuancheng",		//宣城(Xuancheng)@2
		"thatradioandtelevision",		//宣称广电(That radio and television)@2
		"beijinggehuacablehd",		//北京歌华有线高清(Beijing Gehua cable HD)@2
		"chongqingchanghong",		//重庆长虹(Chongqing Changhong)@2
		"chongqingyinke",		//重庆银科(Chongqing Yinke)@2
		"chongqingcable",		//重庆有线(Chongqing cable)@2
		"chongqingcabletwo",		//重庆有线二(Chongqing cable two)@2
		"fujianradioandtelevision",		//福建广电(Fujian radio and television)@2
		"fuzhouradioandtelevision",		//福州广电(Fuzhou radio and television)@2
		"longyan",		//龙岩(Longyan)@2
		"longyancable",		//龙岩有线(Longyan cable)@2
		"nanping",		//南平(Nanping)@2
		"nanpingcable",		//南平有线(Nanping cable)@2
		"ningde",		//宁德(Ningde)@2
		"ningdecable",		//宁德有线(Ningde cable)@2
		"putian",		//浦田(Pu Tian)@2
		"putiancable",		//浦田有线(Pu Tian cable)@2
		"quanzhou",		//泉州(Quanzhou)@2
		"quanzhouradioandtelevision",		//泉州广电(Quanzhou radio and television)@2
		"sanming",		//三明(Sanming)@2
		"sanmingbroadcast",		//三明广电(Sanming broadcast)@2
		"xiamen",		//厦门(Xiamen)@2
		"xiamenskyworth",		//厦门创维(Xiamen SKYWORTH)@2
		"xiamendahua",		//厦门大华(Xiamen Dahua)@2
		"xiamentsinghuatongfang",		//厦门清华同方(Xiamen Tsinghua Tongfang)@2
		"zhangzhou",		//漳州(Zhangzhou)@2
		"zhangzhouradioandtelevision",		//漳州广电(Zhangzhou radio and television)@2
		"silver",		//白银(Silver)@2
		"dingxi",		//定西(Dingxi)@2
		"gansuradioandtelevision",		//甘肃广电(Gansu radio and television)@2
		"gansuhuawei",		//甘肃华为(Gansu Huawei)@2
		"gansujiesaitechnology",		//甘肃杰赛科技(Gansu Jiesai Technology)@2
		"gansustatecoships58",		//甘肃同州COSHIPS58(Gansu state COSHIPS58)@2
		"gansucable",		//甘肃有线(Gansu cable)@2
		"jiayuguan",		//嘉峪关(Jiayuguan)@2
		"jinchang",		//金昌(Jinchang)@2
		"jiuquan",		//酒泉(Jiuquan)@2
		"lanzhou",		//兰州(Lanzhou)@2
		"lanzhoupetrochemical",		//兰州石化(Lanzhou petrochemical)@2
		"longnan",		//陇南(Longnan)@2
		"folkmusic",		//民乐(Folk music)@2
		"musicradio",		//民乐广电(Music Radio)@2
		"pingliang",		//平凉(Pingliang)@2
		"qingyang",		//庆阳(Qingyang)@2
		"tianshui",		//天水(Tianshui)@2
		"wuwei",		//武威(Wuwei)@2
		"zhangye",		//张掖(Zhangye)@2
		"chaozhou",		//潮州(Chaozhou)@2
		"chaoancable",		//潮安有线(Chaoan cable)@2
		"dongguan",		//东莞(Dongguan)@2
		"dongguanradioandtvone",		//东莞广电一(Dongguan radio and TV one)@2
		"dongguanradioandtvtwo",		//东莞广电二(Dongguan radio and TV two)@2
		"foshan",		//佛山(Foshan)@2
		"foshantsinghuatongfang",		//佛山清华同方(Foshan Tsinghua Tongfang)@2
		"withafoshan",		//佛山同州一(With a Foshan)@2
		"foshanwithtwo",		//佛山同州二(Foshan with two)@2
		"foshancable",		//佛山有线(Foshan cable)@2
		"guangzhou",		//广州(Guangzhou)@2
		"guangzhoucable",		//广州有线(Guangzhou cable)@2
		"pearldigital",		//珠江数码(Pearl digital)@2
		"heyuan",		//河源(Heyuan)@2
		"heyuanradioandtelevision",		//河源广电(Heyuan radio and television)@2
		"heyuanjiacai",		//河源佳彩(Heyuan Jia Cai)@2
		"huizhou",		//惠州(Huizhou)@2
		"huizhoucable",		//惠州有线(Huizhou cable)@2
		"jiangmen",		//江门(Jiangmen)@2
		"jiangmenradioandtelevision",		//江门广电(Jiangmen radio and television)@2
		"jieyang",		//揭阳(Jieyang)@2
		"maoming",		//茂名(Maoming)@2
		"meizhou",		//梅州(Meizhou)@2
		"meizhoucable",		//梅州有线(Meizhou cable)@2
		"thesouthchinasea",		//南海(The South China Sea)@2
		"nanhaibroadcasting",		//南海广电(Nanhai broadcasting)@2
		"puning",		//普宁(Puning)@2
		"puningcable",		//普宁有线(Puning cable)@2
		"qingyuan",		//清远(Qingyuan)@2
		"qingyuanradioandtelevision",		//清远广电(Qingyuan radio and television)@2
		"theimmortalhead",		//仙头(The immortal head)@2
		"chaoyangcable",		//朝阳有线(Chaoyang cable)@2
		"guiyucable",		//贵屿有线(Guiyu cable)@2
		"fairytail",		//仙尾(Fairy tail)@2
		"shaoguan",		//韶关(Shaoguan)@2
		"shenzhen",		//深圳(Shenzhen)@2
		"shenzhenprint-ritesd",		//深圳天威标清(Shenzhen Print-Rite SD)@2
		"shenzhenprint-ritehd",		//深圳天威高清(Shenzhen Print-Rite HD)@2
		"yangjiang",		//阳江(Yangjiang)@2
		"yunfu",		//云浮(Yunfu)@2
		"zengcheng",		//增城(Zengcheng)@2
		"zengchengcable",		//增城有线(Zengcheng cable)@2
		"zhanjiang",		//湛江(Zhanjiang)@2
		"zhanjiangradioandtelevision",		//湛江广电(Zhanjiang radio and television)@2
		"zhaoqing",		//肇庆(Zhaoqing)@2
		"zhongshan",		//中山(Zhongshan)@2
		"zhongshancable",		//中山有线(Zhongshan cable)@2
		"zhuhai",		//珠海(Zhuhai)@2
		"zhuhairadioandtvone",		//珠海广电一(Zhuhai radio and TV one)@2
		"zhuhairadioandtvtwo",		//珠海广电二(Zhuhai radio and TV two)@2
		"zhuhainetwork",		//珠海网络(Zhuhai network)@2
		"zhuhaicable",		//珠海有线(Zhuhai cable)@2
		"baise",		//百色(Baise)@2
		"thenorthsea",		//北海(The North Sea)@2
		"chongzuo",		//崇左(Chongzuo)@2
		"portoffangcheng",		//防城港(Port of Fangcheng)@2
		"guangxiradioandtvnetwork",		//广西广电网络(Guangxi radio and TV network)@2
		"guigang",		//贵港(Guigang)@2
		"guilin",		//桂林(Guilin)@2
		"hechi",		//河池(Hechi)@2
		"hezhou",		//贺州(Hezhou)@2
		"guest",		//来宾(Guest)@2
		"liuzhou",		//柳州(Liuzhou)@2
		"nanning",		//南宁(Nanning)@2
		"qinzhou",		//钦州(Qinzhou)@2
		"wuzhou",		//梧州(Wuzhou)@2
		"guangxiyulin",		//玉林(guangxi Yulin)@2
		"anshun",		//安顺(Anshun)@2
		"bijie",		//毕节(Bijie)@2
		"guiyang",		//贵阳(Guiyang)@2
		"guizhouradioandtelevision",		//贵州广电(Guizhou radio and television)@2
		"liupanshui",		//六盘水(Liupanshui)@2
		"tongren",		//铜仁(Tongren)@2
		"zunyi",		//遵义(Zunyi)@2
		"zunyiradioandtelevision",		//遵义广电(Zunyi radio and television)@2
		"zunyihuawei",		//遵义华为(Zunyi Huawei)@2
		"zunyinetcom",		//遵义金网通(Zunyi Netcom)@2
		"zunyikyushu",		//遵义九州(Zunyi Kyushu)@2
		"zunyidigital",		//遵义数字(Zunyi digital)@2
		"zunyistate",		//遵义同州(Zunyi state)@2
		"haikou",		//海口(Haikou)@2
		"hainancable",		//海南有线(Hainan cable)@2
		"hainanradioandtelevision",		//海南广电(Hainan radio and television)@2
		"sanya",		//三亚(Sanya)@2
		"baoding",		//保定(Baoding)@2
		"botou",		//泊头(Botou)@2
		"botoucablea",		//泊头有线一(Botou cable a)@2
		"botoucabletwo",		//泊头有线二(Botou cable two)@2
		"cangzhou",		//沧州(Cangzhou)@2
		"cangzhoucable",		//沧州有线(Cangzhou cable)@2
		"chengde",		//承德(Chengde)@2
		"chengderadioandtelevision",		//承德广电(Chengde radio and television)@2
		"handan",		//邯郸(Handan)@2
		"hengshui",		//衡水(Hengshui)@2
		"yongfang",		//鄘坊(Yong Fang)@2
		"qinghuangdao",		//秦皇岛(Qinghuangdao)@2
		"shijiazhuang",		//石家庄(Shijiazhuang)@2
		"hebeiradioandtvnetwork",		//河北广电网络(Hebei radio and TV network)@2
		"tangshan",		//唐山(Tangshan)@2
		"xingtai",		//邢台(Xingtai)@2
		"zhangjiakou",		//张家口(Zhangjiakou)@2
		"daqing",		//大庆(Daqing)@2
		"daqingcable",		//大庆有线(Daqing cable)@2
		"harbin",		//哈尔滨(Harbin)@2
		"harbinknow-all",		//哈尔滨百事通(Harbin know-all)@2
		"harbinsidakang",		//哈尔滨斯达康(Harbin Sidakang)@2
		"harbincablea",		//哈尔滨有线一(Harbin cable a)@2
		"harbincabletwo",		//哈尔滨有线二(Harbin cable two)@2
		"harbincablethree",		//哈尔滨有线三(Harbin cable three)@2
		"hegang",		//鹤岗(Hegang)@2
		"heihe",		//黑河(Heihe)@2
		"heiheradioandtelevision",		//黑河广电(Heihe radio and television)@2
		"blacklongjiangbroadcasting",		//黒龙江广电(Black Longjiang broadcasting)@2
		"blacklongjiangchanghong",		//黒龙江长虹(Black Longjiang Changhong)@2
		"heilongjiangstatefarms",		//黑龙江农垦(Heilongjiang state farms)@2
		"farmingcable",		//农垦有线(Farming cable)@2
		"jiamusi",		//佳木斯(Jiamusi)@2
		"jixi",		//鸡西(Jixi)@2
		"mudanjiang",		//牡丹江(Mudanjiang)@2
		"mudanjiangcable",		//牡丹江有线(Mudanjiang cable)@2
		"qigihar",		//齐齐哈尔(Qigihar)@2
		"qigiharcable",		//齐齐哈尔有线(Qigihar cable)@2
		"qitaihe",		//七台河(Qitaihe)@2
		"shuangyashan",		//双鸭山(Shuangyashan)@2
		"suihua",		//绥化(Suihua)@2
		"suihuacable",		//绥化有线(Suihua cable)@2
		"tailai",		//泰来(Tailai)@2
		"tailaicable",		//泰来有线(Tailai cable)@2
		"heilongjiangyichun",		//伊春(heilongjiang Yichun)@2
		"anyang",		//安阳(Anyang)@2
		"anyangcable",		//安阳有线(Anyang cable)@2
		"hebi",		//鹤壁(Hebi)@2
		"henanradioandtelevision",		//河南广电(Henan radio and television)@2
		"henangni",		//河南GNI(Henan GNI)@2
		"henanvcom",		//河南威科姆(Henan VCOM)@2
		"jiaozuo",		//焦作(Jiaozuo)@2
		"jiyuan",		//济源(Jiyuan)@2
		"kaifeng",		//开封(Kaifeng)@2
		"kaifengradioandtelevision",		//开封广电(Kaifeng radio and television)@2
		"luohe",		//漯河(Luohe)@2
		"luoyang",		//洛阳(Luoyang)@2
		"nanyang",		//南阳(Nanyang)@2
		"pingdingshan",		//平顶山(Pingdingshan)@2
		"puyang",		//濮阳(Puyang)@2
		"sanmenxia",		//三门峡(Sanmenxia)@2
		"sanmenxiacablea",		//三门峡有线一(Sanmenxia cable a)@2
		"sanmenxiacabletwo",		//三门峡有线二(Sanmenxia cable two)@2
		"sanmenxiacablethree",		//三门峡有线三(Sanmenxia cable three)@2
		"sanmenxiacablefour",		//三门峡有线四(Sanmenxia cable four)@2
		"shangqiu",		//商丘(Shangqiu)@2
		"shangqiuradioandtelevision",		//商丘广电(Shangqiu radio and television)@2
		"yongchengcable",		//永城有线(Yongcheng cable)@2
		"xiangcheng",		//项城(Xiangcheng)@2
		"xiangchengcable",		//项城有线(Xiangcheng cable)@2
		"xinxiang",		//新乡(Xinxiang)@2
		"xinyang",		//信阳(Xinyang)@2
		"xuchang",		//许昌(Xuchang)@2
		"zhengzhou",		//郑州(Zhengzhou)@2
		"zhengzhouhuilong",		//郑州慧龙(Zhengzhou Huilong)@2
		"zhengzhoumotorola",		//郑州摩托罗拉(Zhengzhou Motorola)@2
		"zhengzhougalaxy",		//郑州银河(Zhengzhou Galaxy)@2
		"zhoukou",		//周口(Zhoukou)@2
		"zhumadian",		//驻马店(Zhumadian)@2
		"ezhou",		//鄂州(Ezhou)@2
		"huanggang",		//黄冈(Huanggang)@2
		"huangshi",		//黄石(Huangshi)@2
		"hubeiradioandtelevision",		//湖北广电(Hubei radio and television)@2
		"hubeichanghong",		//湖北长虹(Hubei Changhong)@2
		"jingmen",		//荆门(Jingmen)@2
		"jingzhou",		//荆州(Jingzhou)@2
		"shiyan",		//十堰(Shiyan)@2
		"suizhou",		//随州(Suizhou)@2
		"wuhan",		//武汉(Wuhan)@2
		"wuhanskyworth",		//武汉创维(Wuhan SKYWORTH)@2
		"wuhanhuawei",		//武汉华为(Wuhan Huawei)@2
		"xiangfan",		//襄樊(xiangfan)@2
		"xianning",		//咸宁(Xianning)@2
		"xianningdigital",		//咸宁数字(Xianning digital)@2
		"xiaogan",		//孝感(Xiaogan)@2
		"xuanchang",		//宣昌(Xuan Chang)@2
		"changde",		//常德(Changde)@2
		"changsha",		//长沙(Changsha)@2
		"changshabroadcasting",		//长沙广电(Changsha broadcasting)@2
		"chenzhou",		//郴州(Chenzhou)@2
		"chenzhoucable",		//郴州有线(Chenzhou cable)@2
		"hengyang",		//衡阳(Hengyang)@2
		"hengyangcable",		//衡阳有线(Hengyang cable)@2
		"huaihua",		//怀化(Huaihua)@2
		"hunanradioandtelevision",		//湖南广电 (Hunan radio and television)@2
		"loudi",		//娄底(Loudi)@2
		"shaoyang",		//邵阳(Shaoyang)@2
		"xiangtan",		//湘潭 (Xiangtan)@2
		"yiyang",		//益阳(Yiyang)@2
		"yongzhou",		//永州(yongzhou)@2
		"yueyang",		//岳阳(Yueyang)@2
		"zhangjiajie",		//张家界(Zhangjiajie)@2
		"zhuzhou",		//株洲(Zhuzhou)@2
		"changzhou",		//常州(Changzhou)@2
		"changzhoucable",		//常州有线(Changzhou cable)@2
		"haimen",		//海门(Haimen)@2
		"haimenisland",		//海门同洲 (Haimen Island)@2
		"huaian",		//淮安(Huaian)@2
		"jiangyin",		//江阴(Jiangyin)@2
		"jiangyincable",		//江阴有线(Jiangyin cable)@2
		"lianyungang",		//连云港(Lianyungang)@2
		"nanjing",		//南京(Nanjing)@2
		"jiangsuinteraction",		//江苏互动(Jiangsu interaction)@2
		"nantong",		//南通(Nantong)@2
		"suqian",		//宿迁(Suqian)@2
		"suqiancable",		//宿迁有线(Suqian cable)@2
		"jiangsusuzhou",		//苏州(jiangsu Suzhou)@2
		"suzhoudigital",		//苏州数字(Suzhou digital)@2
		"jiangsutaizhou",		//泰州(jiangsu Taizhou)@2
		"jiangsutaizhoucable",		//泰州有线(jiangsu Taizhou cable)@2
		"wuxi",		//无锡(Wuxi)@2
		"wuxicable",		//无锡有线(Wuxi cable)@2
		"xuzhou",		//徐州(Xuzhou)@2
		"yancheng",		//盐城(Yancheng)@2
		"yangzhou",		//扬州(Yangzhou)@2
		"zhenjiang",		//镇江(Zhenjiang)@2
		"fuzhou",		//抚州(Fuzhou)@2
		"ganzhou",		//赣州(Ganzhou)@2
		"jian",		//吉安(Ji'an)@2
		"jiangxiradioandtelevision",		//江西广电(Jiangxi radio and television)@2
		"jiangxicable",		//江西有线(Jiangxi cable)@2
		"jingdezhen",		//景德镇(Jingdezhen)@2
		"jiujiang",		//九江(Jiujiang)@2
		"nanchang",		//南昌(Nanchang)@2
		"nanchangbroadcasting",		//南昌广电(Nanchang broadcasting)@2
		"pingxiang",		//萍乡(Pingxiang)@2
		"shangrao",		//上饶(Shangrao)@2
		"xinyu",		//新余(Xinyu)@2
		"yichun",		//宜春(Yichun)@2
		"yingtan",		//鹰潭(Yingtan)@2
		"baicheng",		//白城(Baicheng)@2
		"mountbai",		//白山(Mount Bai)@2
		"changchun",		//长春(Changchun)@2
		"jilin",		//吉林(Jilin)@2
		"jilinradioandtvnetwork",		//吉林广电网络(Jilin radio and TV network)@2
		"liaoyuan",		//辽源(Liaoyuan)@2
		"siping",		//四平(Siping)@2
		"songyuan",		//松原(Songyuan)@2
		"tonghua",		//通化(Tonghua)@2
		"anshan",		//鞍山(Anshan)@2
		"benxi",		//本溪(Benxi)@2
		"benxiradioandtelevision",		//本溪广电(Benxi radio and television)@2
		"chaoyang",		//朝阳(Chaoyang)@2
		"dalian",		//大连(Dalian)@2
		"daliandaxian",		//大连大显(Dalian Daxian)@2
		"dalianhaier",		//大连海尔(Dalian Haier)@2
		"dalianhisense",		//大连海信(Dalian Hisense)@2
		"daliankyushu",		//大连九州(Dalian Kyushu)@2
		"dalianthomson",		//大连THOMSON(Dalian THOMSON)@2
		"dandong",		//丹东(Dandong)@2
		"fushun",		//抚顺(Fushun)@2
		"fushunradioandtelevision",		//抚顺广电(Fushun radio and television)@2
		"fuxin",		//阜新(Fuxin)@2
		"huludao",		//葫芦岛(Huludao)@2
		"huludaodigital",		//葫芦岛数字(Huludao digital)@2
		"jinzhou",		//锦州(Jinzhou)@2
		"liaoningradioandtelevision",		//辽宁广电(Liaoning radio and television)@2
		"liaoningchuangjia",		//辽宁创佳(Liaoning Chuangjia)@2
		"liaoyang",		//辽阳(Liaoyang)@2
		"panjin",		//盘锦(Panjin)@2
		"shenyang",		//沈阳(Shenyang)@2
		"shenyangmedianetwork",		//沈阳传媒网络(Shenyang media network)@2
		"tieling",		//铁岭(Tieling)@2
		"tielingskycable",		//铁岭天光有线(Tieling sky cable)@2
		"wafangdiancity",		//瓦房店(Wafangdian City)@2
		"wafangdiancityradioandtelevision",		//瓦房店广电(Wafangdian City radio and television)@2
		"yingkou",		//营口(Yingkou)@2
		"alxaleague",		//阿拉善盟(Alxa League)@2
		"baotou",		//包头(Baotou)@2
		"thebayannaoerleague",		//巴彥淖尔盟(The Bayannaoer League)@2
		"wuyuandigital",		//五原数字(Wuyuan digital)@2
		"chifeng",		//赤峰(Chifeng)@2
		"erdos",		//鄂尔多斯(Erdos)@2
		"hohhot",		//呼和浩特(Hohhot)@2
		"hulunbuir",		//呼伦贝尔(Hulun Buir)@2
		"innermongolia",		//内蒙古(Inner Mongolia)@2
		"innermongoliaradioandtelevision",		//内蒙古广电 (Inner Mongolia radio and television)@2
		"tongliao",		//通辽(Tongliao)@2
		"wuhai",		//乌海(Wuhai)@2
		"thewulanchabuleague",		//乌兰察布盟(The Wulanchabu League)@2
		"xilinguolemeng",		//锡林郭勒盟(Xilinguole Meng)@2
		"xinganmeng",		//兴安盟(Xingan Meng)@2
		"guyuan",		//固原(Guyuan)@2
		"ningxiaradioandtelevision",		//宁夏广电(Ningxia radio and television)@2
		"ningxiaskyworth",		//宁夏创维(Ningxia SKYWORTH)@2
		"shizuishan",		//石嘴山(Shizuishan)@2
		"wuzhong",		//吴忠(Wuzhong)@2
		"yinchuan",		//银川(Yinchuan)@2
		"zhongwei",		//中卫(Zhongwei)@2
		"guoluo",		//果洛(Guoluo)@2
		"haibeistate",		//海北州(Haibei state)@2
		"haidong",		//海东(Haidong)@2
		"haidongprefecture",		//海东州(Haidong Prefecture)@2
		"thestateofhainan",		//海南州(The state of Hainan)@2
		"haixizhou",		//海西州(Hai Xizhou)@2
		"xining",		//西宁(Xining)@2
		"xiningjiulian",		//西宁九联(Xining Jiulian)@2
		"yushu",		//玉树(Yushu)@2
		"binzhou",		//滨州(Binzhou)@2
		"binzhouradioandtelevision",		//滨州广电(Binzhou radio and television)@2
		"texas",		//德州(Texas)@2
		"dongying",		//东营(Dongying)@2
		"dongyingradioandtelevision",		//东营广电(Dongying radio and television)@2
		"heze",		//菏泽(Heze)@2
		"hezecable",		//菏泽有线(Heze cable)@2
		"jinan",		//济南(Ji'nan)@2
		"jinanradioandtelevision",		//济南广电(Ji'nan radio and television)@2
		"jinanhaiera",		//济南海尔一(Ji'nan Haier a)@2
		"jinanhaiertwo",		//济南海尔二(Ji'nan Haier two)@2
		"jinanhaierthree",		//济南海尔三(Ji'nan Haier three)@2
		"jinanhaierfour",		//济南海尔四(Ji'nan Haier four)@2
		"jinanhisensea",		//济南海信一(Ji'nan Hisense a)@2
		"jinanhisensetwo",		//济南海信二(Ji'nan Hisense two)@2
		"jinanhisensethree",		//济南海信三(Ji'nan Hisense three)@2
		"jinantianbo",		//济南天柏(Ji'nan Tianbo)@2
		"jining",		//济宁(Jining)@2
		"laiwu",		//莱芜(Laiwu)@2
		"laiwuradioandtelevision",		//莱芜广电(Laiwu radio and television)@2
		"liaocheng",		//聊城(Liaocheng)@2
		"linyi",		//临沂(Linyi)@2
		"linyihuawei",		//临沂华为(Linyi Huawei)@2
		"qingdao",		//青岛(Qingdao)@2
		"qingdaohuaguang",		//青岛华光(Qingdao Huaguang)@2
		"qingdaokyushu",		//青岛九州(Qingdao Kyushu)@2
		"sunshine",		//日照(Sunshine)@2
		"shandongradioandtelevision",		//山东广电(Shandong radio and television)@2
		"shandongskyworth",		//山东创维(Shandong SKYWORTH)@2
		"shandonghaier",		//山东海尔(Shandong Haier)@2
		"taian",		//泰安(Tai'an)@2
		"taianradioandtelevision",		//泰安广电(Tai'an radio and television)@2
		"weifang",		//潍坊(Weifang)@2
		"anqiucable",		//安丘有线(Anqiu cable)@2
		"weifangradioandtelevision",		//潍坊广电(Weifang radio and television)@2
		"weifanghuaguang",		//潍坊华光(Weifang Huaguang)@2
		"weihai",		//威海(Weihai)@2
		"weihairadioandtelevision",		//威海广电 (Weihai radio and television)@2
		"yantai",		//烟台(Yantai)@2
		"yantaidigitaltv",		//烟台数字电视(Yantai digital TV)@2
		"zaozhuang",		//枣庄(Zaozhuang)@2
		"zaozhuangradioandtelevision",		//枣庄广电(Zaozhuang radio and television)@2
		"zibo",		//淄博(Zibo)@2
		"zibodigitaltv",		//淄博数字电视(Zibo digital TV)@2
		"zibostate",		//淄博同州(Zibo state)@2
		"zibocablea",		//淄博有线一(Zibo cable a)@2
		"zibocabletwo",		//淄博有线二(Zibo cable two)@2
		"zibocablethree",		//淄博有线三(Zibo cable three)@2
		"zibocablefour",		//淄博有线四(Zibo cable four)@2
		"shanghai",		//上海(Shanghai)@2
		"shanghaiorientalcablea",		//上海东方有线一(Shanghai Oriental Cable a)@2
		"shanghaicabletwo",		//上海有线二(Shanghai cable two)@2
		"shanghaidvt-rc-1",		//上海DVT-RC-1(Shanghai DVT-RC-1)@2
		"changzhi",		//长治(Changzhi)@2
		"datong",		//大同(Da Tong)@2
		"datongcable",		//大同有线(Datong cable)@2
		"jincheng",		//晋城(Jincheng)@2
		"jinchengradioandtelevision",		//晋城广电(Jincheng radio and television)@2
		"jinzhong",		//晋中(Jinzhong)@2
		"linfen",		//临汾(Linfen)@2
		"linfencable",		//临汾有线(Linfen cable)@2
		"lvliang",		//吕梁(Lvliang)@2
		"shanxiradioandtelevision",		//山西广电(shanxi radio and television)@2
		"happinessexpress",		//幸福快车(Happiness Express)@2
		"shuozhou",		//朔州(Shuozhou)@2
		"taiyuan",		//太原(Taiyuan)@2
		"taiyuanskyworth",		//太原创维(Taiyuan SKYWORTH)@2
		"taiyuanradioandtelevision",		//太原广电(Taiyuan radio and television)@2
		"taiyuanhuawei",		//太原华为(Taiyuan Huawei)@2
		"taiyuanwave",		//太原浪潮(Taiyuan wave)@2
		"taiyuantyson",		//太原泰森(Taiyuan Tyson)@2
		"taiyuangalaxy",		//太原银河(Taiyuan Galaxy)@2
		"xinzhou",		//忻州(Xinzhou)@2
		"xinzhoucable",		//忻州有线(Xinzhou cable)@2
		"yangquan",		//阳泉(Yangquan)@2
		"yuncheng",		//运城(Yuncheng)@2
		"ankang",		//安康(Ankang)@2
		"baoji",		//宝鸡(Baoji)@2
		"baojiradioandtelevision",		//宝鸡广电(Baoji radio and television)@2
		"hanzhoung",		//汉中(Hanzhoung)@2
		"shangluo",		//商洛(Shangluo)@2
		"shaanxiradioandtelevision",		//陕西广电(Shaanxi radio and television)@2
		"shaanxiaihua",		//陕西爱华(Shaanxi Aihua)@2
		"tongchuan",		//铜川(Tongchuan)@2
		"weinan",		//渭南(Weinan)@2
		"xian",		//西安(Xi'an)@2
		"shaanxidahua",		//陕西大华(Shaanxi Dahua)@2
		"shaanxirailwaysystem",		//陕西铁路系统(Shaanxi railway system)@2
		"xianbbk",		//西安步步高(Xi'an BBK)@2
		"xianhisense",		//西安海信(Xi'an Hisense)@2
		"xianyang",		//咸阳(Xianyang)@2
		"yanan",		//延安(Yanan)@2
		"yananradioandtelevision",		//延安广电(Yanan radio and television)@2
		"yulin",		//榆林(Yulin)@2
		"bazhong",		//巴中(Bazhong)@2
		"chengdu",		//成都(Chengdu)@2
		"chengduconte",		//成都康特(Chengdu conte)@2
		"sichuankyushu",		//四川九州(Sichuan Kyushu)@2
		"dazhou",		//达州(Dazhou)@2
		"deyang",		//德阳(Deyang)@2
		"guangan",		//广安(Guang'an)@2
		"guangyuan",		//广元(Guangyuan)@2
		"leshan",		//乐山(Leshan)@2
		"luzhou",		//泸州(Luzhou)@2
		"meishan",		//眉山(Meishan)@2
		"mianyang",		//绵阳(Mianyang)@2
		"nanchong",		//南充(Nanchong)@2
		"neijiang",		//内江(Neijiang)@2
		"panzhihua",		//攀枝花(Panzhihua)@2
		"sichuanradioandtelevision",		//四川广电(Sichuan Radio and television)@2
		"sichuanchanghong",		//四川长虹(Sichuan Changhong)@2
		"suining",		//遂宁(Suining)@2
		"yaan",		//雅安(Ya'an)@2
		"yibin",		//宜宾(Yibin)@2
		"zigong",		//自贡(Zigong)@2
		"ziyang",		//资阳(Ziyang)@2
		"tianjin",		//天津(Tianjin)@2
		"tianjinbeijingcard",		//天津北京牌(Tianjin Beijing card)@2
		"tianjinchanghong",		//天津长虹(Tianjin Changhong)@2
		"tianjinskyworth",		//天津创维(Tianjin SKYWORTH)@2
		"tianjinradioandtelevisionnetwork",		//天津广电网络(Tianjin Radio and television network)@2
		"hongkongcable",		//香港CABLE(Hongkong CABLE)@2
		"hongkongbroadbandnetwork",		//香港宽频(Hongkong broadband network)@2
		"hongkongnowtv",		//香港NOWTV(Hongkong NOWTV)@2
		"akesu",		//阿克苏(Akesu)@2
		"aletai",		//阿勒泰(Aletai)@2
		"bayanguoluo",		//巴音郭椤(Bayan Guo Luo)@2
		"bortala",		//博尔塔拉(Bortala)@2
		"changji",		//昌吉(Changji)@2
		"hami",		//哈密(Hami)@2
		"andtian",		//和田(And Tian)@2
		"kashi",		//喀什(Kashi)@2
		"karamay",		//克拉玛依(Karamay)@2
		"kizilsu",		//克孜勒苏(Kizilsu)@2
		"shihezi",		//石河子(Shihezi)@2
		"tacheng",		//塔城(Tacheng)@2
		"turpan",		//吐鲁番(Turpan)@2
		"urumqi",		//乌鲁木齐(Urumqi)@2
		"xinjiangradioandtelevision",		//新疆广电(Xinjiang radio and television)@2
		"xinjiangradioandtvone",		//新疆广电一(Xinjiang radio and TV one)@2
		"xinjiangradioandtvtwo",		//新疆广电二(Xinjiang radio and TV two)@2
		"xinjianghuawei",		//新疆华为(Xinjiang Huawei)@2
		"xinjiang",		//新疆(Xinjiang)@2
		"yili",		//伊犁(Yili)@2
		"baoshan",		//保山(Baoshan)@2
		"kunming",		//昆明(Kunming)@2
		"kunminghuawei",		//昆明华为(Kunming Huawei)@2
		"kunmingputian",		//昆明普天(Kunming Putian)@2
		"kunmingtianbo",		//昆明天柏(Kunming Tianbo)@2
		"kunmingyunguangnetwork",		//昆明云广网络一(Kunming Yunguang network)@2
		"lijiang",		//丽江(Lijiang)@2
		"lincang",		//临沧(Lincang)@2
		"qujing",		//曲靖(Qujing)@2
		"qujingradioandtelevision",		//曲靖广电(Qujing radio and television)@2
		"qujingradioandtvtwo",		//曲靖广电二(Qujing radio and TV two)@2
		"simao",		//思茅(Simao)@2
		"yunnanradioandtelevision",		//云南广电(Yunnan radio and television)@2
		"yunnangni",		//云南GNI(Yunnan GNI)@2
		"yunnanhuawei",		//云南华为(Yunnan Huawei)@2
		"yunnandigital",		//云南数字(Yunnan digital)@2
		"yuxi",		//玉溪(Yuxi)@2
		"yuxiradioandtelevision",		//玉溪广电(Yuxi radio and television)@2
		"zhaotong",		//昭通(Zhaotong)@2
		"dongyang",		//东阳(Dongyang)@2
		"dongyangcablea",		//东阳有线一(Dongyang cable a)@2
		"dongyangcabletwo",		//东阳有线二(Dongyang cable two)@2
		"hangzhou",		//杭州(Hangzhou)@2
		"hangzhoucolortechnology",		//杭州彩视科技(Hangzhou color technology)@2
		"hangzhoudahua",		//杭州大华(Hangzhou Dahua)@2
		"hangzhouhuawei",		//杭州华为(Hangzhou Huawei)@2
		"severalsourcesinhangzhou",		//杭州数源(Several sources in Hangzhou)@2
		"huzhou",		//湖州(Huzhou)@2
		"jiashan",		//嘉善(jiashan)@2
		"jiashancablea",		//嘉善有线一(Jiashan cable a)@2
		"soyeacabletwo",		//数源有线二(Soyea cable two)@2
		"jiaxing",		//嘉兴(Jiaxing)@2
		"jiaxingcable",		//嘉兴有线(Jiaxing cable)@2
		"jinhua",		//金华(Jinhua)@2
		"jinhuadahua",		//金华大华(Jin Huadahua)@2
		"kaihua",		//开化(Kaihua)@2
		"kaihuacable",		//开化有线(Kaihua cable)@2
		"linhai",		//临海(Linhai)@2
		"linhaidigital",		//临海数字(Linhai digital)@2
		"lishui",		//丽水(Lishui)@2
		"luqiao",		//路桥(Luqiao)@2
		"ningbo",		//宁波(Ningbo)@2
		"fenghuacable",		//奉化有线(Fenghua cable)@2
		"ningboskyworth",		//宁波创维(Ningbo SKYWORTH)@2
		"ningbodvb",		//宁波DVB(Ningbo DVB)@2
		"ningbohuawei",		//宁波华为(Ningbo Huawei)@2
		"ningbokyushu",		//宁波九州(Ningbo Kyushu)@2
		"ningbostate",		//宁波同州(Ningbo state)@2
		"quzhou",		//衢州(Quzhou)@2
		"shaoxing",		//绍兴(shaoxing)@2
		"shaoxinghuawei",		//绍兴华为(Shaoxing Huawei)@2
		"shaoxingkyushu",		//绍兴九州(Shaoxing Kyushu)@2
		"taizhou",		//台州(Taizhou)@2
		"taizhoucable",		//台州有线(Taizhou cable)@2
		"wenzhou",		//温州(Wenzhou)@2
		"wenzhouhuawei",		//温州华为(Wenzhou Huawei)@2
		"wenzhoufour",		//温州四通(Wenzhou four)@2
		"widelycable",		//中广有线(Widely cable)@2
		"pingyangradioandtelevision",		//平阳广电(Pingyang radio and television)@2
		"yinzhou",		//鄞州(Yinzhou)@2
		"yinzhoucable",		//鄞州有线(Yinzhou cable)@2
		"yiwu",		//义乌(Yiwu)@2
		"yiwuradioandtelevision",		//义乌广电(Yiwu radio and television)@2
		"yongkang",		//永康(Yongkang)@2
		"yongkangcable",		//永康有线(Yongkang cable)@2
		"clouds",		//云和(Clouds)@2
		"cloudsandcabletwo",		//云和有线二(Clouds and cable two)@2
		"acloudandcable",		//云和有线一(A cloud and cable)@2
		"zhejiangradioandtelevision",		//浙江广电(Zhejiang radio and television)@2
		"gianteagletechnology",		//巨鹰科技(Giant Eagle Technology)@2
		"zhoushan",		//舟山(Zhoushan)@2
		"zhuji",		//诸暨(Zhuji)@2
		"guangxiradioandtelevision",		//广西广电(Guangxi radio and television)@2
		"hebeiinnermongolia",		//河北/内蒙古(Hebei Inner Mongolia)@2
		"shandongzibodongguan",		//山东-淄博/东莞(Shandong Zibo Dongguan)@2
		"shandongzibo",		//山东-淄博(Shandong Zibo)@2
		"shandonglinyi",		//山东-临沂(Shandong Linyi)@2
		"foshansouthchinasea",		//佛山/南海(Foshan / South China Sea)@2
		"sichuanwenzhou",		//四川/温州(Sichuan Wenzhou)@2
		"chongqingnanjingningboshenzhen",		//重庆/南京/宁波/深圳(Chongqing Nanjing Ningbo Shenzhen)@2
		"coshipelectronics",		//同洲电子(Coship Electronics)@2
		"ningbozhenhai",		//宁波-镇海(Ningbo Zhenhai)@2
		"hangzhouxinjiangradioandtelevision",		//杭州/新疆广电(Hangzhou / Xinjiang radio and television)@2
		"jinanjiaxing",		//济南/嘉兴(Ji'nan Jiaxing)@2
		"tampa",		//天柏(Tampa)@2
		"dalianqingdao",		//大连/青岛(Dalian Qingdao)@2
		"panasonic",		//松下(Panasonic)@2
		"shaoxingwenzhouningbo",		//绍兴/温州/宁波(Shaoxing Wenzhou Ningbo)@2
		"shaoxingningbo",		//绍兴/宁波(Shaoxing Ningbo)@2
		"huawei",		//华为(HUAWEI)@2
		"dongguanradioandtelevision",		//东莞广电(Dongguan radio and television)@2
		"jiuzhou",		//九洲(Jiuzhou)@2
		"sva",		//上广电(SVA)@2
		"sidakang",		//斯达康(Si Dakang)@2
		"xoceco",		//厦华(Xoceco)@2
		"zhengzhoubeijingtaiyuan",		//郑州/北京/太原(Zhengzhou Beijing Taiyuan)@2
		"goodrecord",		//佳创(Good record)@2
		"changhong",		//长虹(Changhong)@2
		"thenorth",		//北广(The North)@2
		"hunancable",		//湖南有线(Hunan cable)@2
		"rc-14a",		//RC-14A(RC-14A)@2
		"aviationtechnology",		//航科(Aviation Technology)@2
		"lihe",		//力合(Li He)@2
		"yum",		//百胜(Yum)@2
		"rc-8azhuhai",		//RC-8A 珠海(RC-8A Zhuhai)@2
		"beijinggehua",		//北京 歌华(Beijing Gehua)@2
		"hebeiradioandtelevision",		//河北广电(Hebei radio and television)@2
		"wycombevc-9030rc",		//威科姆VC-9030RC(Wycombe VC-9030RC)@2
		"skyworth",		//创维(SKYWORTH)@2
		"jieyangcable",		//揭阳有线(Jieyang cable)@2
		"shandongcable",		//山东有线(Shandong cable)@2
		"bestv",		//BESTV(BESTV)@2
		"ztezte",		//ZTE中兴(ZTE ZTE)@2
		"tianjinradioandtelevision",		//天津广电(Tianjin radio and television)@2
		"yangzhouradioandtelevision",		//扬州广电(Yangzhou radio and television)@2
		"qinghairadioandtelevision",		//青海广电(Qinghai radio and television)@2
		"digitaltv",		//数字电视(Digital TV)@2
		"zhangye",		//张掖广电(zhangye)@2
		"changyuancable",		//长垣有线(Changyuan cable)@2
		"guangdongcable",		//广东有线(Guangdong cable)@2
		"hainanftv",		//海南民视(Hainan FTV)@2
		"huaxiandigital",		//滑县数字(Huaxian digital)@2
		"asianshares",		//金亚股份(Asian shares)@2
		"jingzhouletter",		//荆州视信(Jingzhou letter)@2
		"tangshancable",		//唐山有线(Tangshan cable)@2
		"galaxyelectronics",		//银河电子(Galaxy Electronics)@2
		"harbincable",		//哈尔滨有线(Harbin cable)@2
		"anlucable",		//安陆有线(Anlu cable)@2
		"danriverbroadcasting",		//丹江广电(Dan River broadcasting)@2
		"everyvillage",		//村村通(Every village)@2
		"anweihaier",		//安微海尔(An Wei Haier)@2
		"harbin-know-all",		//哈尔宾-百事通(Harbin-Know-all)@2
		"henancable",		//河南有线(Henan cable)@2
		"sichuanchanghongdigitaltv",		//四川长虹数字电视(Sichuan ChanghongDigitalTV)@2
		"sichuanchanghongandjiuzhou",		//四川长虹长虹和九洲高清(Sichuan Changhong and jiuzhou)@2
		"changhongsatellite",		//长虹卫星(Changhong Satellite)dvb-c8000b1@2
		"sichuanchanghongradioandtelevision",		//四川长虹广电(Sichuan Changhong Radio and television)@2
		"heilongjiangchanghongradioandtelevision",		//黑龙江长虹广电(Heilongjiang Changhong Radio and television)@2
		"hubeiradioandtelevisionchanghong",		//湖北广电长虹(HubeiRadio and television Changhong)@2
		"shandongradioandtelevisionskyworth",		//山东广电创维(ShandongRadio and television SKYWORTH)@2
		"ningxiaradioandtelevisionskyworth",		//泞夏广电创维(Ning XiaRadio and television SKYWORTH)@2
		"sothesourceskyworth",		//太源创维(So the sourceSKYWORTH)@2
		"xiamenskyworth",		//夏门创维(XiamenSKYWORTH)@2
		"xiandahua",		//西安大华(Xi'an Da Hua)@2
		"xiamendahua",		//夏门大华(Xiamen Da Hua)@2
		"shanghaitheeastcabletwo",		//上海东方有线二(Shanghai The East cable two)@2
		"gansuradioandtelevisionstate",		//甘肃广电同州(Gansu Radio and television state)@2
		"gansuradioandtelevisionhuawei",		//甘肃广电华为(Gansu Radio and television HUAWEI)@2
		"gansuradioandtelevisiongciscience&technology",		//甘肃广电杰赛科技(Gansu Radio and television GCI Science & Technology)@2
		"gaussbaer",		//高斯贝尔(Gauss Baer)@2
		"dongguanjiacai",		//东莞佳彩(Dongguan Jia Cai)@2
		"enping",		//恩平(Enping)@2
		"foshanstate",		//佛山同州(Foshan state)@2
		"qingyuanradioandtvstation",		//清远广播电视台(Qingyuan radio and TV station)@2
		"shantoucabletv",		//汕头有线电视(Shantou cable TV)-AR180@2
		"shantouguiyucable",		//汕头贵屿有线(Shantou Guiyu cable)@2
		"shantouchaoyangcable1",		//汕头朝阳有线1(Shantou Chaoyang cable 1)@2
		"chaozhouchaoancable",		//潮州潮安有线(Chaozhou Chaoan cable)@2
		"foshannanhaibroadcasting",		//佛山南海广电(Foshan Nanhai broadcasting)@2
		"guangzhoupearlriverdigital",		//广州珠江数码(Guangzhou Pearl River digital)@2
		"zhuhairadioandtelevision",		//珠海广电(Zhuhai radio and television)@2
		"guizhounetcom",		//贵州金网通(Guizhou Netcom)@2
		"guizhouzunyinetcom",		//贵州遵义金网通(Guizhou Zunyi Netcom)@2
		"guizhouzunyidigital",		//贵州遵义数字(Guizhou Zunyi digital)@2
		"guizhouzunyicounty",		//贵州遵义同州(Guizhou Zunyi County)@2
		"guizhouzunyihuawei",		//贵州遵义华为(Guizhou Zunyi Huawei)@2
		"guizhouzunyijiuzhou",		//贵州遵义九州(Guizhou Zunyi Jiuzhou)@2
		"shandongradioandtelevisionhaier",		//山东广电海尔(Shandong radio and television Haier)@2
		"theseadi",		//海美迪(The sea di)-HD600A@2
		"theseadi",		//海美迪(The sea di)@2
		"henanchanghong",		//河南长虹(Henan Changhong)@2
		"wickhamradio",		//广电威科姆(Wickham radio)@2
		"svagni",		//广电GNI(SVA GNI)@2
		"zhoukouxiangchengcable",		//周口项城有线(Zhoukou Xiangcheng cable)@2
		"sanmenxiacablefour",		//三门峽有线四(Sanmenxia cable four)@2
		"sanmenxiacabletwo",		//三门峽有线二(Sanmenxia cable two)@2
		"sanmenxiacablethree",		//三门峽有线三(Sanmenxia cable three)@2
		"sanmenxiacablea",		//三门峽有线一(Sanmenxia cable a)@2
		"handanradioandtelevision",		//邯郸广电(Handan radio and television)@2
		"shijiazhuangradioandtvnetwork",		//石家庄广电网络(Shijiazhuang radio and TV network)@2
		"iron岒skycable",		//铁岒天光有线(Iron 岒 sky cable)@2
		"yunnanradioandtelevisionhuawei",		//云南广电华为(Yunnan radio and television Huawei)@2
		"linfenhuawei",		//临汾华为(Linfen Huawei)@2
		"jiangsuyangzhou",		//江苏扬州(Jiangsu Yangzhou)@2
		"thebasisoft3",		//杰科T3(The basis of T3)@2
		"jiuliantechnology",		//九联科技(Jiulian Technology)@2
		"qinghaixiningjiulian",		//青海西宁九联(Qinghai  Xining Jiulian)@2
		"chengdukyushu",		//成都九州(Chengdu Kyushu)@2
		"kaibor",		//开博尔(Kai bor)F4F9@2
		"kaibor",		//开博尔(Kai bor)-K360i@2
		"konka",		//康佳(Konka)@2
		"konka",		//康佳(Konka)-SDC25@2
		"asthemusic",		//乐视(As the music)-C21@2
		"radioandtelevisionsuccess",		//广电创佳(Radio and television success)@2
		"dalianthompson",		//大连汤姆逊(Dalian Thompson)@2
		"picture",		//美如画(Picture)-r5@2
		"picture",		//美如画(Picture)-v9@2
		"bayannaoerwuyuannumber",		//巴彦淖尔五原数(Bayannaoer Wuyuan number)@2
		"shandongyangxin",		//山东阳信(Shandong Yangxin)@2
		"yantaidigital",		//烟台数字(Yantai digital)@2
		"pennstateradioandtelevision",		//宾州广电(Penn State Radio and television)@2
		"zibocable",		//淄博有线(Zibo cable)@2
		"zibodigital",		//淄博数字(Zibo digital)@2
		"radioandtelevisionhaier",		//广电海尔(Radio and television Haier)@2
		"aihuabroadcasting",		//爱华广电(Aihua broadcasting)@2
		"xianrailwaysystem",		//西安铁路系统(Xi'an railway system)@2
		"china",		//神州(China)@2
		"shenzhouelectronic",		//神州电子(Shenzhou electronic)@2
		"panasonichd",		//松下高清(Panasonic HD)@2
		"moons",		//天敏(Moons)-LT300@2
		"withtheisland",		//同洲(With the island)-N7700@2
		"coshipgeneral",		//同洲通用(Coship  General)@2
		"withtheisland",		//同洲(With the island)-CDVBC58000@2
		"foshanchau",		//佛山同洲(Foshan Chau)@2
		"coshipchau",		//COSHIP同洲(COSHIP Chau)-N9201@2
		"zibocoship",		//淄博同洲(Zibo Coship)@2
		"ningbocoship",		//宁波同洲(Ningbo Coship)@2
		"hongkong",		//香港(Hongkong)-NOWTV@2
		"xinjianghuaweibroadcasting",		//新疆华为广电(Xinjiang  Huawei broadcasting)@2
		"chutianvideo",		//楚天视讯(Chutian video)@2
		"thegalaxy",		//银河(The galaxy)-DVBC2010CB@2
		"thegalaxy",		//银河(The galaxy)-DVBC2010C@2
		"yunnandigitaltv",		//云南数字电视(Yunnan digital TV)@2
		"yunnanhouseholds",		//云南户户通(Yunnan  Households)@2
		"kunmingcloudnetwork",		//昆明云广网络(Kunming cloud network)@2
		"radioandtelevisiondigital",		//广电数字(Radio and television digital)@2
		"radioandtelevision",		//广电(Radio and television)-GNI@2
		"jiuzhouningbo",		//九洲宁波(Jiuzhou  Ningbo)@2
		"shaoxingjiuzhou",		//绍兴九洲(Shaoxing Jiuzhou)@2
		"wenzhoupingyangradioandtelevision",		//温州平阳广电(Wenzhou Pingyang radio and television)@2
		"wenzhouzhongguangcable",		//温州中广有线(Wenzhou Zhongguang cable)@2
		"zhejiangbroadcastinggianteagletechnology",		//浙江广电巨鹰科技(Zhejiang  broadcasting Giant Eagle Technology)@2
		"ningbofenghuacable",		//宁波奉化有线(Ningbo Fenghua cable)@2
		"jiashancabletwo",		//嘉善有线二(Jiashan cable  two)@2
		"jiashancable",		//嘉善有线(Jiashan cable)@2
		"sixsatellitereceiver",		//中六卫星接收机(Six satellite receiver)@2
		"chinatelecomihaveehome"		//中国电信我有E家(China Telecom I have E home)@2
	};
	
	public final static Map<String , Set<String>> combineProductor = new HashMap<String , Set<String> >();
	
	static
	{
		regestCombileProductor("Beijing" , new String[]{"ZhengzhouBeijingTaiyuan",
														"BeijingGehua",
														"Chaoyangcable",
														"Gehua",
														"BeijingGehuacableHD",
														"GehuaSTB"});
		regestCombileProductor("Guangzhou" , new String[]{"Guangzhoucable",
				"Pearldigital",
				"GuangzhouPearlRiverdigital"});
		regestCombileProductor("Shenzhen" , new String[]{"ShenzhenPrint-RiteSD",
				"ShenzhenPrint-RiteHD",
				"ShenzhenPrint-Rite",
				"TianweiSTB",
				"TOPWAY",
				"ChongqingNanjingNingboShenzhen"});
		regestCombileProductor("Dongguan" , new String[]{"DongguanradioandTVone",
				"DongguanradioandTVtwo",
				"ShandongZiboDongguan",
				"Dongguanradioandtelevision",
				"DongguanJiaCai"});
		regestCombileProductor("Foshan" , new String[]{"FoshanTsinghuaTongfang",
				"WithaFoshan",
				"Foshanwithtwo",
				"Foshancable",
				"FoshanSouthChinaSea",
				"Foshanstate","FoshanNanhaibroadcasting","FoshanChau"});
		regestCombileProductor("Huizhou" , new String[]{"Huizhoucable"});
		regestCombileProductor("Heyuan" , new String[]{"Heyuanradioandtelevision","HeyuanJiaCai"});
		regestCombileProductor("Jiangmen" , new String[]{"Jiangmenradioandtelevision",});
		regestCombileProductor("Jieyang" , new String[]{"Jieyangcable"});
		regestCombileProductor("Maoming" , new String[]{});
		regestCombileProductor("Meizhou" , new String[]{"Meizhoucable"});
		regestCombileProductor("TheSouthChinaSea" , new String[]{"Nanhaibroadcasting",
				"FoshanSouthChinaSea"});
		regestCombileProductor("Puning" , new String[]{"Puningcable"});
		regestCombileProductor("Qingyuan" , new String[]{"Qingyuanradioandtelevision",
				"QingyuanradioandTVstation"});
		regestCombileProductor("Shaoguan" , new String[]{});
		regestCombileProductor("Yangjiang" , new String[]{});
		regestCombileProductor("Yunfu" , new String[]{});
		regestCombileProductor("Zengcheng" , new String[]{"Zengchengcable"});
		regestCombileProductor("Zhanjiang" , new String[]{"Zhanjiangradioandtelevision"});
		regestCombileProductor("Zhaoqing" , new String[]{});
		regestCombileProductor("Zhongshan" , new String[]{"Zhongshancable"});
		regestCombileProductor("Zhuhai" , new String[]{"Zhuhairadioandtelevision",
				"ZhuhairadioandTVone",
				"ZhuhairadioandTVtwo",
				"Zhuhainetwork",
				"Zhuhaicable"});
		regestCombileProductor("Chaozhou" , new String[]{"ChaozhouChaoancable","Chaoancable"});
		regestCombileProductor("Enping" , new String[]{});
		regestCombileProductor("shantou" , new String[]{"ShantoucableTV",
				"ShantouGuiyucable",
				"ShantouChaoyangcable1",
				"Guiyucable",
				"Theimmortalhead",
				"Fairytail"});
		regestCombileProductor("Guangdong" , new String[]{"Guangdongcable"});
		regestCombileProductor("Nanning" , new String[]{});
		regestCombileProductor("TheNorthSea" , new String[]{});
		regestCombileProductor("Guilin" , new String[]{});
		regestCombileProductor("PortofFangcheng" , new String[]{});
		regestCombileProductor("Qinzhou" , new String[]{});
		regestCombileProductor("Baise" , new String[]{});
		regestCombileProductor("Chongzuo" , new String[]{});
		regestCombileProductor("Guigang" , new String[]{});
		regestCombileProductor("Hechi" , new String[]{});
		regestCombileProductor("Hezhou" , new String[]{});
		regestCombileProductor("Guest" , new String[]{});
		regestCombileProductor("Liuzhou" , new String[]{});
		regestCombileProductor("guangxiYulin" , new String[]{});
		regestCombileProductor("Wuzhou" , new String[]{});
		regestCombileProductor("PennStateRadioandtelevision" , new String[]{});
		regestCombileProductor("Guangxi" , new String[]{"GuangxiradioandTVnetwork",
				"Guangxiradioandtelevision"});
		regestCombileProductor("Shanghai" , new String[]{"ShanghaiOrientalCable",
				"ShanghaiOrientalCablea",
				"Shanghaicabletwo",
				"ShanghaiDVT-RC-1",
				"ShanghaiTheEastcabletwo"});
		regestCombileProductor("Tianjin" , new String[]{"Tianjin2",
				"TianjinBeijingcard",
				"TianjinChanghong",
				"TianjinSKYWORTH",
				"TianjinRadioandtelevisionnetwork",
				"Tianjinradioandtelevision"});
		regestCombileProductor("Chongqing" , new String[]{"ChongqingChanghong",
				"ChongqingYinke",
				"Chongqingcable",
				"Chongqingcabletwo",
				"ChongqingNanjingNingboShenzhen"});
		regestCombileProductor("Shenyang" , new String[]{"Shenyangmedianetwork"});
		regestCombileProductor("Dalian" , new String[]{"DalianDaxian",
				"DalianHaier",
				"DalianHisense",
				"DalianKyushu",
				"DalianTHOMSON",
				"DalianQingdao","DalianThompson"});
		regestCombileProductor("Dandong" , new String[]{});
		regestCombileProductor("Fushun" , new String[]{"Fushunradioandtelevision"});
		regestCombileProductor("Anshan" , new String[]{});
		regestCombileProductor("Benxi" , new String[]{"Benxiradioandtelevision"});
		regestCombileProductor("Jinzhou" , new String[]{});
		regestCombileProductor("Tieling" , new String[]{"Tielingskycable"});
		regestCombileProductor("WafangdianCity" , new String[]{"WafangdianCityradioandtelevision"});
		regestCombileProductor("Yingkou" , new String[]{});
		regestCombileProductor("Fuxin" , new String[]{});
		regestCombileProductor("Huludao" , new String[]{"Huludaodigital"});
		regestCombileProductor("Chaoyang" , new String[]{});
		regestCombileProductor("Panjin" , new String[]{});
		regestCombileProductor("Liaoyang" , new String[]{});
		regestCombileProductor("LIAONING" , new String[]{"Liaoningradioandtelevision",
				"LiaoningChuangjia"});
		regestCombileProductor("Baicheng" , new String[]{});
		regestCombileProductor("MountBai" , new String[]{});
		regestCombileProductor("Changchun" , new String[]{});
		regestCombileProductor("Liaoyuan" , new String[]{});
		regestCombileProductor("Siping" , new String[]{});
		regestCombileProductor("Songyuan" , new String[]{});
		regestCombileProductor("Tonghua" , new String[]{});
 		regestCombileProductor("Jilin" , new String[]{"Jilin2",
				"JilinradioandTVnetwork"});
		regestCombileProductor("Nanjing" , new String[]{"ChongqingNanjingNingboShenzhen"});
		regestCombileProductor("jiangsuSuzhou" , new String[]{"Suzhoudigital" });
		regestCombileProductor("Lianyungang" , new String[]{ });
		regestCombileProductor("Wuxi" , new String[]{"Wuxicable" });
		regestCombileProductor("Xuzhou" , new String[]{ });
		regestCombileProductor("Yancheng" , new String[]{ });
		regestCombileProductor("Yangzhou" , new String[]{"Yangzhouradioandtelevision",
				"JiangsuYangzhou" });
		regestCombileProductor("Zhenjiang" , new String[]{ });
		regestCombileProductor("Huaian" , new String[]{ });
		regestCombileProductor("Jiangyin" , new String[]{"Jiangyincable" });
		regestCombileProductor("Nantong" , new String[]{ });
		regestCombileProductor("Suqian" , new String[]{"Suqiancable" });
		regestCombileProductor("jiangsuTaizhou" , new String[]{"jiangsuTaizhoucable" });
		regestCombileProductor("Changzhou" , new String[]{"Changzhoucable" });
		regestCombileProductor("Haimen" , new String[]{"HaimenIsland" });
		regestCombileProductor("JIANGSU" , new String[]{"Jiangsuinteraction" });
		regestCombileProductor("Wuhan" , new String[]{"WuhanSKYWORTH",
				"WuhanHuawei" });
		regestCombileProductor("Huanggang" , new String[]{});
		regestCombileProductor("Huangshi" , new String[]{});
		regestCombileProductor("Jingmen" , new String[]{});
		regestCombileProductor("Shiyan" , new String[]{});
		regestCombileProductor("Suizhou" , new String[]{});
		regestCombileProductor("xiangfan" , new String[]{});
		regestCombileProductor("Xiaogan" , new String[]{});
		regestCombileProductor("XuanChang" , new String[]{});
		regestCombileProductor("Ezhou" , new String[]{});
		regestCombileProductor("DanRiverbroadcasting" , new String[]{});
		regestCombileProductor("Anlucable" , new String[]{});
		regestCombileProductor("Jingzhou" , new String[]{"Jingzhouletter" });
		regestCombileProductor("Xianning" , new String[]{"Xianningdigital" });
		regestCombileProductor("Hubei" , new String[]{"Hubeiradioandtelevision",
				"HubeiChanghong",
				"HubeiRadioandtelevisionChanghong",
				"Chutianvideo" });
		regestCombileProductor("Chengdu" , new String[]{"Chengduconte",
				"ChengduKyushu" });
		regestCombileProductor("Dazhou" , new String[]{ });
		regestCombileProductor("Deyang" , new String[]{ });
		regestCombileProductor("Guangan" , new String[]{ });
		regestCombileProductor("Guangyuan" , new String[]{ });
		regestCombileProductor("Leshan" , new String[]{ });
		regestCombileProductor("Luzhou" , new String[]{ });
		regestCombileProductor("Meishan" , new String[]{ });
		regestCombileProductor("Mianyang" , new String[]{ });
		regestCombileProductor("Nanchong" , new String[]{ });
		regestCombileProductor("Neijiang" , new String[]{ });
		regestCombileProductor("Panzhihua" , new String[]{ });
		regestCombileProductor("Bazhong" , new String[]{ });
		regestCombileProductor("Suining" , new String[]{ });
		regestCombileProductor("Yaan" , new String[]{ });
		regestCombileProductor("Yibin" , new String[]{ });
		regestCombileProductor("Zigong" , new String[]{ });
		regestCombileProductor("Ziyang" , new String[]{ });
 		regestCombileProductor("Sichuan" , new String[]{"SichuanKyushu",
				"SichuanRadioandtelevision",
				"SichuanChanghong",
				"SichuanWenzhou",
				"SichuanChanghongDigitalTV",
				"SichuanChanghongandjiuzhou","SichuanChanghongRadioandtelevision"});
		regestCombileProductor("Xian" , new String[]{"XianBBK",
				"XianHisense",
				"XianDaHua",
				"Xianrailwaysystem" });
		regestCombileProductor("Baoji" , new String[]{"Baojiradioandtelevision" });
		regestCombileProductor("Xianyang" , new String[]{ });
		regestCombileProductor("Yanan" , new String[]{"Yananradioandtelevision" });
		regestCombileProductor("Yulin" , new String[]{ });
		regestCombileProductor("Ankang" , new String[]{ });
		regestCombileProductor("Hanzhoung" , new String[]{ });
		regestCombileProductor("Shangluo" , new String[]{ });
		regestCombileProductor("Tongchuan" , new String[]{ });
		regestCombileProductor("Weinan" , new String[]{ });
		regestCombileProductor("Sichuan" , new String[]{"Shaanxiradioandtelevision",
				"ShaanxiAihua",
				"ShaanxiDahua",
				"Shaanxirailwaysystem" });
		regestCombileProductor("Hangzhou" , new String[]{"Hangzhoucolortechnology",
				"HangzhouDahua",
				"HangzhouHuawei",
				"SeveralsourcesinHangzhou",
				"HangzhouXinjiangradioandtelevision" });
		regestCombileProductor("Jiaxing" , new String[]{"Jiaxingcable",
				"JinanJiaxing" });
		regestCombileProductor("Jinhua" , new String[]{"JinHuadahua" });
		regestCombileProductor("Ningbo" , new String[]{"NingboSKYWORTH",
				"NingboDVB",
				"NingboHuawei",
				"NingboKyushu",
				"Ningbostate",
				"ChongqingNanjingNingboShenzhen",
				"NingboZhenhai",
				"ShaoxingWenzhouNingbo",
				"ShaoxingNingbo",
				"NingboCoship",
				"JiuzhouNingbo",
				"NingboFenghuacable",
				});
		regestCombileProductor("shaoxing" , new String[]{"ShaoxingHuawei",
				"ShaoxingKyushu",
				"ShaoxingWenzhouNingbo",
				"ShaoxingNingbo",
				"ShaoxingJiuzhou" });
		regestCombileProductor("Huzhou" , new String[]{ });
		regestCombileProductor("jiashan" , new String[]{"Jiashancablea",
				"Jiashancabletwo",
				"Jiashancable",
				"Soyeacabletwo" });
		regestCombileProductor("Taizhou" , new String[]{"Taizhoucable" });
		regestCombileProductor("Wenzhou" , new String[]{"WenzhouHuawei",
				"Wenzhoufour",
				"SichuanWenzhou",
				"ShaoxingWenzhouNingbo",
				"WenzhouPingyangradioandtelevision",
				"WenzhouZhongguangcable"});
		regestCombileProductor("Yiwu" , new String[]{"Yiwuradioandtelevision"});
		regestCombileProductor("Zhoushan" , new String[]{ });
		regestCombileProductor("Kaihua" , new String[]{"Kaihuacable"});
		regestCombileProductor("Linhai" , new String[]{"Linhaidigital"});
		regestCombileProductor("Luqiao" , new String[]{});
		regestCombileProductor("Quzhou" , new String[]{});
		regestCombileProductor("Yinzhou" , new String[]{"Yinzhoucable"});
		regestCombileProductor("Yongkang" , new String[]{"Yongkangcable"});
		regestCombileProductor("Clouds" , new String[]{"Cloudsandcabletwo",
				"Acloudandcable"});
		regestCombileProductor("Zhuji" , new String[]{ });
		regestCombileProductor("Dongyang" , new String[]{"Dongyangcablea",
				"Dongyangcabletwo"});
		regestCombileProductor("Pingyangradioandtelevision" , new String[]{ });
		regestCombileProductor("Fenghuacable" , new String[]{ });
		regestCombileProductor("Zhejiang" , new String[]{"Zhejiangradioandtelevision",
				"ZhejiangbroadcastingGiantEagleTechnology" });
		regestCombileProductor("Changsha" , new String[]{"Changshabroadcasting" });
		regestCombileProductor("Yueyang" , new String[]{ });
		regestCombileProductor("Loudi" , new String[]{ });
		regestCombileProductor("Shaoyang" , new String[]{ });
		regestCombileProductor("Xiangtan" , new String[]{ });
		regestCombileProductor("Yiyang" , new String[]{ });
		regestCombileProductor("yongzhou" , new String[]{ });
		regestCombileProductor("Changde" , new String[]{ });
		regestCombileProductor("Huaihua" , new String[]{ });
		regestCombileProductor("Zhangjiajie" , new String[]{ });
		regestCombileProductor("Zhuzhou" , new String[]{ });
		regestCombileProductor("Chenzhou" , new String[]{"Chenzhoucable" });
		regestCombileProductor("Hengyang" , new String[]{"Hengyangcable" });
		regestCombileProductor("Hunan" , new String[]{"Hunanradioandtelevision",
				"Hunancable" });
		regestCombileProductor("Jinan" , new String[]{"Jinanradioandtelevision",
				"JinanHaiera",
				"JinanHaiertwo",
				"JinanHaierthree",
				"JinanHaierfour",
				"JinanHisensea","JinanHisensetwo","JinanHisensethree","JinanTianbo","JinanJiaxing"});
		regestCombileProductor("Qingdao" , new String[]{"QingdaoHuaguang",
				"QingdaoKyushu",
				"DalianQingdao" });
		regestCombileProductor("Yantai" , new String[]{"YantaidigitalTV",
				"Yantaidigital" });
		regestCombileProductor("Zaozhuang" , new String[]{"Zaozhuangradioandtelevision" });
		regestCombileProductor("Binzhou" , new String[]{"Binzhouradioandtelevision" });
		regestCombileProductor("Texas" , new String[]{ });
		regestCombileProductor("Dongying" , new String[]{"Dongyingradioandtelevision" });
		regestCombileProductor("Heze" , new String[]{"Hezecable" });
		regestCombileProductor("Jining" , new String[]{ });
		regestCombileProductor("Laiwu" , new String[]{"Laiwuradioandtelevision" });
		regestCombileProductor("Liaocheng" , new String[]{ });
		regestCombileProductor("Linyi" , new String[]{"LinyiHuawei",
				"ShandongLinyi" });
		regestCombileProductor("Sunshine" , new String[]{ });
		regestCombileProductor("Taian" , new String[]{"Taianradioandtelevision" });
		regestCombileProductor("Weifang" , new String[]{"Weifangradioandtelevision",
				"WeifangHuaguang" });
		regestCombileProductor("Weihai" , new String[]{"Weihairadioandtelevision" });
		regestCombileProductor("Zibo" , new String[]{"ZibodigitalTV",
				"Zibostate",
				"Zibocablea",
				"Zibocabletwo",
				"Zibocablethree",
				"Zibocablefour",
				"ShandongZiboDongguan",
				"ShandongZibo",
				"Zibocable",
				"Zibodigital",
				"ZiboCoship",});
		regestCombileProductor("Anqiucable" , new String[]{ });
		regestCombileProductor("Shandong" , new String[]{"Shandongradioandtelevision",
				"ShandongSKYWORTH",
				"ShandongHaier",
				"Shandongcable",
				"ShandongRadioandtelevisionSKYWORTH",
				"ShandongradioandtelevisionHaier"});
		regestCombileProductor("Hefei" , new String[]{"Hefeicable" });
		regestCombileProductor("MountHuangshan" , new String[]{ });
		regestCombileProductor("Luan" , new String[]{"Luandigital" });
		regestCombileProductor("Maanshan" , new String[]{ });
		regestCombileProductor("Suzhou" , new String[]{"Suzhouradioandtelevision" });
		regestCombileProductor("Tongling" , new String[]{"Tonglingradioandtelevision" });
		regestCombileProductor("Wuhu" , new String[]{ });
		regestCombileProductor("Xuancheng" , new String[]{"Thatradioandtelevision" });
		regestCombileProductor("Anqing" , new String[]{ });
		regestCombileProductor("FrogBu" , new String[]{ });
		regestCombileProductor("Chizhou" , new String[]{"Chizhouradioandtelevision" });
		regestCombileProductor("Chuzhou" , new String[]{ });
		regestCombileProductor("Fuyang" , new String[]{ });
		regestCombileProductor("Bozhou" , new String[]{ });
		regestCombileProductor("Huaibei" , new String[]{ });
		regestCombileProductor("Huainan" , new String[]{"Huainanradioandtelevision" });
		regestCombileProductor("Anwei" , new String[]{"Anhuiradio",
				"AnhuiHaier",
				"Anextensivenetwork" });
		regestCombileProductor("Guiyang" , new String[]{ });
		regestCombileProductor("Zunyi" , new String[]{"Zunyiradioandtelevision",
				"ZunyiHuawei",
				"ZunyiNetcom",
				"ZunyiKyushu",
				"Zunyidigital",
				"Zunyistate",
				"GuizhouZunyiNetcom",
				"GuizhouZunyidigital",
				"GuizhouZunyiCounty",
				"GuizhouZunyiHuawei",
				"GuizhouZunyiJiuzhou",});
		regestCombileProductor("Anshun" , new String[]{ });
		regestCombileProductor("Bijie" , new String[]{ });
		regestCombileProductor("Liupanshui" , new String[]{ });
		regestCombileProductor("Tongren" , new String[]{ });
		regestCombileProductor("Guizhou" , new String[]{"Guizhouradioandtelevision",
				"GuizhouNetcom" });
		regestCombileProductor("Harbin" , new String[]{"Harbinknow-all",
				"HarbinSidakang",
				"Harbincablea",
				"Harbincabletwo",
				"Harbincablethree",
				"Harbincable","Harbin-Know-all"});
		regestCombileProductor("Daqing" , new String[]{"Daqingcable" });
		regestCombileProductor("Qigihar" , new String[]{"Qigiharcable" });
		regestCombileProductor("Hegang" , new String[]{ });
		regestCombileProductor("Heihe" , new String[]{"Heiheradioandtelevision" });
		regestCombileProductor("Jiamusi" , new String[]{ });
		regestCombileProductor("Jixi" , new String[]{ });
		regestCombileProductor("Mudanjiang" , new String[]{"Mudanjiangcable" });
		regestCombileProductor("Qitaihe" , new String[]{ });
		regestCombileProductor("Shuangyashan" , new String[]{ });
		regestCombileProductor("Suihua" , new String[]{"Suihuacable" });
		regestCombileProductor("Tailai" , new String[]{"Tailaicable" });
		regestCombileProductor("heilongjiangYichun" , new String[]{ });
		regestCombileProductor("Heilongjiang" , new String[]{"BlackLongjiangbroadcasting",
				"BlackLongjiangChanghong",
				"Heilongjiangstatefarms",
				"Farmingcable",
				"HeilongjiangChanghongRadioandtelevision" });
		regestCombileProductor("Taiyuan" , new String[]{"TaiyuanSKYWORTH",
				"Taiyuanradioandtelevision",
				"TaiyuanHuawei",
				"Taiyuanwave",
				"TaiyuanTyson",
				"TaiyuanGalaxy","ZhengzhouBeijingTaiyuan","SothesourceSKYWORTH"});
		regestCombileProductor("DaTong" , new String[]{"Datongcable"});
		regestCombileProductor("Jincheng" , new String[]{"Jinchengradioandtelevision" });
		regestCombileProductor("Jinzhong" , new String[]{ });
		regestCombileProductor("Linfen" , new String[]{"Linfencable",
				"LinfenHuawei" });
		regestCombileProductor("Lvliang" , new String[]{ });
		regestCombileProductor("Changzhi" , new String[]{ });
		regestCombileProductor("Shuozhou" , new String[]{ });
		regestCombileProductor("Xinzhou" , new String[]{"Xinzhoucable" });
		regestCombileProductor("Yangquan" , new String[]{ });
		regestCombileProductor("Yuncheng" , new String[]{ });
		regestCombileProductor("Shanxi" , new String[]{"shanxiradioandtelevision" });
		regestCombileProductor("Kunming" , new String[]{"KunmingHuawei",
				"KunmingPutian",
				"KunmingTianbo",
				"KunmingYunguangnetwork",
				"Kunmingcloudnetwork" });
		regestCombileProductor("Lijiang" , new String[]{ });
		regestCombileProductor("Lincang" , new String[]{ });
		regestCombileProductor("Qujing" , new String[]{"Qujingradioandtelevision",
				"QujingradioandTVtwo" });
		regestCombileProductor("Baoshan" , new String[]{ });
		regestCombileProductor("Simao" , new String[]{ });
		regestCombileProductor("Yuxi" , new String[]{"Yuxiradioandtelevision" });
		regestCombileProductor("Zhaotong" , new String[]{ });
		regestCombileProductor("Yunnan" , new String[]{"Yunnanradioandtelevision",
				"YunnanGNI",
				"YunnanHuawei",
				"Yunnandigital",
				"YunnanradioandtelevisionHuawei",
				"YunnandigitalTV","YunnanHouseholds"});
		regestCombileProductor("Zhengzhou" , new String[]{"ZhengzhouHuilong",
				"ZhengzhouMotorola",
				"ZhengzhouGalaxy",
				"ZhengzhouBeijingTaiyuan" });
		regestCombileProductor("Anyang" , new String[]{"Anyangcable" });
		regestCombileProductor("Kaifeng" , new String[]{"Kaifengradioandtelevision" });
		regestCombileProductor("Zhumadian" , new String[]{ });
		regestCombileProductor("Hebi" , new String[]{ });
		regestCombileProductor("Jiaozuo" , new String[]{ });
		regestCombileProductor("Jiyuan" , new String[]{ });
		regestCombileProductor("Luohe" , new String[]{ });
		regestCombileProductor("Luoyang" , new String[]{ });
		regestCombileProductor("Nanyang" , new String[]{ });
		regestCombileProductor("Pingdingshan" , new String[]{ });
		regestCombileProductor("Puyang" , new String[]{ });
		regestCombileProductor("Shangqiu" , new String[]{"Shangqiuradioandtelevision" });
		regestCombileProductor("Huaxiandigital" , new String[]{ });
		regestCombileProductor("Sanmenxia" , new String[]{"Sanmenxiacablea",
				"Sanmenxiacabletwo",
				"Sanmenxiacablethree",
				"Sanmenxiacablefour" });
		regestCombileProductor("Xiangcheng" , new String[]{"Xiangchengcable" });
		regestCombileProductor("Xinxiang" , new String[]{ });
		regestCombileProductor("Xinyang" , new String[]{"ShandongYangxin" });
		regestCombileProductor("Xuchang" , new String[]{ });
		regestCombileProductor("Zhoukou" , new String[]{"ZhoukouXiangchengcable" });
		regestCombileProductor("Yongchengcable" , new String[]{ });
		regestCombileProductor("Changyuancable" , new String[]{ });
		regestCombileProductor("Henan" , new String[]{"Henanradioandtelevision",
				"HenanGNI",
				"HenanVCOM",
				"Henancable",
				"HenanChanghong" });
		regestCombileProductor("Haikou" , new String[]{ });
		regestCombileProductor("Sanya" , new String[]{ });
		regestCombileProductor("Hainan" , new String[]{"Hainancable",
				"Hainanradioandtelevision",
				"HainanFTV" });
		regestCombileProductor("Shijiazhuang" , new String[]{"ShijiazhuangradioandTVnetwork" });
		regestCombileProductor("Baoding" , new String[]{ });
		regestCombileProductor("Chengde" , new String[]{"Chengderadioandtelevision" });
		regestCombileProductor("Handan" , new String[]{"Handanradioandtelevision" });
		regestCombileProductor("Hengshui" , new String[]{ });
		regestCombileProductor("YongFang" , new String[]{ });
		regestCombileProductor("Qinghuangdao" , new String[]{ });
		regestCombileProductor("Tangshan" , new String[]{"Tangshancable" });
		regestCombileProductor("Xingtai" , new String[]{ });
		regestCombileProductor("Zhangjiakou" , new String[]{ });
		regestCombileProductor("Cangzhou" , new String[]{"Cangzhoucable" });
		regestCombileProductor("Botou" , new String[]{"Botoucablea",
				"Botoucabletwo" });
		regestCombileProductor("Hebei" , new String[]{"HebeiradioandTVnetwork",
				"HebeiInnerMongolia",
				"Hebeiradioandtelevision" });
		regestCombileProductor("Xiamen" , new String[]{"XiamenSKYWORTH",
				"XiamenDahua",
				"XiamenTsinghuaTongfang",
				"XiamenSKYWORTH2",
				"XiamenDaHua"});
		regestCombileProductor("Fuzhouradioandtelevision" , new String[]{ });
		regestCombileProductor("Longyan" , new String[]{"Longyancable" });
		regestCombileProductor("Nanping" , new String[]{"Nanpingcable" });
		regestCombileProductor("Ningde" , new String[]{"Ningdecable" });
		regestCombileProductor("PuTian" , new String[]{"PuTiancable" });
		regestCombileProductor("Quanzhou" , new String[]{"Quanzhouradioandtelevision" });
		regestCombileProductor("Sanming" , new String[]{"Sanmingbroadcast" });
		regestCombileProductor("Zhangzhou" , new String[]{"Zhangzhouradioandtelevision" });
		regestCombileProductor("Fujian" , new String[]{"Fujianradioandtelevision" });
		regestCombileProductor("Urumqi" , new String[]{ });
		regestCombileProductor("Akesu" , new String[]{ });
		regestCombileProductor("Aletai" , new String[]{ });
		regestCombileProductor("BayanGuoLuo" , new String[]{ });
		regestCombileProductor("Bortala" , new String[]{ });
		regestCombileProductor("Changji" , new String[]{ });
		regestCombileProductor("Hami" , new String[]{ });
		regestCombileProductor("AndTian" , new String[]{ });
		regestCombileProductor("Kashi" , new String[]{ });
		regestCombileProductor("Karamay" , new String[]{ });
		regestCombileProductor("Kizilsu" , new String[]{ });
		regestCombileProductor("Shihezi" , new String[]{ });
		regestCombileProductor("Tacheng" , new String[]{ });
		regestCombileProductor("Turpan" , new String[]{ });
		regestCombileProductor("Yili" , new String[]{ });
		regestCombileProductor("Xinjiang" , new String[]{"Xinjiangradioandtelevision",
				"XinjiangradioandTVone",
				"XinjiangradioandTVtwo",
				"XinjiangHuawei",
				"Xinjiang2",
				"HangzhouXinjiangradioandtelevision","XinjiangHuaweibroadcasting"});
		regestCombileProductor("Nanchang" , new String[]{"Nanchangbroadcasting" });
		regestCombileProductor("Fuzhou" , new String[]{ });
		regestCombileProductor("Ganzhou" , new String[]{ });
		regestCombileProductor("Jian" , new String[]{ });
		regestCombileProductor("Jingdezhen" , new String[]{ });
		regestCombileProductor("Jiujiang" , new String[]{ });
		regestCombileProductor("Pingxiang" , new String[]{ });
		regestCombileProductor("Shangrao" , new String[]{ });
		regestCombileProductor("Xinyu" , new String[]{ });
		regestCombileProductor("Yichun" , new String[]{ });
		regestCombileProductor("Yingtan" , new String[]{ });
		regestCombileProductor("Jiangxi" , new String[]{"Jiangxiradioandtelevision",
				"Jiangxicable" });
		regestCombileProductor("Lanzhou" , new String[]{"Lanzhoupetrochemical" });
		regestCombileProductor("Silver" , new String[]{ });
		regestCombileProductor("Dingxi" , new String[]{ });
		regestCombileProductor("Jiayuguan" , new String[]{ });
		regestCombileProductor("Jinchang" , new String[]{ });
		regestCombileProductor("Jiuquan" , new String[]{ });
		regestCombileProductor("Longnan" , new String[]{ });
		regestCombileProductor("Pingliang" , new String[]{ });
		regestCombileProductor("Qingyang" , new String[]{ });
		regestCombileProductor("Tianshui" , new String[]{ });
		regestCombileProductor("Wuwei" , new String[]{ });
		regestCombileProductor("Folkmusic" , new String[]{"MusicRadio"});
		regestCombileProductor("Zhangye" , new String[]{"zhangye" });
		regestCombileProductor("Gansu" , new String[]{"Gansuradioandtelevision",
				"GansuHuawei",
				"GansuJiesaiTechnology",
				"GansustateCOSHIPS58",
				"Gansucable",
				"GansuRadioandtelevisionstate",
				"GansuRadioandtelevisionHUAWEI",
				"GansuRadioandtelevisionGCIScience&Technology"});
		regestCombileProductor("HongKong" , new String[]{"HongkongCABLE",
				"Hongkongbroadbandnetwork",
				"HongkongNOWTV",
				"Hongkong"});
		regestCombileProductor("Hohhot" , new String[]{});
		regestCombileProductor("AlxaLeague" , new String[]{});
		regestCombileProductor("Baotou" , new String[]{});
		regestCombileProductor("Wuyuandigital" , new String[]{});
		regestCombileProductor("Chifeng" , new String[]{});
		regestCombileProductor("Erdos" , new String[]{});
		regestCombileProductor("HulunBuir" , new String[]{});
		regestCombileProductor("Tongliao" , new String[]{});
		regestCombileProductor("Wuhai" , new String[]{});
		regestCombileProductor("TheWulanchabuLeague" , new String[]{});
		regestCombileProductor("XilinguoleMeng" , new String[]{});
		regestCombileProductor("XinganMeng" , new String[]{});
		regestCombileProductor("InnerMongolia" , new String[]{"InnerMongoliaradioandtelevision",
				"InnerMongolia3",
				"HebeiInnerMongolia"});
		regestCombileProductor("Yinchuan" , new String[]{});
		regestCombileProductor("Zhongwei" , new String[]{});
		regestCombileProductor("Guyuan" , new String[]{});
		regestCombileProductor("Shizuishan" , new String[]{});
		regestCombileProductor("Wuzhong" , new String[]{});
		regestCombileProductor("Ningxiaradioandtelevision" , new String[]{"NingxiaSKYWORTH",
				"NingXiaRadioandtelevisionSKYWORTH" });
		regestCombileProductor("Guoluo" , new String[]{});
		regestCombileProductor("Haibeistate" , new String[]{});
		regestCombileProductor("Haidong" , new String[]{});
		regestCombileProductor("HaidongPrefecture" , new String[]{});
		regestCombileProductor("ThestateofHainan" , new String[]{});
		regestCombileProductor("HaiXizhou" , new String[]{});
		regestCombileProductor("Yushu" , new String[]{});
		regestCombileProductor("Qinghairadioandtelevision" , new String[]{});
		regestCombileProductor("Xining" , new String[]{"XiningJiulian",
				"QinghaiXiningJiulian"});
		regestCombileProductor("Otherregions" , new String[]{});
		regestCombileProductor("Coship" , new String[]{"Coshipstb",
				"CoshipElectronics",
				"Withtheisland",
				"CoshipGeneral",
				"Withtheisland",
				"COSHIPChau"});
		regestCombileProductor("EastPacific" , new String[]{ });
		regestCombileProductor("Staronthe9th" , new String[]{ });
		regestCombileProductor("HUAWEI" , new String[]{"HuaweiSTB" });
		regestCombileProductor("Changhong" , new String[]{"ChanghongSatellite",
				"ChanghongSTB" });
		regestCombileProductor("Konka" , new String[]{"KonkaSTB" });
		regestCombileProductor("Kaibor" , new String[]{"OpenBorSTB" });
		regestCombileProductor("Picture" , new String[]{"U.S.picturesqueSTB" });
		regestCombileProductor("Haier" , new String[]{"HaierSTB",
				"RadioandtelevisionHaier" });
		regestCombileProductor("ZTE" , new String[]{"ZTESTB",
				"ZTEZTE" });
		regestCombileProductor("SiDakang" , new String[]{"StarcomSTB" });
		regestCombileProductor("Jiuzhou" , new String[]{"jiuzhou" });
		regestCombileProductor("SKYWORTH" , new String[]{"SkyworthSTB" });
		regestCombileProductor("HisenseSTB" , new String[]{ });
		regestCombileProductor("HuaXiaSTB" , new String[]{"Xoceco" });
		regestCombileProductor("MIB" , new String[]{ });
		regestCombileProductor("China" , new String[]{"Shenzhouelectronic" });
		regestCombileProductor("BESTV" , new String[]{ });
		regestCombileProductor("Asthemusic" , new String[]{"MusicasSTB" });
		regestCombileProductor("Tampa" , new String[]{"DVNSTB" });
		regestCombileProductor("Panasonic" , new String[]{"PanasonicHD","PanasonicSTB" });
		regestCombileProductor("EastWideSight" , new String[]{});
		regestCombileProductor("Widelycable" , new String[]{ });
		regestCombileProductor("Radioandtelevision" , new String[]{"Radioandtelevisiondigital",
				"Radioandtelevisionsuccess",
				"Aihuabroadcasting",
				"Wickhamradio",
				"SVAGNI",
				"SVA",
				"SVASTB"});
		regestCombileProductor("TheNorth" , new String[]{"BBEFSTB" });
		regestCombileProductor("YumSTB" , new String[]{});
		regestCombileProductor("JuyouNetwork" , new String[]{});
		regestCombileProductor("MotorolaSTB" , new String[]{});
		regestCombileProductor("ChinaTelecomIhaveEhome" , new String[]{});
		regestCombileProductor("Sixsatellitereceiver" , new String[]{});
		regestCombileProductor("GaussBaer" , new String[]{"GOSPELLSTB" });
		regestCombileProductor("Theseadi" , new String[]{});
		regestCombileProductor("Ninejoint" , new String[]{"JiulianTechnology" });
		regestCombileProductor("Everyvillage" , new String[]{});
		regestCombileProductor("Huhutong" , new String[]{});
		regestCombileProductor("HoiMeiDiSTB" , new String[]{"HIMEDIASTB"});
		regestCombileProductor("DaxianSTB" , new String[]{});
		regestCombileProductor("GalaxySTB" , new String[]{});
		regestCombileProductor("JiaChongSTB" , new String[]{});
		regestCombileProductor("JiaCaiSTB" , new String[]{});
		regestCombileProductor("GoldNetcomSTB" , new String[]{});
		regestCombileProductor("PanoramicSTB" , new String[]{});
		regestCombileProductor("AirDivisionSTB" , new String[]{});
		regestCombileProductor("LeaguerSTB" , new String[]{});
		regestCombileProductor("MATRIX" , new String[]{});
		regestCombileProductor("Wave" , new String[]{});
		regestCombileProductor("HappinessExpress" , new String[]{});
		regestCombileProductor("GiantEagleTechnology" , new String[]{});
		regestCombileProductor("Goodrecord" , new String[]{});
		regestCombileProductor("RC-14A" , new String[]{});
		regestCombileProductor("AviationTechnology" , new String[]{});
		regestCombileProductor("LiHe" , new String[]{});
		regestCombileProductor("Yum" , new String[]{});
		regestCombileProductor("RC-8AZhuhai" , new String[]{});
		regestCombileProductor("WycombeVC-9030RC" , new String[]{});
		regestCombileProductor("DigitalTV" , new String[]{});
		regestCombileProductor("Asianshares" , new String[]{});
		regestCombileProductor("GalaxyElectronics" , new String[]{});
		regestCombileProductor("ThebasisofT3" , new String[]{});
		regestCombileProductor("Moons" , new String[]{});
		regestCombileProductor("Thegalaxy" , new String[]{});
	}
	
	private static void regestCombileProductor(String p1 , String[] p2)
	{
		Set<String> set = new HashSet<String>() ;
		set.add(p1);
		for ( int i = 0 ; i < p2.length ; i ++ )
			set.add(p2[i]);
		
		combineProductor.put(p1.toLowerCase(), set);
	}
}