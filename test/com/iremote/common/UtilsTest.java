package com.iremote.common;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.codec.digest.DigestUtils;

import com.iremote.infraredtrans.Remoter;
import com.iremote.infraredtrans.tlv.TlvWrap;

import cn.com.isurpass.gateway.server.processor.gateway.GatewayReportProcessor;
import cn.com.isurpass.gateway.udpserver.NettyUdpConnectionWrap;

public class UtilsTest
{
	private static String VTID= "America/Vancouver";
	private static String STID = "Asia/Shanghai";
	
	public static void main(String arg[])
	{
		System.out.println("back=\u8FD4\u56DE");
		System.out.println("add=\u589E\u52A0");
		System.out.println("submit=\u5B8C\u6210");
		System.out.println("cancel=\u53D6\u6D88");
		System.out.println("ok=\u786E\u5B9A");
		System.out.println("back=\u8FD4\u56DE");
		System.out.println("add=\u589E\u52A0");
		System.out.println("usertype=\u7528\u6237\u7C7B\u578B");
		System.out.println("passworduser=\u5BC6\u7801\u7528\u6237");
		System.out.println("fingerprintuser=\u6307\u7EB9\u7528\u6237");
		System.out.println("carduser=IC\u5361\u7528\u6237");
		System.out.println("sn=\u7F16\u53F7");
		System.out.println("password=\u5BC6\u7801");
		System.out.println("edit=\u4FEE\u6539");
		System.out.println("delete=\u5220\u9664");
		System.out.println("");
		System.out.println("doorlockuser=\u95E8\u9501\u7528\u6237");
		System.out.println("doorlockusermanage=\u95E8\u9501\u7528\u6237\u7BA1\u7406");
		System.out.println("adddoorlockusertitle=\u589E\u52A0\u95E8\u9501\u7528\u6237");
		System.out.println("");
		System.out.println("unlockongrantedprivilege=\u6388\u6743\u5F00\u9501");
		System.out.println("deviceprivielgegrant=\u8BBE\u5907\u6388\u6743");
		System.out.println("privilegegrantto=\u6388\u6743\u7ED9");
		System.out.println("periodofvalidity=\u6709\u6548\u671F");
		System.out.println("noprivilegegrantitemmessage=\u6CA1\u6709\u6388\u6743\u8BB0\u5F55\uFF0C\u60A8\u53EF\u4EE5\u6309\u53F3\u4E0A\u89D2\u589E\u52A0\u6309\u94AE\u589E\u52A0\u6388\u6743");
		System.out.println("grantdeviceprivielge=\u589E\u52A0\u8BBE\u5907\u6388\u6743");
		System.out.println("inputphonenumber=\u8BF7\u8F93\u5165\u624B\u673A\u53F7\u7801");
		System.out.println("inputusername=\u8BF7\u8F93\u5165\u7528\u6237\u59D3\u540D");
		System.out.println("inputvalidfrom=\u8BF7\u8F93\u5165\u6388\u6743\u5F00\u59CB\u65F6\u95F4");
		System.out.println("inputvalidthrougth=\u8BF7\u8F93\u5165\u6388\u6743\u7ED3\u675F\u65F6\u95F4");
		System.out.println("validfromshouldbeforevalidthrougth=\u5F00\u59CB\u65F6\u95F4\u4E0D\u80FD\u5927\u4E8E\u6216\u7B49\u4E8E\u7ED3\u675F\u65F6\u95F4\uFF01");
		System.out.println("");
		System.out.println("deviceoperation=\u64CD\u4F5C\u8BBE\u5907");
		System.out.println("day=\u5929");
		System.out.println("hour=\u5C0F\u65F6");
		System.out.println("min=\u5206\u949F");
		System.out.println("inputsecuritycode=\u8BF7\u8F93\u5165\u9A8C\u8BC1\u7801");
		System.out.println("inputphonenumber=\u8BF7\u8F93\u5165\u60A8\u7684\u624B\u673A\u53F7\u7801");
		System.out.println("unlock=\u5F00\u9501");
		System.out.println("authorizationexpired=\u6388\u6743\u5DF2\u8FC7\u671F");
		System.out.println("operationfailed=\u64CD\u4F5C\u5931\u8D25");
		System.out.println("verificationcodenotcorrect=\u9A8C\u8BC1\u7801\u9519\u8BEF");
		System.out.println("networkfault=\u7F51\u7EDC\u6545\u969C");
		System.out.println("wakeuplockin30second=\u8BF7\u5728{0}\u79D2\u5185\uFF0C\u5524\u9192\u95E8\u9501");
		System.out.println("securitycodephonenumbernotmatch=\u9A8C\u8BC1\u7801\u4E0E\u624B\u673A\u53F7\u4E0D\u5339\u914D");
		System.out.println("");
		System.out.println("");
		System.out.println("validindayhourmin={0}\u5929{1}\u5C0F\u65F6{2}\u5206\u949F\u540E\u6388\u6743\u751F\u6548");
		System.out.println("validexpireindayhourmin={0}\u5929{1}\u5C0F\u65F6{2}\u5206\u949F\u540E\u6388\u6743\u5931\u6548");
		System.out.println("");
		System.out.println("validinhourmin={0}\u5C0F\u65F6{1}\u5206\u949F\u540E\u6388\u6743\u751F\u6548");
		System.out.println("validexpireinhourmin={0}\u5C0F\u65F6{1}\u5206\u949F\u540E\u6388\u6743\u5931\u6548");
		System.out.println("");
		System.out.println("validinmin={0}\u5206\u949F\u540E\u6388\u6743\u751F\u6548");
		System.out.println("validexpireinmin={0}\u5206\u949F\u540E\u6388\u6743\u5931\u6548");
		System.out.println("");
		System.out.println("confirmdeleteprivilege=\u786E\u5B9A\u8981\u5220\u9664\u8BE5\u6388\u6743\u5417\uFF1F");
		System.out.println("");
		System.out.println("errorraise=\u51FA\u9519\u4E86");
		System.out.println("errmsg_errcode=\u51FA\u9519\u4E86,\u9519\u8BEF\u4EE3\u7801\uFF1A{0}");
		System.out.println("errmsg_notexists=\u60A8\u627E\u7684\u4E1C\u897F\u4E0D\u5B58\u5728\uFF0C\u6216\u8005\u5DF2\u7ECF\u88AB\u5220\u9664\u4E86");
		System.out.println("errmsg_phonenumberformaterror=\u8BF7\u8F93\u5165\u6B63\u786E\u7684\u7535\u8BDD\u53F7\u7801");
		System.out.println("errmsg_inputphonenumber=\u8BF7\u8F93\u5165\u7535\u8BDD\u53F7\u7801");
		System.out.println("errorcode=\u9519\u8BEF\u4EE3\u7801");
		System.out.println("");
		System.out.println("devicenameisrequired=\u8BBE\u5907\u540D\u4E0D\u80FD\u4E3A\u7A7A");
		System.out.println("wildcharnotsupport=\u4E0D\u652F\u6301\u8868\u60C5\u5B57\u7B26");
		System.out.println("devicenamecrash=\u8BBE\u5907\u540D\u5DF2\u5B58\u5728");
		System.out.println("networkerror=\u7F51\u7EDC\u6545\u969C");
		System.out.println("");
		System.out.println("channelnamerequired=\u901A\u9053\u540D\u4E0D\u80FD\u4E3A\u7A7A");
		System.out.println("channelnamerepeated=\u901A\u9053\u540D\u91CD\u590D");
		System.out.println("channelnameexist=\u901A\u9053\u540D\u5DF2\u5B58\u5728");
		System.out.println("partitionnamerequired=\u9632\u533A\u540D\u4E0D\u80FD\u4E3A\u7A7A");
		System.out.println("partitionnamerepeated=\u9632\u533A\u540D\u91CD\u590D");
		System.out.println("partitionnameexist=\u9632\u533A\u540D\u5DF2\u5B58\u5728");
		System.out.println("");
		System.out.println("adddoorlockuser=\u589E\u52A0\u95E8\u9501\u7528\u6237");
		System.out.println("passwordhastobesixmumbers=\u5BC6\u7801\u5FC5\u987B\u662F6\u4E2A\u6570\u5B57");
		System.out.println("passwordhastobeatmostsixmumbers=\u5BC6\u7801\u5FC5\u987B\u662F\u6570\u5B57,\u4E0D\u80FD\u8D85\u8FC76\u4E2A");
		System.out.println("enteratleaseonedefensenumber=\u8BF7\u8F93\u5165\u81F3\u5C11\u4E00\u4E2A\u9632\u62A5\u8B66\u53F7\u7801");
		System.out.println("addpassworduser=\u589E\u52A0\u5BC6\u7801\u7528\u6237");
		System.out.println("addfingerprintuser=\u589E\u52A0\u6307\u7EB9\u7528\u6237");
		System.out.println("addcarduser=\u589E\u52A0IC\u5361\u7528\u6237");
		System.out.println("");
		System.out.println("enterthename=\u8BF7\u8F93\u5165\u59D3\u540D");
		System.out.println("enterpasswordnumberonly=\u8BF7\u8F93\u5165\u5BC6\u7801\uFF0C\u53EA\u80FD\u8F93\u5165\u6570\u5B57");
		System.out.println("alarmuser=\u62A5\u8B66\u7528\u6237");
		System.out.println("displaypassword=\u663E\u793A\u5BC6\u7801");
		System.out.println("enteralarmphonenunmber=\u8BF7\u8F93\u5165\u544A\u8B66\u53F7\u7801");
		System.out.println("selectstartnumber=\u9009\u62E9\u5F00\u59CB\u65F6\u95F4");
		System.out.println("selectendnumber=\u9009\u62E9\u7ED3\u675F\u65F6\u95F4");
		System.out.println("starttime=\u5F00\u59CB\u65F6\u95F4");
		System.out.println("endtime=\u7ED3\u675F\u65F6\u95F4");
		System.out.println("");
		System.out.println("addfailure=\u6DFB\u52A0\u5931\u8D25");
		System.out.println("adddoorlockuserfailure=\u589E\u52A0\u95E8\u9501\u7528\u6237\u5931\u8D25");
		System.out.println("addsuccessfully=\u6DFB\u52A0\u6210\u529F");
		System.out.println("adddoorlockusersuccessfully=\u589E\u52A0\u95E8\u9501\u7528\u6237\u6210\u529F");
		System.out.println("backtodoorlockuserpage=\u4E09\u79D2\u540E\u8FD4\u56DE\u95E8\u9501\u7528\u6237\u9875\u9762");
		System.out.println("");
		System.out.println("deletefailure=\u5220\u9664\u5931\u8D25");
		System.out.println("deletedoorlockuser=\u5220\u9664\u95E8\u9501\u7528\u6237");
		System.out.println("deletedoorlockuserfailure=\u5220\u9664\u95E8\u9501\u7528\u6237\u5931\u8D25");
		System.out.println("editdoorlockuserfailure=\u4FEE\u6539\u95E8\u9501\u7528\u6237\u5931\u8D25");
		System.out.println("editingdoorlockuser=\u6B63\u5728\u4FEE\u6539\uFF0C\u8BF7\u7A0D\u5019");
		System.out.println("");
		System.out.println("deletesuccessfully=\u5220\u9664\u6210\u529F");
		System.out.println("deletedoorlockusersuccessfully=\u5220\u9664\u95E8\u9501\u7528\u6237\u6210\u529F");
		System.out.println("");
		System.out.println("devicebusy=\u8BBE\u5907\u5FD9");
		System.out.println("devicebusytryagainlater=\u8BBE\u5907\u5FD9\uFF0C\u8BF7\u7A0D\u540E\u518D\u8BD5");
		System.out.println("");
		System.out.println("doorlocknotexist=\u95E8\u9501\u4E0D\u5B58\u5728");
		System.out.println("backin3seconds=\u4E09\u79D2\u540E\u8FD4\u56DE");
		System.out.println("");
		System.out.println("swipeyourcard=\u8BF7\u5237\u5361");
		System.out.println("swipecardatthedooraccess=\u8BF7\u5237\u5361");
		System.out.println("enteryourfingerprint=\u8BF7\u8F93\u5165\u6307\u7EB9");
		System.out.println("enterfingerprintonthedoorlock=\u8BF7\u5728\u95E8\u9501\u4E0A\u8F93\u5165\u6307\u7EB9");
		System.out.println("enterfingerprintagain=\u8BF7\u518D\u6B21\u8F93\u5165\u6307\u7EB9");
		System.out.println("enterfingerprintonthedoorlockagain=\u8BF7\u518D\u6B21\u5728\u95E8\u9501\u4E0A\u8F93\u5165\u6307\u7EB9");
		System.out.println("");
		System.out.println("Sendcommand=\u53D1\u9001\u547D\u4EE4");
		System.out.println("commandsending=\u6B63\u5728\u53D1\u9001\u547D\u4EE4");
		System.out.println("");
		System.out.println("editdoorlock=\u4FEE\u6539\u95E8\u9501");
		System.out.println("editsuccessfully=\u4FEE\u6539\u6210\u529F");
		System.out.println("backinonesecond=\u4E00\u79D2\u540E\u8FD4\u56DE");
		System.out.println("editdoorlockuser=\u4FEE\u6539\u95E8\u9501\u7528\u6237");
		System.out.println("");
		System.out.println("wakeupdoorlock=\u8BF7\u5524\u9192\u95E8\u9501");
		System.out.println("wakeupdoorlockin30seconds=\u8BF7\u572830\u79D2\u5185\u5524\u9192\u95E8\u9501");
		System.out.println("");
		System.out.println("gateway=\u7F51\u5173");
		System.out.println("addac=\u6DFB\u52A0\u7A7A\u8C03");
		System.out.println("adddehumidificationdevice=\u6DFB\u52A0\u9664\u6E7F\u8BBE\u5907");
		System.out.println("addventilationdevice=\u6DFB\u52A0\u65B0\u98CE\u8BBE\u5907");
		System.out.println("addgateway=\u589E\u52A0\u7F51\u5173");
		System.out.println("gatewayidrequired=\u7F51\u5173id\u4E0D\u80FD\u4E3A\u7A7A");
		System.out.println("checkcoderequired=\u6821\u9A8C\u7801\u4E0D\u80FD\u4E3A\u7A7A");
		System.out.println("gatewaynamerequired=\u7F51\u5173\u540D\u79F0\u4E0D\u80FD\u4E3A\u7A7A");
		System.out.println("gatewayoffline=\u7F51\u5173\u79BB\u7EBF");
		System.out.println("");
		System.out.println("gatewayid=\u7F51\u5173ID");
		System.out.println("checkcode=\u6821\u9A8C\u7801");
		System.out.println("gatewayname=\u7F51\u5173\u540D\u79F0");
		System.out.println("operatesuccessfully=\u64CD\u4F5C\u6210\u529F");
		System.out.println("");
		System.out.println("creategatewayqrcode=\u521B\u5EFA\u7F51\u5173\u4E8C\u7EF4\u7801");
		System.out.println("gatewaytype=\u7F51\u5173\u7C7B\u578B");
		System.out.println("airbox=\u7A7A\u6C14\u76D2\u5B50");
		System.out.println("lock=\u95E8\u9501");
		System.out.println("writeintodatabase=\u5199\u5165\u6570\u636E\u5E93");
		System.out.println("instruction=\u8BF4\u660E");
		System.out.println("qrcodeid=\u4E8C\u7EF4\u7801ID");
		System.out.println("alreadexist=\u5DF2\u5B58\u5728");
		System.out.println("qrcodestring=\u4E8C\u7EF4\u7801\u4E32");
		System.out.println("alreadwirteintodatabase=\u5DF2\u5165\u5E93");
		System.out.println("notwriteintodatabase=\u672A\u5165\u5E93");
		System.out.println("");
		System.out.println("phonenumber=\u624B\u673A\u53F7");
		System.out.println("username=\u7528\u6237\u540D");
		System.out.println("");
		System.out.println("phonenumbershort=\u624B\u673A\u53F7");
		System.out.println("usernameshort=\u7528\u6237\u540D");
		System.out.println("");
		System.out.println("addlockuseronlockitself=\u8BF7\u4ECE\u95E8\u9501\u6DFB\u52A0\u7528\u6237\u5E76\u5F00\u95E8");
		System.out.println("noprivilege=\u6CA1\u6709\u6743\u9650");
		System.out.println("onceonly=\u4EC5\u4E00\u6B21\u6709\u6548");
		System.out.println("");
		System.out.println("inputpassword1=\u8F93\u5165\u5BC6\u7801,\u6216\u7559\u7A7A");
		System.out.println("return=\u8FD4\u56DE");
		System.out.println("");
		System.out.println("operation_success=\u64cd\u4f5c\u6210\u529f");
		System.out.println("mailregist_title=\u90ae\u7bb1\u6ce8\u518c");
		System.out.println("resetpassword_title=\u627e\u56de\u5bc6\u7801");
		System.out.println("resetpassword_newpassword=\u65b0\u5bc6\u7801");
		System.out.println("resetpassword_comfirmpassword=\u786e\u8ba4\u5bc6\u7801");
		System.out.println("wakeupneo=\u64CD\u4F5C\u6210\u529F\uFF0C\u5524\u9192\u8BBE\u5907\u540E\u751F\u6548");
		System.out.println("wakeupdevice=\u8BF7\u5148\u5524\u9192\u8BBE\u5907");
		System.out.println("timeout=\u8D85\u65F6");
		System.out.println("targetnotexist=\u76EE\u6807\u4E0D\u5B58\u5728");
		System.out.println("unsupportrequest=\u4E0D\u652F\u6301\u7684\u8BF7\u6C42");
		System.out.println("resetpassword_comfirmpassword=\u786e\u8ba4\u5bc6\u7801");
		System.out.println("");
		System.out.println("login=\u767B\u5F55");
		System.out.println("loginmode=\u767B\u5F55\u65B9\u5F0F");
		System.out.println("byPhone=\u624B\u673A\u767B\u5F55");
		System.out.println("byEmail=\u90AE\u7BB1\u767B\u5F55");
		System.out.println("account=\u8D26\u53F7");
		System.out.println("password=\u5BC6\u7801");
		System.out.println("email=\u90AE\u7BB1");
		System.out.println("vendorcode=\u5382\u5546\u4EE3\u7801");
		System.out.println("isurpass=\u7ECF\u7EAC");
		System.out.println("dorlink=\u591A\u7075");
		System.out.println("itiger=\u5C0F\u864E");
		System.out.println("loginfailed=\u767B\u5F55\u5931\u8D25");
		System.out.println("emailregist=\u90AE\u7BB1\u6CE8\u518C");
		System.out.println("forgotpassword=\u627E\u56DE\u5BC6\u7801");
		System.out.println("confirmdeletegateway=\u662F\u5426\u5220\u9664\u7F51\u5173");
		System.out.println("gatewayid=\u7F51\u5173ID");
		System.out.println("operate=\u64CD\u4F5C");
		System.out.println("modify=\u4FEE\u6539");
		System.out.println("addDSC=\u6DFB\u52A0DSC");
		System.out.println("device=\u8BBE\u5907");
		System.out.println("modifygateway=\u4FEE\u6539\u7F51\u5173");
		System.out.println("name=\u540D\u79F0");
		System.out.println("deviceid=\u8BBE\u5907ID");
		System.out.println("type=\u7C7B\u578B");
		System.out.println("devicetype1=\u70DF\u611F");
		System.out.println("devicetype2=\u6F0F\u6C34");
		System.out.println("devicetype3=\u71C3\u6C14");
		System.out.println("devicetype4=\u95E8\u78C1");
		System.out.println("devicetype5=\u95E8\u9501");
		System.out.println("devicetype6=\u79FB\u52A8");
		System.out.println("devicetype7=\u5F00\u5173");
		System.out.println("devicetype8=\u4E8C\u901A\u9053\u5F00\u5173");
		System.out.println("devicetype9=\u4E09\u901A\u9053\u5F00\u5173");
		System.out.println("devicetype10=\u62A5\u8B66\u5668");
		System.out.println("devicetype11=\u63D2\u5EA7");
		System.out.println("devicetype12=\u673A\u68B0\u624B\u81C2");
		System.out.println("devicetype13=\u7A97\u5E18");
		System.out.println("devicetype14=\u7A7A\u8C03\u63A7\u5236\u5668");
		System.out.println("devicetype15=\u7535\u8868");
		System.out.println("devicetype16=SOS");
		System.out.println("devicetype17=\u6C34\u8868");
		System.out.println("devicetype18=\u732B\u773C");
		System.out.println("devicetype19=\u9501\u82AF");
		System.out.println("devicetype20=\u8C03\u5149\u706F");
		System.out.println("devicetype22=\u95E8\u7981");
		System.out.println("devicetype23=\u7A7A\u6C14\u76D2\u5B50");
		System.out.println("devicetype24=\u4E00\u952E\u60C5\u666F\u9762\u677F");
		System.out.println("devicetype25=\u4E8C\u952E\u60C5\u666F\u9762\u677F");
		System.out.println("devicetype26=\u4E09\u952E\u60C5\u666F\u9762\u677F");
		System.out.println("devicetype27=\u56DB\u952E\u60C5\u666F\u9762\u677F");
		System.out.println("devicetype28=\u7A7A\u8C03\u5916\u673A");
		System.out.println("devicetype29=\u65B0\u98CE");
		System.out.println("devicetype30=\u5BB6\u5EAD\u5F71\u9662");
		System.out.println("devicetype31=\u4E00\u952E\u65E0\u7535\u6E90\u5F00\u5173");
		System.out.println("devicetype32=\u4E8C\u952E\u65E0\u7535\u6E90\u5F00\u5173");
		System.out.println("devicetype33=\u4E09\u952E\u65E0\u7535\u6E90\u5F00\u5173");
		System.out.println("devicetype34=\u56DB\u952E\u65E0\u7535\u6E90\u5F00\u5173");
		System.out.println("devicetype35=\u516D\u952E\u65E0\u7535\u6E90\u5F00\u5173");
		System.out.println("devicetype36=\u9664\u6E7F");
		System.out.println("devicetype37=\u9664\u6E7F\u5916\u673A");
		System.out.println("devicetype38=\u65B0\u98CE\u5916\u673A");
		System.out.println("devicetype39=\u80CC\u666F\u97F3\u4E50");
		System.out.println("devicetype40=\u65B0\u98CE");
		System.out.println("devicetype41=\u4E8C\u4F4D\u5F00\u5173\u60C5\u666F\u6DF7\u5408\u8BBE\u5907");
		System.out.println("devicetype42=\u4E09\u4F4D\u5F00\u5173\u60C5\u666F\u6DF7\u5408\u8BBE\u5907");
		System.out.println("devicetype43=\u56DB\u4F4D\u5F00\u5173\u60C5\u666F\u6DF7\u5408\u8BBE\u5907");
		System.out.println("devicetype44=\u6295\u5F71\u4EEA");
		System.out.println("devicetype45=\u8BBE\u9632\u64A4\u9632\u8BBE\u5907");
		System.out.println("devicetype46=\u8C03\u8272\u706F");
		System.out.println("devicetype47=DSC\u5B89\u9632\u8BBE\u5907");
		System.out.println("devicetype48=\u4FBF\u643A\u60C5\u666F\u9762\u677F");
		System.out.println("devicetype49=\u4E8C\u952E\u4FBF\u643A\u60C5\u666F\u9762\u677F");
		System.out.println("devicetype50=\u56DB\u952E\u4FBF\u643A\u60C5\u666F\u9762\u677F");
		System.out.println("devicetype51=\u6696\u6C14\u63A7\u5236\u5668");
		System.out.println("devicetypetv=\u7535\u89C6");
		System.out.println("devicetypeac=\u7A7A\u8C03");
		System.out.println("devicetypestb=\u673A\u9876\u76D2");
		System.out.println("devicetypecamera=\u6444\u50CF\u5934");
		System.out.println("country=\u56FD\u5BB6");
		System.out.println("noreadrecord=\u6CA1\u6709\u8BFB\u53D6\u8BB0\u5F55");
		System.out.println("read=\u8BFB\u53D6");
		System.out.println("sensitivity=\u7075\u654F\u5EA6");
		System.out.println("silentseconds=\u9759\u9ED8\u65F6\u95F4");
		System.out.println("low=\u4F4E");
		System.out.println("high=\u9AD8");
		System.out.println("set=\u8BBE\u7F6E");
		System.out.println("seconds=\u79D2");
		System.out.println("refresh=\u5237\u65B0");
		System.out.println("neotips=\u672C\u8BBE\u5907\u662F\u4E00\u4E2A\u7761\u7720\u8BBE\u5907\uFF0C\u7075\u654F\u5EA6\u548C\u9759\u9ED8\u65F6\u95F4\u7684\u8BBE\u7F6E\u3001\u8BFB\u53D6\u547D\u4EE4\uFF0C\u8981\u7B49\u5230\u8BBE\u5907\u4ECE\u7761\u7720\u72B6\u6001\u4E2D\u9192\u6765\u624D\u4F1A\u751F\u6548\uFF0C\u6545\u5F53\u60A8\u8BBE\u7F6E\u5B8C\u540E\u8BF7\u624B\u52A8\u5524\u9192\u8BBE\u5907");
		System.out.println("inputpassword=\u8BF7\u8F93\u5165\u5BC6\u7801,\u4E0D\u4FEE\u6539\u8BF7\u7559\u7A7A");
		System.out.println("return=\u8FD4\u56DE");
		System.out.println("");
		System.out.println("timezone=\u65F6\u533A");
		System.out.println("10016=\u7528\u6237\u4E0D\u5B58\u5728!");
		System.out.println("10010=\u7528\u6237\u540D\u6216\u5BC6\u7801\u9519\u8BEF!");
		System.out.println("10011=\u7528\u6237\u88AB\u51BB\u7ED3!");
		System.out.println("error=\u9519\u8BEF!");
		System.out.println("partitionnumber=\u9632\u533A\u6570");
		System.out.println("partition=\u9632\u533A");	}
	
