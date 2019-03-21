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
		"beijing",		//����(Beijing)@5+2+2(+13
		"guangdong",		//�㶫(Guangdong)@28+65+19+5(+17
		"guangxi",		//����(Guangxi)@3+3+2+1(+26
		"shanghaiorientalcable",		//�Ϻ�/��������(Shanghai / Oriental Cable)@2+5+2(+14
		"tianjin",		//���(Tianjin)@4+3+4(+10
		"chongqing",		//����(Chongqing)@5+4+4(+16
		"liaoning",		//����(LIAONING)@13+11+11(+17
		"jiangsu",		//����(JIANGSU)@17+10+11(+14
		"hubei",		//����(Hubei)@7+10+10+(+15
		"sichuan",		//�Ĵ�(Sichuan)@3+3+3(+12
		"shaanxi",		//����(Shaanxi)@15+9+9(+10
		"zhejiang",		//�㽭(Zhejiang)@22+14+15(+17
		"hunan",		//����(Hunan)@7+1+1(+9
		"shandong",		//ɽ��(Shandong)@16+19+(+21
		"anwei",		//��΢(Anwei)@2+18+1(+16
		"guizhou",		//����(Guizhou)@5+6+10(+10
		"heilongjiang",		//������(Heilongjiang)@11+11+11+(+19
		"shanxi",		//ɽ��(Shanxi)@6+7+7+(+12
		"innermongolia",		//����(Inner Mongolia)@3+2+2+(+9
		"yunnan",		//����(Yunnan)@4+6+8(+8
		"henan",		//����(Henan)@10+10+10+(+10
		"hainan",		//����(Hainan)@3+1+1(+12
		"jilin",		//����(Jilin)@1+1+1(+17
		"hebei",		//�ӱ�(Hebei)@3+8+6+(+18
		"fujian",		//����(Fujian)@5+11+11(+17
		"xinjiang",		//�½�(Xinjiang)@ 2+1+2+(+11
		"jiangxi",		//����(Jiangxi)@1+7+1(+18
		"eastpacific",		//��̫(East Pacific)@1+(+5
		"coshipstb",		//ͬ�޻�����(Coship stb)@    2+3+24(+25
		"otherregions",		//��������(Other regions)@ 146,
		"staronthe9th",		//���Ǿź�(Star on the 9th)@    2+(+8
		"gansu",		//����(Gansu)@4+4+(+4
		"hongkong",		//���(Hong Kong)@3+3(+
		"taiwan",		//̨��(Taiwan)@1+1(+
		"huaweistb",		//��Ϊ������(Huawei STB)@3+24(+
		"changhongstb",		///���������(Changhong STB)@4+9(+
		"konkastb",		//���ѻ�����(Konka STB)@5+2(+
		"ztestb",		//���˻�����(ZTE STB)@9+(+6
		"jiuzhou",		//���޻�����(���޻�����)@5+14(+
		"motorolastb",		//Ħ������������(Motorola STB)@+6(+
		"skyworthstb",		//��ά������(Skyworth STB)@5+13(+
		"hisensestb",		//���Ż�����(Hisense STB)@5+7(+
		"haierstb",		//����������(Haier STB)@5+2(+
		"gospellstb",		//��˹����������(GOSPELL STB)@4+3(+
		"hoimeidistb",		//�����ϻ�����(Hoi Mei Di STB)@10+2(+
		"openborstb",		//������������(Open Bor STB)@5+2+7(+
		"dvnstb",		//��ػ�����(DVN STB)@4+12(+
		"daxianstb",		//���Ի�����(Daxian STB)@5+2(+
		"musicasstb",		//���ӻ�����(Music as STB)@5+(+
		"starcomstb",		//˹�￵������(Starcom STB)@4+4(+
		"galaxystb",		//���ӻ�����(Galaxy STB)@4+4+1(+
		"ninejoint",		//����(Nine joint)@4+8(+
		"gehuastb",		//�軪���������(Gehua STB)@5+4(+
		"tianweistb",		//����������(Tianwei STB)@5+2(+
		"huaxiastb",		//�Ļ�������(Hua Xia STB)@5+2(+
		"jiachongstb",		//�Ѵ�������(Jia Chong STB)@5+1(+
		"jiacaistb",		//�Ѳʻ�����(Jia Cai STB)@5+2(+
		"goldnetcomstb",		//����ͨ������(Gold Netcom STB)@5+3(+
		"juyounetwork",		//��������(Juyou Network)@5+1(+
		"u.s.picturesquestb",		//���续������(U.S. picturesque STB)@5+6(+
		"eastwidesight",		//��������(East WideSight)@5+(+
		"panasonicstb",		//���»�����(Panasonic STB)@5+1(+
		"svastb",		//�Ϲ�������(SVA STB)@5+1(+
		"bbefstb",		//���������(BBEF STB)@5+5(+
		"panoramicstb",		//ȫ��������(Panoramic STB)@4+4(+
		"airdivisionstb",		//���ƻ�����(Air Division STB)@4+1(+
		"leaguerstb",		//���ϻ�����(Leaguer STB)@4+1(+
		"yumstb",		//��ʤ������(Yum STB)/MATRIX@2+2(+
		"matrix",		//MATRIX(MATRIX)@2+2(+
		"wave",		//�˳�������(Wave)@3+(+
		"topway",		//����������Ѷ(TOPWAY)@1+(+
		"gehua",		//�����軪����(Gehua)@1+(+
		"huhutong",		//����ͨ(Huhutong)@1+(+
		"mib",		//С�׺���(MIB)@2+(+
		"anhuiradio",		//���չ��(Anhui radio)@2
		"anextensivenetwork",		//��������(An extensive network)@2
		"anhuihaier",		//���պ���(Anhui Haier)@2
		"anqing",		//����(Anqing)@2
		"frogbu",		//�ܲ�(Frog Bu)@2
		"chizhou",		//����(Chizhou)@2
		"chizhouradioandtelevision",		//���ݹ��(Chizhou radio and television)@2
		"chuzhou",		//����(Chuzhou)@2
		"fuyang",		//����(Fuyang)@2
		"bozhou",		//����(Bozhou)@2
		"hefei",		//�Ϸ�(Hefei)@2
		"hefeicable",		//�Ϸ�����(Hefei cable)@2
		"huaibei",		//����(Huaibei)@2
		"huainan",		//����(Huainan)@2
		"huainanradioandtelevision",		//���Ϲ��(Huainan radio and television)@2
		"mounthuangshan",		//��ɽ(Mount Huangshan)@2
		"luan",		//����(Lu'an)@2
		"luandigital",		//��������(Lu'an digital)@2
		"maanshan",		//��ɽ(Ma'anshan)@2
		"suzhou",		//����(Suzhou)@2
		"suzhouradioandtelevision",		//���ݹ��(Suzhou radio and television)@2
		"tongling",		//ͭ��(Tongling)@2
		"tonglingradioandtelevision",		//ͭ����(Tongling radio and television)@2
		"wuhu",		//�ߺ�(Wuhu)@2
		"xuancheng",		//����(Xuancheng)@2
		"thatradioandtelevision",		//���ƹ��(That radio and television)@2
		"beijinggehuacablehd",		//�����軪���߸���(Beijing Gehua cable HD)@2
		"chongqingchanghong",		//���쳤��(Chongqing Changhong)@2
		"chongqingyinke",		//��������(Chongqing Yinke)@2
		"chongqingcable",		//��������(Chongqing cable)@2
		"chongqingcabletwo",		//�������߶�(Chongqing cable two)@2
		"fujianradioandtelevision",		//�������(Fujian radio and television)@2
		"fuzhouradioandtelevision",		//���ݹ��(Fuzhou radio and television)@2
		"longyan",		//����(Longyan)@2
		"longyancable",		//��������(Longyan cable)@2
		"nanping",		//��ƽ(Nanping)@2
		"nanpingcable",		//��ƽ����(Nanping cable)@2
		"ningde",		//����(Ningde)@2
		"ningdecable",		//��������(Ningde cable)@2
		"putian",		//����(Pu Tian)@2
		"putiancable",		//��������(Pu Tian cable)@2
		"quanzhou",		//Ȫ��(Quanzhou)@2
		"quanzhouradioandtelevision",		//Ȫ�ݹ��(Quanzhou radio and television)@2
		"sanming",		//����(Sanming)@2
		"sanmingbroadcast",		//�������(Sanming broadcast)@2
		"xiamen",		//����(Xiamen)@2
		"xiamenskyworth",		//���Ŵ�ά(Xiamen SKYWORTH)@2
		"xiamendahua",		//���Ŵ�(Xiamen Dahua)@2
		"xiamentsinghuatongfang",		//�����廪ͬ��(Xiamen Tsinghua Tongfang)@2
		"zhangzhou",		//����(Zhangzhou)@2
		"zhangzhouradioandtelevision",		//���ݹ��(Zhangzhou radio and television)@2
		"silver",		//����(Silver)@2
		"dingxi",		//����(Dingxi)@2
		"gansuradioandtelevision",		//������(Gansu radio and television)@2
		"gansuhuawei",		//���໪Ϊ(Gansu Huawei)@2
		"gansujiesaitechnology",		//��������Ƽ�(Gansu Jiesai Technology)@2
		"gansustatecoships58",		//����ͬ��COSHIPS58(Gansu state COSHIPS58)@2
		"gansucable",		//��������(Gansu cable)@2
		"jiayuguan",		//������(Jiayuguan)@2
		"jinchang",		//���(Jinchang)@2
		"jiuquan",		//��Ȫ(Jiuquan)@2
		"lanzhou",		//����(Lanzhou)@2
		"lanzhoupetrochemical",		//����ʯ��(Lanzhou petrochemical)@2
		"longnan",		//¤��(Longnan)@2
		"folkmusic",		//����(Folk music)@2
		"musicradio",		//���ֹ��(Music Radio)@2
		"pingliang",		//ƽ��(Pingliang)@2
		"qingyang",		//����(Qingyang)@2
		"tianshui",		//��ˮ(Tianshui)@2
		"wuwei",		//����(Wuwei)@2
		"zhangye",		//��Ҵ(Zhangye)@2
		"chaozhou",		//����(Chaozhou)@2
		"chaoancable",		//��������(Chaoan cable)@2
		"dongguan",		//��ݸ(Dongguan)@2
		"dongguanradioandtvone",		//��ݸ���һ(Dongguan radio and TV one)@2
		"dongguanradioandtvtwo",		//��ݸ����(Dongguan radio and TV two)@2
		"foshan",		//��ɽ(Foshan)@2
		"foshantsinghuatongfang",		//��ɽ�廪ͬ��(Foshan Tsinghua Tongfang)@2
		"withafoshan",		//��ɽͬ��һ(With a Foshan)@2
		"foshanwithtwo",		//��ɽͬ�ݶ�(Foshan with two)@2
		"foshancable",		//��ɽ����(Foshan cable)@2
		"guangzhou",		//����(Guangzhou)@2
		"guangzhoucable",		//��������(Guangzhou cable)@2
		"pearldigital",		//�齭����(Pearl digital)@2
		"heyuan",		//��Դ(Heyuan)@2
		"heyuanradioandtelevision",		//��Դ���(Heyuan radio and television)@2
		"heyuanjiacai",		//��Դ�Ѳ�(Heyuan Jia Cai)@2
		"huizhou",		//����(Huizhou)@2
		"huizhoucable",		//��������(Huizhou cable)@2
		"jiangmen",		//����(Jiangmen)@2
		"jiangmenradioandtelevision",		//���Ź��(Jiangmen radio and television)@2
		"jieyang",		//����(Jieyang)@2
		"maoming",		//ï��(Maoming)@2
		"meizhou",		//÷��(Meizhou)@2
		"meizhoucable",		//÷������(Meizhou cable)@2
		"thesouthchinasea",		//�Ϻ�(The South China Sea)@2
		"nanhaibroadcasting",		//�Ϻ����(Nanhai broadcasting)@2
		"puning",		//����(Puning)@2
		"puningcable",		//��������(Puning cable)@2
		"qingyuan",		//��Զ(Qingyuan)@2
		"qingyuanradioandtelevision",		//��Զ���(Qingyuan radio and television)@2
		"theimmortalhead",		//��ͷ(The immortal head)@2
		"chaoyangcable",		//��������(Chaoyang cable)@2
		"guiyucable",		//��������(Guiyu cable)@2
		"fairytail",		//��β(Fairy tail)@2
		"shaoguan",		//�ع�(Shaoguan)@2
		"shenzhen",		//����(Shenzhen)@2
		"shenzhenprint-ritesd",		//������������(Shenzhen Print-Rite SD)@2
		"shenzhenprint-ritehd",		//������������(Shenzhen Print-Rite HD)@2
		"yangjiang",		//����(Yangjiang)@2
		"yunfu",		//�Ƹ�(Yunfu)@2
		"zengcheng",		//����(Zengcheng)@2
		"zengchengcable",		//��������(Zengcheng cable)@2
		"zhanjiang",		//տ��(Zhanjiang)@2
		"zhanjiangradioandtelevision",		//տ�����(Zhanjiang radio and television)@2
		"zhaoqing",		//����(Zhaoqing)@2
		"zhongshan",		//��ɽ(Zhongshan)@2
		"zhongshancable",		//��ɽ����(Zhongshan cable)@2
		"zhuhai",		//�麣(Zhuhai)@2
		"zhuhairadioandtvone",		//�麣���һ(Zhuhai radio and TV one)@2
		"zhuhairadioandtvtwo",		//�麣����(Zhuhai radio and TV two)@2
		"zhuhainetwork",		//�麣����(Zhuhai network)@2
		"zhuhaicable",		//�麣����(Zhuhai cable)@2
		"baise",		//��ɫ(Baise)@2
		"thenorthsea",		//����(The North Sea)@2
		"chongzuo",		//����(Chongzuo)@2
		"portoffangcheng",		//���Ǹ�(Port of Fangcheng)@2
		"guangxiradioandtvnetwork",		//�����������(Guangxi radio and TV network)@2
		"guigang",		//���(Guigang)@2
		"guilin",		//����(Guilin)@2
		"hechi",		//�ӳ�(Hechi)@2
		"hezhou",		//����(Hezhou)@2
		"guest",		//����(Guest)@2
		"liuzhou",		//����(Liuzhou)@2
		"nanning",		//����(Nanning)@2
		"qinzhou",		//����(Qinzhou)@2
		"wuzhou",		//����(Wuzhou)@2
		"guangxiyulin",		//����(guangxi Yulin)@2
		"anshun",		//��˳(Anshun)@2
		"bijie",		//�Ͻ�(Bijie)@2
		"guiyang",		//����(Guiyang)@2
		"guizhouradioandtelevision",		//���ݹ��(Guizhou radio and television)@2
		"liupanshui",		//����ˮ(Liupanshui)@2
		"tongren",		//ͭ��(Tongren)@2
		"zunyi",		//����(Zunyi)@2
		"zunyiradioandtelevision",		//������(Zunyi radio and television)@2
		"zunyihuawei",		//���廪Ϊ(Zunyi Huawei)@2
		"zunyinetcom",		//�������ͨ(Zunyi Netcom)@2
		"zunyikyushu",		//�������(Zunyi Kyushu)@2
		"zunyidigital",		//��������(Zunyi digital)@2
		"zunyistate",		//����ͬ��(Zunyi state)@2
		"haikou",		//����(Haikou)@2
		"hainancable",		//��������(Hainan cable)@2
		"hainanradioandtelevision",		//���Ϲ��(Hainan radio and television)@2
		"sanya",		//����(Sanya)@2
		"baoding",		//����(Baoding)@2
		"botou",		//��ͷ(Botou)@2
		"botoucablea",		//��ͷ����һ(Botou cable a)@2
		"botoucabletwo",		//��ͷ���߶�(Botou cable two)@2
		"cangzhou",		//����(Cangzhou)@2
		"cangzhoucable",		//��������(Cangzhou cable)@2
		"chengde",		//�е�(Chengde)@2
		"chengderadioandtelevision",		//�е¹��(Chengde radio and television)@2
		"handan",		//����(Handan)@2
		"hengshui",		//��ˮ(Hengshui)@2
		"yongfang",		//�{��(Yong Fang)@2
		"qinghuangdao",		//�ػʵ�(Qinghuangdao)@2
		"shijiazhuang",		//ʯ��ׯ(Shijiazhuang)@2
		"hebeiradioandtvnetwork",		//�ӱ��������(Hebei radio and TV network)@2
		"tangshan",		//��ɽ(Tangshan)@2
		"xingtai",		//��̨(Xingtai)@2
		"zhangjiakou",		//�żҿ�(Zhangjiakou)@2
		"daqing",		//����(Daqing)@2
		"daqingcable",		//��������(Daqing cable)@2
		"harbin",		//������(Harbin)@2
		"harbinknow-all",		//����������ͨ(Harbin know-all)@2
		"harbinsidakang",		//������˹�￵(Harbin Sidakang)@2
		"harbincablea",		//����������һ(Harbin cable a)@2
		"harbincabletwo",		//���������߶�(Harbin cable two)@2
		"harbincablethree",		//������������(Harbin cable three)@2
		"hegang",		//�׸�(Hegang)@2
		"heihe",		//�ں�(Heihe)@2
		"heiheradioandtelevision",		//�ںӹ��(Heihe radio and television)@2
		"blacklongjiangbroadcasting",		//�\�������(Black Longjiang broadcasting)@2
		"blacklongjiangchanghong",		//�\��������(Black Longjiang Changhong)@2
		"heilongjiangstatefarms",		//������ũ��(Heilongjiang state farms)@2
		"farmingcable",		//ũ������(Farming cable)@2
		"jiamusi",		//��ľ˹(Jiamusi)@2
		"jixi",		//����(Jixi)@2
		"mudanjiang",		//ĵ����(Mudanjiang)@2
		"mudanjiangcable",		//ĵ��������(Mudanjiang cable)@2
		"qigihar",		//�������(Qigihar)@2
		"qigiharcable",		//�����������(Qigihar cable)@2
		"qitaihe",		//��̨��(Qitaihe)@2
		"shuangyashan",		//˫Ѽɽ(Shuangyashan)@2
		"suihua",		//�绯(Suihua)@2
		"suihuacable",		//�绯����(Suihua cable)@2
		"tailai",		//̩��(Tailai)@2
		"tailaicable",		//̩������(Tailai cable)@2
		"heilongjiangyichun",		//����(heilongjiang Yichun)@2
		"anyang",		//����(Anyang)@2
		"anyangcable",		//��������(Anyang cable)@2
		"hebi",		//�ױ�(Hebi)@2
		"henanradioandtelevision",		//���Ϲ��(Henan radio and television)@2
		"henangni",		//����GNI(Henan GNI)@2
		"henanvcom",		//��������ķ(Henan VCOM)@2
		"jiaozuo",		//����(Jiaozuo)@2
		"jiyuan",		//��Դ(Jiyuan)@2
		"kaifeng",		//����(Kaifeng)@2
		"kaifengradioandtelevision",		//������(Kaifeng radio and television)@2
		"luohe",		//���(Luohe)@2
		"luoyang",		//����(Luoyang)@2
		"nanyang",		//����(Nanyang)@2
		"pingdingshan",		//ƽ��ɽ(Pingdingshan)@2
		"puyang",		//���(Puyang)@2
		"sanmenxia",		//����Ͽ(Sanmenxia)@2
		"sanmenxiacablea",		//����Ͽ����һ(Sanmenxia cable a)@2
		"sanmenxiacabletwo",		//����Ͽ���߶�(Sanmenxia cable two)@2
		"sanmenxiacablethree",		//����Ͽ������(Sanmenxia cable three)@2
		"sanmenxiacablefour",		//����Ͽ������(Sanmenxia cable four)@2
		"shangqiu",		//����(Shangqiu)@2
		"shangqiuradioandtelevision",		//������(Shangqiu radio and television)@2
		"yongchengcable",		//��������(Yongcheng cable)@2
		"xiangcheng",		//���(Xiangcheng)@2
		"xiangchengcable",		//�������(Xiangcheng cable)@2
		"xinxiang",		//����(Xinxiang)@2
		"xinyang",		//����(Xinyang)@2
		"xuchang",		//���(Xuchang)@2
		"zhengzhou",		//֣��(Zhengzhou)@2
		"zhengzhouhuilong",		//֣�ݻ���(Zhengzhou Huilong)@2
		"zhengzhoumotorola",		//֣��Ħ������(Zhengzhou Motorola)@2
		"zhengzhougalaxy",		//֣������(Zhengzhou Galaxy)@2
		"zhoukou",		//�ܿ�(Zhoukou)@2
		"zhumadian",		//פ���(Zhumadian)@2
		"ezhou",		//����(Ezhou)@2
		"huanggang",		//�Ƹ�(Huanggang)@2
		"huangshi",		//��ʯ(Huangshi)@2
		"hubeiradioandtelevision",		//�������(Hubei radio and television)@2
		"hubeichanghong",		//��������(Hubei Changhong)@2
		"jingmen",		//����(Jingmen)@2
		"jingzhou",		//����(Jingzhou)@2
		"shiyan",		//ʮ��(Shiyan)@2
		"suizhou",		//����(Suizhou)@2
		"wuhan",		//�人(Wuhan)@2
		"wuhanskyworth",		//�人��ά(Wuhan SKYWORTH)@2
		"wuhanhuawei",		//�人��Ϊ(Wuhan Huawei)@2
		"xiangfan",		//�差(xiangfan)@2
		"xianning",		//����(Xianning)@2
		"xianningdigital",		//��������(Xianning digital)@2
		"xiaogan",		//Т��(Xiaogan)@2
		"xuanchang",		//����(Xuan Chang)@2
		"changde",		//����(Changde)@2
		"changsha",		//��ɳ(Changsha)@2
		"changshabroadcasting",		//��ɳ���(Changsha broadcasting)@2
		"chenzhou",		//����(Chenzhou)@2
		"chenzhoucable",		//��������(Chenzhou cable)@2
		"hengyang",		//����(Hengyang)@2
		"hengyangcable",		//��������(Hengyang cable)@2
		"huaihua",		//����(Huaihua)@2
		"hunanradioandtelevision",		//���Ϲ�� (Hunan radio and television)@2
		"loudi",		//¦��(Loudi)@2
		"shaoyang",		//����(Shaoyang)@2
		"xiangtan",		//��̶ (Xiangtan)@2
		"yiyang",		//����(Yiyang)@2
		"yongzhou",		//����(yongzhou)@2
		"yueyang",		//����(Yueyang)@2
		"zhangjiajie",		//�żҽ�(Zhangjiajie)@2
		"zhuzhou",		//����(Zhuzhou)@2
		"changzhou",		//����(Changzhou)@2
		"changzhoucable",		//��������(Changzhou cable)@2
		"haimen",		//����(Haimen)@2
		"haimenisland",		//����ͬ�� (Haimen Island)@2
		"huaian",		//����(Huaian)@2
		"jiangyin",		//����(Jiangyin)@2
		"jiangyincable",		//��������(Jiangyin cable)@2
		"lianyungang",		//���Ƹ�(Lianyungang)@2
		"nanjing",		//�Ͼ�(Nanjing)@2
		"jiangsuinteraction",		//���ջ���(Jiangsu interaction)@2
		"nantong",		//��ͨ(Nantong)@2
		"suqian",		//��Ǩ(Suqian)@2
		"suqiancable",		//��Ǩ����(Suqian cable)@2
		"jiangsusuzhou",		//����(jiangsu Suzhou)@2
		"suzhoudigital",		//��������(Suzhou digital)@2
		"jiangsutaizhou",		//̩��(jiangsu Taizhou)@2
		"jiangsutaizhoucable",		//̩������(jiangsu Taizhou cable)@2
		"wuxi",		//����(Wuxi)@2
		"wuxicable",		//��������(Wuxi cable)@2
		"xuzhou",		//����(Xuzhou)@2
		"yancheng",		//�γ�(Yancheng)@2
		"yangzhou",		//����(Yangzhou)@2
		"zhenjiang",		//��(Zhenjiang)@2
		"fuzhou",		//����(Fuzhou)@2
		"ganzhou",		//����(Ganzhou)@2
		"jian",		//����(Ji'an)@2
		"jiangxiradioandtelevision",		//�������(Jiangxi radio and television)@2
		"jiangxicable",		//��������(Jiangxi cable)@2
		"jingdezhen",		//������(Jingdezhen)@2
		"jiujiang",		//�Ž�(Jiujiang)@2
		"nanchang",		//�ϲ�(Nanchang)@2
		"nanchangbroadcasting",		//�ϲ����(Nanchang broadcasting)@2
		"pingxiang",		//Ƽ��(Pingxiang)@2
		"shangrao",		//����(Shangrao)@2
		"xinyu",		//����(Xinyu)@2
		"yichun",		//�˴�(Yichun)@2
		"yingtan",		//ӥ̶(Yingtan)@2
		"baicheng",		//�׳�(Baicheng)@2
		"mountbai",		//��ɽ(Mount Bai)@2
		"changchun",		//����(Changchun)@2
		"jilin",		//����(Jilin)@2
		"jilinradioandtvnetwork",		//���ֹ������(Jilin radio and TV network)@2
		"liaoyuan",		//��Դ(Liaoyuan)@2
		"siping",		//��ƽ(Siping)@2
		"songyuan",		//��ԭ(Songyuan)@2
		"tonghua",		//ͨ��(Tonghua)@2
		"anshan",		//��ɽ(Anshan)@2
		"benxi",		//��Ϫ(Benxi)@2
		"benxiradioandtelevision",		//��Ϫ���(Benxi radio and television)@2
		"chaoyang",		//����(Chaoyang)@2
		"dalian",		//����(Dalian)@2
		"daliandaxian",		//��������(Dalian Daxian)@2
		"dalianhaier",		//��������(Dalian Haier)@2
		"dalianhisense",		//��������(Dalian Hisense)@2
		"daliankyushu",		//��������(Dalian Kyushu)@2
		"dalianthomson",		//����THOMSON(Dalian THOMSON)@2
		"dandong",		//����(Dandong)@2
		"fushun",		//��˳(Fushun)@2
		"fushunradioandtelevision",		//��˳���(Fushun radio and television)@2
		"fuxin",		//����(Fuxin)@2
		"huludao",		//��«��(Huludao)@2
		"huludaodigital",		//��«������(Huludao digital)@2
		"jinzhou",		//����(Jinzhou)@2
		"liaoningradioandtelevision",		//�������(Liaoning radio and television)@2
		"liaoningchuangjia",		//��������(Liaoning Chuangjia)@2
		"liaoyang",		//����(Liaoyang)@2
		"panjin",		//�̽�(Panjin)@2
		"shenyang",		//����(Shenyang)@2
		"shenyangmedianetwork",		//������ý����(Shenyang media network)@2
		"tieling",		//����(Tieling)@2
		"tielingskycable",		//�����������(Tieling sky cable)@2
		"wafangdiancity",		//�߷���(Wafangdian City)@2
		"wafangdiancityradioandtelevision",		//�߷�����(Wafangdian City radio and television)@2
		"yingkou",		//Ӫ��(Yingkou)@2
		"alxaleague",		//��������(Alxa League)@2
		"baotou",		//��ͷ(Baotou)@2
		"thebayannaoerleague",		//�͏��׶���(The Bayannaoer League)@2
		"wuyuandigital",		//��ԭ����(Wuyuan digital)@2
		"chifeng",		//���(Chifeng)@2
		"erdos",		//������˹(Erdos)@2
		"hohhot",		//���ͺ���(Hohhot)@2
		"hulunbuir",		//���ױ���(Hulun Buir)@2
		"innermongolia",		//���ɹ�(Inner Mongolia)@2
		"innermongoliaradioandtelevision",		//���ɹŹ�� (Inner Mongolia radio and television)@2
		"tongliao",		//ͨ��(Tongliao)@2
		"wuhai",		//�ں�(Wuhai)@2
		"thewulanchabuleague",		//�����첼��(The Wulanchabu League)@2
		"xilinguolemeng",		//���ֹ�����(Xilinguole Meng)@2
		"xinganmeng",		//�˰���(Xingan Meng)@2
		"guyuan",		//��ԭ(Guyuan)@2
		"ningxiaradioandtelevision",		//���Ĺ��(Ningxia radio and television)@2
		"ningxiaskyworth",		//���Ĵ�ά(Ningxia SKYWORTH)@2
		"shizuishan",		//ʯ��ɽ(Shizuishan)@2
		"wuzhong",		//����(Wuzhong)@2
		"yinchuan",		//����(Yinchuan)@2
		"zhongwei",		//����(Zhongwei)@2
		"guoluo",		//����(Guoluo)@2
		"haibeistate",		//������(Haibei state)@2
		"haidong",		//����(Haidong)@2
		"haidongprefecture",		//������(Haidong Prefecture)@2
		"thestateofhainan",		//������(The state of Hainan)@2
		"haixizhou",		//������(Hai Xizhou)@2
		"xining",		//����(Xining)@2
		"xiningjiulian",		//��������(Xining Jiulian)@2
		"yushu",		//����(Yushu)@2
		"binzhou",		//����(Binzhou)@2
		"binzhouradioandtelevision",		//���ݹ��(Binzhou radio and television)@2
		"texas",		//����(Texas)@2
		"dongying",		//��Ӫ(Dongying)@2
		"dongyingradioandtelevision",		//��Ӫ���(Dongying radio and television)@2
		"heze",		//����(Heze)@2
		"hezecable",		//��������(Heze cable)@2
		"jinan",		//����(Ji'nan)@2
		"jinanradioandtelevision",		//���Ϲ��(Ji'nan radio and television)@2
		"jinanhaiera",		//���Ϻ���һ(Ji'nan Haier a)@2
		"jinanhaiertwo",		//���Ϻ�����(Ji'nan Haier two)@2
		"jinanhaierthree",		//���Ϻ�����(Ji'nan Haier three)@2
		"jinanhaierfour",		//���Ϻ�����(Ji'nan Haier four)@2
		"jinanhisensea",		//���Ϻ���һ(Ji'nan Hisense a)@2
		"jinanhisensetwo",		//���Ϻ��Ŷ�(Ji'nan Hisense two)@2
		"jinanhisensethree",		//���Ϻ�����(Ji'nan Hisense three)@2
		"jinantianbo",		//�������(Ji'nan Tianbo)@2
		"jining",		//����(Jining)@2
		"laiwu",		//����(Laiwu)@2
		"laiwuradioandtelevision",		//���߹��(Laiwu radio and television)@2
		"liaocheng",		//�ĳ�(Liaocheng)@2
		"linyi",		//����(Linyi)@2
		"linyihuawei",		//���ʻ�Ϊ(Linyi Huawei)@2
		"qingdao",		//�ൺ(Qingdao)@2
		"qingdaohuaguang",		//�ൺ����(Qingdao Huaguang)@2
		"qingdaokyushu",		//�ൺ����(Qingdao Kyushu)@2
		"sunshine",		//����(Sunshine)@2
		"shandongradioandtelevision",		//ɽ�����(Shandong radio and television)@2
		"shandongskyworth",		//ɽ����ά(Shandong SKYWORTH)@2
		"shandonghaier",		//ɽ������(Shandong Haier)@2
		"taian",		//̩��(Tai'an)@2
		"taianradioandtelevision",		//̩�����(Tai'an radio and television)@2
		"weifang",		//Ϋ��(Weifang)@2
		"anqiucable",		//��������(Anqiu cable)@2
		"weifangradioandtelevision",		//Ϋ�����(Weifang radio and television)@2
		"weifanghuaguang",		//Ϋ������(Weifang Huaguang)@2
		"weihai",		//����(Weihai)@2
		"weihairadioandtelevision",		//������� (Weihai radio and television)@2
		"yantai",		//��̨(Yantai)@2
		"yantaidigitaltv",		//��̨���ֵ���(Yantai digital TV)@2
		"zaozhuang",		//��ׯ(Zaozhuang)@2
		"zaozhuangradioandtelevision",		//��ׯ���(Zaozhuang radio and television)@2
		"zibo",		//�Ͳ�(Zibo)@2
		"zibodigitaltv",		//�Ͳ����ֵ���(Zibo digital TV)@2
		"zibostate",		//�Ͳ�ͬ��(Zibo state)@2
		"zibocablea",		//�Ͳ�����һ(Zibo cable a)@2
		"zibocabletwo",		//�Ͳ����߶�(Zibo cable two)@2
		"zibocablethree",		//�Ͳ�������(Zibo cable three)@2
		"zibocablefour",		//�Ͳ�������(Zibo cable four)@2
		"shanghai",		//�Ϻ�(Shanghai)@2
		"shanghaiorientalcablea",		//�Ϻ���������һ(Shanghai Oriental Cable a)@2
		"shanghaicabletwo",		//�Ϻ����߶�(Shanghai cable two)@2
		"shanghaidvt-rc-1",		//�Ϻ�DVT-RC-1(Shanghai DVT-RC-1)@2
		"changzhi",		//����(Changzhi)@2
		"datong",		//��ͬ(Da Tong)@2
		"datongcable",		//��ͬ����(Datong cable)@2
		"jincheng",		//����(Jincheng)@2
		"jinchengradioandtelevision",		//���ǹ��(Jincheng radio and television)@2
		"jinzhong",		//����(Jinzhong)@2
		"linfen",		//�ٷ�(Linfen)@2
		"linfencable",		//�ٷ�����(Linfen cable)@2
		"lvliang",		//����(Lvliang)@2
		"shanxiradioandtelevision",		//ɽ�����(shanxi radio and television)@2
		"happinessexpress",		//�Ҹ��쳵(Happiness Express)@2
		"shuozhou",		//˷��(Shuozhou)@2
		"taiyuan",		//̫ԭ(Taiyuan)@2
		"taiyuanskyworth",		//̫ԭ��ά(Taiyuan SKYWORTH)@2
		"taiyuanradioandtelevision",		//̫ԭ���(Taiyuan radio and television)@2
		"taiyuanhuawei",		//̫ԭ��Ϊ(Taiyuan Huawei)@2
		"taiyuanwave",		//̫ԭ�˳�(Taiyuan wave)@2
		"taiyuantyson",		//̫ԭ̩ɭ(Taiyuan Tyson)@2
		"taiyuangalaxy",		//̫ԭ����(Taiyuan Galaxy)@2
		"xinzhou",		//����(Xinzhou)@2
		"xinzhoucable",		//��������(Xinzhou cable)@2
		"yangquan",		//��Ȫ(Yangquan)@2
		"yuncheng",		//�˳�(Yuncheng)@2
		"ankang",		//����(Ankang)@2
		"baoji",		//����(Baoji)@2
		"baojiradioandtelevision",		//�������(Baoji radio and television)@2
		"hanzhoung",		//����(Hanzhoung)@2
		"shangluo",		//����(Shangluo)@2
		"shaanxiradioandtelevision",		//�������(Shaanxi radio and television)@2
		"shaanxiaihua",		//��������(Shaanxi Aihua)@2
		"tongchuan",		//ͭ��(Tongchuan)@2
		"weinan",		//μ��(Weinan)@2
		"xian",		//����(Xi'an)@2
		"shaanxidahua",		//������(Shaanxi Dahua)@2
		"shaanxirailwaysystem",		//������·ϵͳ(Shaanxi railway system)@2
		"xianbbk",		//����������(Xi'an BBK)@2
		"xianhisense",		//��������(Xi'an Hisense)@2
		"xianyang",		//����(Xianyang)@2
		"yanan",		//�Ӱ�(Yanan)@2
		"yananradioandtelevision",		//�Ӱ����(Yanan radio and television)@2
		"yulin",		//����(Yulin)@2
		"bazhong",		//����(Bazhong)@2
		"chengdu",		//�ɶ�(Chengdu)@2
		"chengduconte",		//�ɶ�����(Chengdu conte)@2
		"sichuankyushu",		//�Ĵ�����(Sichuan Kyushu)@2
		"dazhou",		//����(Dazhou)@2
		"deyang",		//����(Deyang)@2
		"guangan",		//�㰲(Guang'an)@2
		"guangyuan",		//��Ԫ(Guangyuan)@2
		"leshan",		//��ɽ(Leshan)@2
		"luzhou",		//����(Luzhou)@2
		"meishan",		//üɽ(Meishan)@2
		"mianyang",		//����(Mianyang)@2
		"nanchong",		//�ϳ�(Nanchong)@2
		"neijiang",		//�ڽ�(Neijiang)@2
		"panzhihua",		//��֦��(Panzhihua)@2
		"sichuanradioandtelevision",		//�Ĵ����(Sichuan Radio and television)@2
		"sichuanchanghong",		//�Ĵ�����(Sichuan Changhong)@2
		"suining",		//����(Suining)@2
		"yaan",		//�Ű�(Ya'an)@2
		"yibin",		//�˱�(Yibin)@2
		"zigong",		//�Թ�(Zigong)@2
		"ziyang",		//����(Ziyang)@2
		"tianjin",		//���(Tianjin)@2
		"tianjinbeijingcard",		//��򱱾���(Tianjin Beijing card)@2
		"tianjinchanghong",		//��򳤺�(Tianjin Changhong)@2
		"tianjinskyworth",		//���ά(Tianjin SKYWORTH)@2
		"tianjinradioandtelevisionnetwork",		//���������(Tianjin Radio and television network)@2
		"hongkongcable",		//���CABLE(Hongkong CABLE)@2
		"hongkongbroadbandnetwork",		//��ۿ�Ƶ(Hongkong broadband network)@2
		"hongkongnowtv",		//���NOWTV(Hongkong NOWTV)@2
		"akesu",		//������(Akesu)@2
		"aletai",		//����̩(Aletai)@2
		"bayanguoluo",		//�������(Bayan Guo Luo)@2
		"bortala",		//��������(Bortala)@2
		"changji",		//����(Changji)@2
		"hami",		//����(Hami)@2
		"andtian",		//����(And Tian)@2
		"kashi",		//��ʲ(Kashi)@2
		"karamay",		//��������(Karamay)@2
		"kizilsu",		//��������(Kizilsu)@2
		"shihezi",		//ʯ����(Shihezi)@2
		"tacheng",		//����(Tacheng)@2
		"turpan",		//��³��(Turpan)@2
		"urumqi",		//��³ľ��(Urumqi)@2
		"xinjiangradioandtelevision",		//�½����(Xinjiang radio and television)@2
		"xinjiangradioandtvone",		//�½����һ(Xinjiang radio and TV one)@2
		"xinjiangradioandtvtwo",		//�½�����(Xinjiang radio and TV two)@2
		"xinjianghuawei",		//�½���Ϊ(Xinjiang Huawei)@2
		"xinjiang",		//�½�(Xinjiang)@2
		"yili",		//����(Yili)@2
		"baoshan",		//��ɽ(Baoshan)@2
		"kunming",		//����(Kunming)@2
		"kunminghuawei",		//������Ϊ(Kunming Huawei)@2
		"kunmingputian",		//��������(Kunming Putian)@2
		"kunmingtianbo",		//�������(Kunming Tianbo)@2
		"kunmingyunguangnetwork",		//�����ƹ�����һ(Kunming Yunguang network)@2
		"lijiang",		//����(Lijiang)@2
		"lincang",		//�ٲ�(Lincang)@2
		"qujing",		//����(Qujing)@2
		"qujingradioandtelevision",		//�������(Qujing radio and television)@2
		"qujingradioandtvtwo",		//��������(Qujing radio and TV two)@2
		"simao",		//˼é(Simao)@2
		"yunnanradioandtelevision",		//���Ϲ��(Yunnan radio and television)@2
		"yunnangni",		//����GNI(Yunnan GNI)@2
		"yunnanhuawei",		//���ϻ�Ϊ(Yunnan Huawei)@2
		"yunnandigital",		//��������(Yunnan digital)@2
		"yuxi",		//��Ϫ(Yuxi)@2
		"yuxiradioandtelevision",		//��Ϫ���(Yuxi radio and television)@2
		"zhaotong",		//��ͨ(Zhaotong)@2
		"dongyang",		//����(Dongyang)@2
		"dongyangcablea",		//��������һ(Dongyang cable a)@2
		"dongyangcabletwo",		//�������߶�(Dongyang cable two)@2
		"hangzhou",		//����(Hangzhou)@2
		"hangzhoucolortechnology",		//���ݲ��ӿƼ�(Hangzhou color technology)@2
		"hangzhoudahua",		//���ݴ�(Hangzhou Dahua)@2
		"hangzhouhuawei",		//���ݻ�Ϊ(Hangzhou Huawei)@2
		"severalsourcesinhangzhou",		//������Դ(Several sources in Hangzhou)@2
		"huzhou",		//����(Huzhou)@2
		"jiashan",		//����(jiashan)@2
		"jiashancablea",		//��������һ(Jiashan cable a)@2
		"soyeacabletwo",		//��Դ���߶�(Soyea cable two)@2
		"jiaxing",		//����(Jiaxing)@2
		"jiaxingcable",		//��������(Jiaxing cable)@2
		"jinhua",		//��(Jinhua)@2
		"jinhuadahua",		//�𻪴�(Jin Huadahua)@2
		"kaihua",		//����(Kaihua)@2
		"kaihuacable",		//��������(Kaihua cable)@2
		"linhai",		//�ٺ�(Linhai)@2
		"linhaidigital",		//�ٺ�����(Linhai digital)@2
		"lishui",		//��ˮ(Lishui)@2
		"luqiao",		//·��(Luqiao)@2
		"ningbo",		//����(Ningbo)@2
		"fenghuacable",		//�����(Fenghua cable)@2
		"ningboskyworth",		//������ά(Ningbo SKYWORTH)@2
		"ningbodvb",		//����DVB(Ningbo DVB)@2
		"ningbohuawei",		//������Ϊ(Ningbo Huawei)@2
		"ningbokyushu",		//��������(Ningbo Kyushu)@2
		"ningbostate",		//����ͬ��(Ningbo state)@2
		"quzhou",		//����(Quzhou)@2
		"shaoxing",		//����(shaoxing)@2
		"shaoxinghuawei",		//���˻�Ϊ(Shaoxing Huawei)@2
		"shaoxingkyushu",		//���˾���(Shaoxing Kyushu)@2
		"taizhou",		//̨��(Taizhou)@2
		"taizhoucable",		//̨������(Taizhou cable)@2
		"wenzhou",		//����(Wenzhou)@2
		"wenzhouhuawei",		//���ݻ�Ϊ(Wenzhou Huawei)@2
		"wenzhoufour",		//������ͨ(Wenzhou four)@2
		"widelycable",		//�й�����(Widely cable)@2
		"pingyangradioandtelevision",		//ƽ�����(Pingyang radio and television)@2
		"yinzhou",		//۴��(Yinzhou)@2
		"yinzhoucable",		//۴������(Yinzhou cable)@2
		"yiwu",		//����(Yiwu)@2
		"yiwuradioandtelevision",		//���ڹ��(Yiwu radio and television)@2
		"yongkang",		//����(Yongkang)@2
		"yongkangcable",		//��������(Yongkang cable)@2
		"clouds",		//�ƺ�(Clouds)@2
		"cloudsandcabletwo",		//�ƺ����߶�(Clouds and cable two)@2
		"acloudandcable",		//�ƺ�����һ(A cloud and cable)@2
		"zhejiangradioandtelevision",		//�㽭���(Zhejiang radio and television)@2
		"gianteagletechnology",		//��ӥ�Ƽ�(Giant Eagle Technology)@2
		"zhoushan",		//��ɽ(Zhoushan)@2
		"zhuji",		//����(Zhuji)@2
		"guangxiradioandtelevision",		//�������(Guangxi radio and television)@2
		"hebeiinnermongolia",		//�ӱ�/���ɹ�(Hebei Inner Mongolia)@2
		"shandongzibodongguan",		//ɽ��-�Ͳ�/��ݸ(Shandong Zibo Dongguan)@2
		"shandongzibo",		//ɽ��-�Ͳ�(Shandong Zibo)@2
		"shandonglinyi",		//ɽ��-����(Shandong Linyi)@2
		"foshansouthchinasea",		//��ɽ/�Ϻ�(Foshan / South China Sea)@2
		"sichuanwenzhou",		//�Ĵ�/����(Sichuan Wenzhou)@2
		"chongqingnanjingningboshenzhen",		//����/�Ͼ�/����/����(Chongqing Nanjing Ningbo Shenzhen)@2
		"coshipelectronics",		//ͬ�޵���(Coship Electronics)@2
		"ningbozhenhai",		//����-��(Ningbo Zhenhai)@2
		"hangzhouxinjiangradioandtelevision",		//����/�½����(Hangzhou / Xinjiang radio and television)@2
		"jinanjiaxing",		//����/����(Ji'nan Jiaxing)@2
		"tampa",		//���(Tampa)@2
		"dalianqingdao",		//����/�ൺ(Dalian Qingdao)@2
		"panasonic",		//����(Panasonic)@2
		"shaoxingwenzhouningbo",		//����/����/����(Shaoxing Wenzhou Ningbo)@2
		"shaoxingningbo",		//����/����(Shaoxing Ningbo)@2
		"huawei",		//��Ϊ(HUAWEI)@2
		"dongguanradioandtelevision",		//��ݸ���(Dongguan radio and television)@2
		"jiuzhou",		//����(Jiuzhou)@2
		"sva",		//�Ϲ��(SVA)@2
		"sidakang",		//˹�￵(Si Dakang)@2
		"xoceco",		//�û�(Xoceco)@2
		"zhengzhoubeijingtaiyuan",		//֣��/����/̫ԭ(Zhengzhou Beijing Taiyuan)@2
		"goodrecord",		//�Ѵ�(Good record)@2
		"changhong",		//����(Changhong)@2
		"thenorth",		//����(The North)@2
		"hunancable",		//��������(Hunan cable)@2
		"rc-14a",		//RC-14A(RC-14A)@2
		"aviationtechnology",		//����(Aviation Technology)@2
		"lihe",		//����(Li He)@2
		"yum",		//��ʤ(Yum)@2
		"rc-8azhuhai",		//RC-8A �麣(RC-8A Zhuhai)@2
		"beijinggehua",		//���� �軪(Beijing Gehua)@2
		"hebeiradioandtelevision",		//�ӱ����(Hebei radio and television)@2
		"wycombevc-9030rc",		//����ķVC-9030RC(Wycombe VC-9030RC)@2
		"skyworth",		//��ά(SKYWORTH)@2
		"jieyangcable",		//��������(Jieyang cable)@2
		"shandongcable",		//ɽ������(Shandong cable)@2
		"bestv",		//BESTV(BESTV)@2
		"ztezte",		//ZTE����(ZTE ZTE)@2
		"tianjinradioandtelevision",		//�����(Tianjin radio and television)@2
		"yangzhouradioandtelevision",		//���ݹ��(Yangzhou radio and television)@2
		"qinghairadioandtelevision",		//�ຣ���(Qinghai radio and television)@2
		"digitaltv",		//���ֵ���(Digital TV)@2
		"zhangye",		//��Ҵ���(zhangye)@2
		"changyuancable",		//��ԫ����(Changyuan cable)@2
		"guangdongcable",		//�㶫����(Guangdong cable)@2
		"hainanftv",		//��������(Hainan FTV)@2
		"huaxiandigital",		//��������(Huaxian digital)@2
		"asianshares",		//���ǹɷ�(Asian shares)@2
		"jingzhouletter",		//��������(Jingzhou letter)@2
		"tangshancable",		//��ɽ����(Tangshan cable)@2
		"galaxyelectronics",		//���ӵ���(Galaxy Electronics)@2
		"harbincable",		//����������(Harbin cable)@2
		"anlucable",		//��½����(Anlu cable)@2
		"danriverbroadcasting",		//�������(Dan River broadcasting)@2
		"everyvillage",		//���ͨ(Every village)@2
		"anweihaier",		//��΢����(An Wei Haier)@2
		"harbin-know-all",		//������-����ͨ(Harbin-Know-all)@2
		"henancable",		//��������(Henan cable)@2
		"sichuanchanghongdigitaltv",		//�Ĵ��������ֵ���(Sichuan ChanghongDigitalTV)@2
		"sichuanchanghongandjiuzhou",		//�Ĵ����糤��;��޸���(Sichuan Changhong and jiuzhou)@2
		"changhongsatellite",		//��������(Changhong Satellite)dvb-c8000b1@2
		"sichuanchanghongradioandtelevision",		//�Ĵ�������(Sichuan Changhong Radio and television)@2
		"heilongjiangchanghongradioandtelevision",		//������������(Heilongjiang Changhong Radio and television)@2
		"hubeiradioandtelevisionchanghong",		//������糤��(HubeiRadio and television Changhong)@2
		"shandongradioandtelevisionskyworth",		//ɽ����紴ά(ShandongRadio and television SKYWORTH)@2
		"ningxiaradioandtelevisionskyworth",		//Ţ�Ĺ�紴ά(Ning XiaRadio and television SKYWORTH)@2
		"sothesourceskyworth",		//̫Դ��ά(So the sourceSKYWORTH)@2
		"xiamenskyworth",		//���Ŵ�ά(XiamenSKYWORTH)@2
		"xiandahua",		//������(Xi'an Da Hua)@2
		"xiamendahua",		//���Ŵ�(Xiamen Da Hua)@2
		"shanghaitheeastcabletwo",		//�Ϻ��������߶�(Shanghai The East cable two)@2
		"gansuradioandtelevisionstate",		//������ͬ��(Gansu Radio and television state)@2
		"gansuradioandtelevisionhuawei",		//�����绪Ϊ(Gansu Radio and television HUAWEI)@2
		"gansuradioandtelevisiongciscience&technology",		//����������Ƽ�(Gansu Radio and television GCI Science & Technology)@2
		"gaussbaer",		//��˹����(Gauss Baer)@2
		"dongguanjiacai",		//��ݸ�Ѳ�(Dongguan Jia Cai)@2
		"enping",		//��ƽ(Enping)@2
		"foshanstate",		//��ɽͬ��(Foshan state)@2
		"qingyuanradioandtvstation",		//��Զ�㲥����̨(Qingyuan radio and TV station)@2
		"shantoucabletv",		//��ͷ���ߵ���(Shantou cable TV)-AR180@2
		"shantouguiyucable",		//��ͷ��������(Shantou Guiyu cable)@2
		"shantouchaoyangcable1",		//��ͷ��������1(Shantou Chaoyang cable 1)@2
		"chaozhouchaoancable",		//���ݳ�������(Chaozhou Chaoan cable)@2
		"foshannanhaibroadcasting",		//��ɽ�Ϻ����(Foshan Nanhai broadcasting)@2
		"guangzhoupearlriverdigital",		//�����齭����(Guangzhou Pearl River digital)@2
		"zhuhairadioandtelevision",		//�麣���(Zhuhai radio and television)@2
		"guizhounetcom",		//���ݽ���ͨ(Guizhou Netcom)@2
		"guizhouzunyinetcom",		//�����������ͨ(Guizhou Zunyi Netcom)@2
		"guizhouzunyidigital",		//������������(Guizhou Zunyi digital)@2
		"guizhouzunyicounty",		//��������ͬ��(Guizhou Zunyi County)@2
		"guizhouzunyihuawei",		//�������廪Ϊ(Guizhou Zunyi Huawei)@2
		"guizhouzunyijiuzhou",		//�����������(Guizhou Zunyi Jiuzhou)@2
		"shandongradioandtelevisionhaier",		//ɽ����纣��(Shandong radio and television Haier)@2
		"theseadi",		//������(The sea di)-HD600A@2
		"theseadi",		//������(The sea di)@2
		"henanchanghong",		//���ϳ���(Henan Changhong)@2
		"wickhamradio",		//�������ķ(Wickham radio)@2
		"svagni",		//���GNI(SVA GNI)@2
		"zhoukouxiangchengcable",		//�ܿ��������(Zhoukou Xiangcheng cable)@2
		"sanmenxiacablefour",		//���ō{������(Sanmenxia cable four)@2
		"sanmenxiacabletwo",		//���ō{���߶�(Sanmenxia cable two)@2
		"sanmenxiacablethree",		//���ō{������(Sanmenxia cable three)@2
		"sanmenxiacablea",		//���ō{����һ(Sanmenxia cable a)@2
		"handanradioandtelevision",		//�������(Handan radio and television)@2
		"shijiazhuangradioandtvnetwork",		//ʯ��ׯ�������(Shijiazhuang radio and TV network)@2
		"iron��skycable",		//�����������(Iron �� sky cable)@2
		"yunnanradioandtelevisionhuawei",		//���Ϲ�绪Ϊ(Yunnan radio and television Huawei)@2
		"linfenhuawei",		//�ٷڻ�Ϊ(Linfen Huawei)@2
		"jiangsuyangzhou",		//��������(Jiangsu Yangzhou)@2
		"thebasisoft3",		//�ܿ�T3(The basis of T3)@2
		"jiuliantechnology",		//�����Ƽ�(Jiulian Technology)@2
		"qinghaixiningjiulian",		//�ຣ��������(Qinghai  Xining Jiulian)@2
		"chengdukyushu",		//�ɶ�����(Chengdu Kyushu)@2
		"kaibor",		//������(Kai bor)F4F9@2
		"kaibor",		//������(Kai bor)-K360i@2
		"konka",		//����(Konka)@2
		"konka",		//����(Konka)-SDC25@2
		"asthemusic",		//����(As the music)-C21@2
		"radioandtelevisionsuccess",		//��紴��(Radio and television success)@2
		"dalianthompson",		//������ķѷ(Dalian Thompson)@2
		"picture",		//���续(Picture)-r5@2
		"picture",		//���续(Picture)-v9@2
		"bayannaoerwuyuannumber",		//�����׶���ԭ��(Bayannaoer Wuyuan number)@2
		"shandongyangxin",		//ɽ������(Shandong Yangxin)@2
		"yantaidigital",		//��̨����(Yantai digital)@2
		"pennstateradioandtelevision",		//���ݹ��(Penn State Radio and television)@2
		"zibocable",		//�Ͳ�����(Zibo cable)@2
		"zibodigital",		//�Ͳ�����(Zibo digital)@2
		"radioandtelevisionhaier",		//��纣��(Radio and television Haier)@2
		"aihuabroadcasting",		//�������(Aihua broadcasting)@2
		"xianrailwaysystem",		//������·ϵͳ(Xi'an railway system)@2
		"china",		//����(China)@2
		"shenzhouelectronic",		//���ݵ���(Shenzhou electronic)@2
		"panasonichd",		//���¸���(Panasonic HD)@2
		"moons",		//����(Moons)-LT300@2
		"withtheisland",		//ͬ��(With the island)-N7700@2
		"coshipgeneral",		//ͬ��ͨ��(Coship  General)@2
		"withtheisland",		//ͬ��(With the island)-CDVBC58000@2
		"foshanchau",		//��ɽͬ��(Foshan Chau)@2
		"coshipchau",		//COSHIPͬ��(COSHIP Chau)-N9201@2
		"zibocoship",		//�Ͳ�ͬ��(Zibo Coship)@2
		"ningbocoship",		//����ͬ��(Ningbo Coship)@2
		"hongkong",		//���(Hongkong)-NOWTV@2
		"xinjianghuaweibroadcasting",		//�½���Ϊ���(Xinjiang  Huawei broadcasting)@2
		"chutianvideo",		//������Ѷ(Chutian video)@2
		"thegalaxy",		//����(The galaxy)-DVBC2010CB@2
		"thegalaxy",		//����(The galaxy)-DVBC2010C@2
		"yunnandigitaltv",		//�������ֵ���(Yunnan digital TV)@2
		"yunnanhouseholds",		//���ϻ���ͨ(Yunnan  Households)@2
		"kunmingcloudnetwork",		//�����ƹ�����(Kunming cloud network)@2
		"radioandtelevisiondigital",		//�������(Radio and television digital)@2
		"radioandtelevision",		//���(Radio and television)-GNI@2
		"jiuzhouningbo",		//��������(Jiuzhou  Ningbo)@2
		"shaoxingjiuzhou",		//���˾���(Shaoxing Jiuzhou)@2
		"wenzhoupingyangradioandtelevision",		//����ƽ�����(Wenzhou Pingyang radio and television)@2
		"wenzhouzhongguangcable",		//�����й�����(Wenzhou Zhongguang cable)@2
		"zhejiangbroadcastinggianteagletechnology",		//�㽭����ӥ�Ƽ�(Zhejiang  broadcasting Giant Eagle Technology)@2
		"ningbofenghuacable",		//���������(Ningbo Fenghua cable)@2
		"jiashancabletwo",		//�������߶�(Jiashan cable  two)@2
		"jiashancable",		//��������(Jiashan cable)@2
		"sixsatellitereceiver",		//�������ǽ��ջ�(Six satellite receiver)@2
		"chinatelecomihaveehome"		//�й���������E��(China Telecom I have E home)@2
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