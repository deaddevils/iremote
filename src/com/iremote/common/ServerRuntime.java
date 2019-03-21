package com.iremote.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.iremote.domain.OemProductor;

public class ServerRuntime {

	public final Date ServerStartTime = new Date();
	public int timezone = 8;
	
	public int defaultsmscount = 30 ;
	public int defaultcallcount = 0 ;
	
	private int systemcode;
	private final String defaultkey = "2nv82nf7s92jd";
	private int doorlinkallthirdpartid ;
	private int ametaallthirdpartid;
	private int universalplatform ;
	private Set<String> neoproductorset = new TreeSet<>();
	private Set<String> leedarsonproductorset = new TreeSet<>();
	private Set<String> sirenproductorset = new TreeSet<>();
	private int gatewayheartbeattime = 60 ;

	private String defaultdebugdeviceid;
	private int defaultheartbeatwithwifi;
	private int defaultheartbeatwithgsm;

	private String standardzwaveproductor;

	private String defaultlanguage = "en_US";

	private Integer dahuacameraserverport = 9100;
	private Integer gatewayserverport = 8922;
	private Integer lockserverport = 8923;
	private Integer udplockserverport = 8924;
	private String activemqbrockerurl;
	private String activemqusername;
	private String activemqpassword;

	private String ctccNBiotAppId = "K04f8L7OwiTkKUxf8KgpyZxA1FYa";
	private String ctccNBiotSecret = "lBQh21nK2av38My94CDi5ZBHr04a";
	private int readctccToken = 0 ;
	private String aliiotappkey;
	private String aliiotappsecret;
	private String aliiotprojectres;

	private static ServerRuntime instance = new ServerRuntime();
	private Map<Integer , OemProductor> oemproductormap = new HashMap<>();
	
	public static ServerRuntime getInstance()
	{
		return instance;
	}