	public static void main3(String arg[]) throws BufferOverflowException, IOException
	{
		byte[] data = new byte[]{126,102,1,0,24,0,2,0,20,105,82,101,109,111,116,101,51,48,48,54,49,48,48,48,48,48,48,49,54,23,126};
		byte[][] rs = Utils.splitRequest(data, data.length);
		
		for ( int i = 0 ; i < rs.length ; i ++ )
			Utils.print("", rs[i]);
		
        String deviceid = TlvWrap.readString(rs[0], TagDefine.TAG_GATEWAY_DEVICE_ID, TagDefine.TAG_HEAD_LENGTH);
        
        NettyUdpConnectionWrap nbc = new NettyUdpConnectionWrap(null , null);
        
        Remoter r = new Remoter();
        r.setHaslogin(true);  // udp device do not login
        r.setUuid(deviceid);
        
        nbc.setAttachment(r);
        
        for ( int i = 0 ; i < rs.length ; i ++ )
        {
            Utils.print(String.format("Receive data from %s(udp)", r.getUuid()), rs[i]);
            
            new GatewayReportProcessor().processRequest( rs[i], nbc);
        }
	}
	
	public static void main1(String arg[])
	{
		TimeZone tz = TimeZone.getDefault();
		System.out.println(tz.getID());
		System.out.println(tz.getDisplayName());
		System.out.println(Utils.createsecuritytoken(32));
	}
	
	public static void main2(String arg[])
	{	
		Calendar c = Calendar.getInstance();
		System.out.println(Utils.formatTime(c.getTime()));
		
		timezoneTranslate(c , VTID);

		
		System.out.println(Utils.formatTime(c.getTime())); 
		
		timezoneTranslate(c , STID);
		System.out.println(Utils.formatTime(c.getTime())); 
	}
	
	public static void timezoneTranslate(Calendar c , String tozoneid)
	{
		TimeZone ttz = TimeZone.getTimeZone(tozoneid);

		c.add(Calendar.MILLISECOND, -1 * c.get(Calendar.ZONE_OFFSET) - c.get(Calendar.DST_OFFSET));
		 
		c.setTimeZone(ttz);
		
		c.add(Calendar.MILLISECOND, c.get(Calendar.ZONE_OFFSET)) ;
		c.add(Calendar.MILLISECOND, c.get(Calendar.DST_OFFSET));
	}
	
	public static void createSecrityRand()
	{
		SecureRandom sr = new SecureRandom();
		for ( int i = 0 ; i < 48 ; i ++ )
		{
			//System.out.println(String.format("%d  %d  %d %d ", sr.nextInt(32) , sr.nextInt(4) , sr.nextInt(32) ,sr.nextInt(256)));
			System.out.println(String.format("%d ", sr.nextInt(16)));
		}
		//System.out.println(DigestUtils.md5Hex("223456"));
		
	}
}