	public int getTimezone() {
		return timezone;
	}

	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}

	public int getDefaultsmscount() {
		return defaultsmscount;
	}

	public void setDefaultsmscount(int defaultsmscount) {
		this.defaultsmscount = defaultsmscount;
	}

	public int getDefaultcallcount() {
		return defaultcallcount;
	}

	public void setDefaultcallcount(int defaultcallcount) {
		this.defaultcallcount = defaultcallcount;
	}

	public int getSystemcode() {
		return systemcode;
	}

	public void setSystemcode(int systemcode) {
		this.systemcode = systemcode;
	}

	public Date getServerStartTime() {
		return ServerStartTime;
	}
	public String getDefaultkey() {
		return defaultkey;
	}

	public int getDoorlinkallthirdpartid()
	{
		return doorlinkallthirdpartid;
	}

	public void setDoorlinkallthirdpartid(int doorlinkallthirdpartid)
	{
		this.doorlinkallthirdpartid = doorlinkallthirdpartid;
	}

	public int getAmetaallthirdpartid()
	{
		return ametaallthirdpartid;
	}

	public void setAmetaallthirdpartid(int ametaallthirdpartid)
	{
		this.ametaallthirdpartid = ametaallthirdpartid;
	}

	public int getUniversalplatform()
	{
		return universalplatform;
	}

	public void setUniversalplatform(int universalplatform)
	{
		this.universalplatform = universalplatform;
	}

	public int getGatewayheartbeattime() {
		return gatewayheartbeattime;
	}

	public void setGatewayheartbeattime(int gatewayheartbeattime) {
		this.gatewayheartbeattime = gatewayheartbeattime;
	}

	public Set<String> getNeoproductorset() {
		return neoproductorset;
	}

	public void setNeoproductorset(Set<String> neoproductorset) {
		this.neoproductorset = neoproductorset;
	}
	
	public String getCtccNBiotAppId() {
		return ctccNBiotAppId;
	}

	public void setCtccNBiotAppId(String ctccNBiotAppId) {
		this.ctccNBiotAppId = ctccNBiotAppId;
	}

	public String getCtccNBiotSecret() {
		return ctccNBiotSecret;
	}

	public void setCtccNBiotSecret(String ctccNBiotSecret) {
		this.ctccNBiotSecret = ctccNBiotSecret;
	}

	public int getReadctccToken() {
		return readctccToken;
	}

	public void setReadctccToken(int readctccToken) {
		this.readctccToken = readctccToken;
	}

	public Set<String> getLeedarsonproductorset() {
		return leedarsonproductorset;
	}

	public void setLeedarsonproductorset(Set<String> leedarsonproductorset) {
		this.leedarsonproductorset = leedarsonproductorset;
	}
	public String getDefaultdebugdeviceid() {
		return defaultdebugdeviceid;
	}

	public Set<String> getSirenproductorset() {
		return sirenproductorset;
	}

	public void setSirenproductorset(Set<String> sirenproductorset) {
		this.sirenproductorset = sirenproductorset;
	}
	public void setDefaultdebugdeviceid(String defaultdebugdeviceid) {
		this.defaultdebugdeviceid = defaultdebugdeviceid;
	}

	public int getDefaultheartbeatwithwifi() {
		return defaultheartbeatwithwifi;
	}

	public void setDefaultheartbeatwithwifi(int defaultheartbeatwithwifi) {
		this.defaultheartbeatwithwifi = defaultheartbeatwithwifi;
	}

	public int getDefaultheartbeatwithgsm() {
		return defaultheartbeatwithgsm;
	}

	public void setDefaultheartbeatwithgsm(int defaultheartbeatwithgsm) {
		this.defaultheartbeatwithgsm = defaultheartbeatwithgsm;
	}

	public String getStandardzwaveproductor() {
		return standardzwaveproductor;
	}

	public void setStandardzwaveproductor(String standardzwaveproductor) {
		this.standardzwaveproductor = standardzwaveproductor;
	}

	public String getDefaultlanguage() {
		return defaultlanguage;
	}

	public void setDefaultlanguage(String defaultlanguage) {
		this.defaultlanguage = defaultlanguage;
	}

	public Map<Integer, OemProductor> getOemproductormap() {
		return oemproductormap;
	}

	public Integer getDahuacameraserverport() {
		return dahuacameraserverport;
	}

	public void setDahuacameraserverport(Integer dahuacameraserverport) {
		this.dahuacameraserverport = dahuacameraserverport;
	}

	public Integer getGatewayserverport() {
		return gatewayserverport;
	}

	public void setGatewayserverport(Integer gatewayserverport) {
		this.gatewayserverport = gatewayserverport;
	}

	public Integer getLockserverport() {
		return lockserverport;
	}

	public void setLockserverport(Integer lockserverport) {
		this.lockserverport = lockserverport;
	}

	public Integer getUdplockserverport() {
		return udplockserverport;
	}

	public void setUdplockserverport(Integer udplockserverport) {
		this.udplockserverport = udplockserverport;
	}

	public String getActivemqbrockerurl() {
		return activemqbrockerurl;
	}

	public void setActivemqbrockerurl(String activemqbrockerurl) {
		this.activemqbrockerurl = activemqbrockerurl;
	}

	public String getActivemqusername() {
		return activemqusername;
	}

	public void setActivemqusername(String activemqusername) {
		this.activemqusername = activemqusername;
	}

	public String getActivemqpassword() {
		return activemqpassword;
	}

	public void setActivemqpassword(String activemqpassword) {
		this.activemqpassword = activemqpassword;
	}

	public String getAliiotappkey() {
		return aliiotappkey;
	}

	public void setAliiotappkey(String aliiotappkey) {
		this.aliiotappkey = aliiotappkey;
	}

	public String getAliiotappsecret() {
		return aliiotappsecret;
	}

	public void setAliiotappsecret(String aliiotappsecret) {
		this.aliiotappsecret = aliiotappsecret;
	}

	public String getAliiotprojectres() {
		return aliiotprojectres;
	}

	public void setAliiotprojectres(String aliiotprojectres) {
		this.aliiotprojectres = aliiotprojectres;
	}
}
