package com.iremote.action.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.Address;
import com.iremote.domain.AppVersion;
import com.iremote.domain.Associationscene;
import com.iremote.domain.City;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.PhoneUserAttribute;
import com.iremote.domain.Province;
import com.iremote.domain.Region;
import com.iremote.domain.Remote;
import com.iremote.domain.Room;
import com.iremote.domain.UserShare;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveDeviceShare;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.service.AddressService;
import com.iremote.service.AppVersionService;
import com.iremote.service.CameraService;
import com.iremote.service.CityService;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.PhoneUserAttributeService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.ProvinceService;
import com.iremote.service.RegionService;
import com.iremote.service.RoomService;
import com.iremote.service.SceneService;
import com.iremote.service.UserShareService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZWaveDeviceShareService;
import com.iremote.vo.AddressVo;
import com.iremote.vo.Appliance;
import com.iremote.vo.Command;
import com.iremote.vo.IremoteIP;
import com.iremote.vo.RemoteData;
import com.iremote.vo.Scene;
import com.iremote.vo.Share;
import com.iremote.vo.SubDevice;
import com.iremote.vo.Userinfo;
import com.opensymphony.xwork2.Action;

public class QueryDataAction
{
	private static Log log = LogFactory.getLog(QueryDataAction.class);
	private static final Set<String> DEVICE_TYPE_DONT_SHOW = new HashSet<String>();

	protected int resultCode = ErrorCodeDefine.SUCCESS ;
	protected String lastsyntime;
	protected Date dlastsyntime ; 
	protected String serverlastupdatetime;
	protected List<RemoteData> remote = new ArrayList<RemoteData>() ;
	protected List<Scene> scene = new ArrayList<Scene>() ;
	protected List<Room> rooms = new ArrayList<Room>();
	protected List<Share> shares = new ArrayList<Share>();
	protected List<IremoteIP> iremotesip = new ArrayList<IremoteIP>();
	protected List<ZWaveDevice> appliancestatus = new ArrayList<ZWaveDevice>();
	protected AppVersion appversion ;
	protected List<PhoneUser> shareuses = new ArrayList<PhoneUser>();
	
	protected List<Integer> sharephoneuserid = new ArrayList<Integer>();
	protected List<Integer> sharetophoneuserid = new ArrayList<Integer>();
	protected PhoneUser phoneuser;  
	protected boolean sendselfdata = true ;
	protected Userinfo userinfo = new Userinfo();

	public String execute()
	{
		sharephoneuserid.add(phoneuser.getPhoneuserid());
				
		initShares();

		initRemotesip();
		queryApplianceStatus();
		updatedUser();
		devicesharedtome();
		readVersion();

		initUserInfo();
		
		
		return Action.SUCCESS;
	}
	
	protected void initUserInfo(){
		userinfo.setName(phoneuser.getName());
		userinfo.setAvatar(phoneuser.getAvatar());
		PhoneUserAttributeService puas = new PhoneUserAttributeService();
		AddressService as = new AddressService();
		PhoneUserAttribute usertitle = puas.querybyphoneuseridandcode(phoneuser.getPhoneuserid(), "hometitle");
		if(usertitle!=null){
			userinfo.setHometitle(usertitle.getValue());
		}
		Address useraddress = as.queryByPhoneuserid(phoneuser.getPhoneuserid());
		AddressVo addressvo = new AddressVo();
		if(useraddress!=null){
			ResourceBundle rb;
			String language = phoneuser.getLanguage();
			if (IRemoteConstantDefine.DEFAULT_LANGUAGE.equals(language)) {
				rb = ResourceBundle.getBundle("citydata", Locale.SIMPLIFIED_CHINESE);
			} else if (IRemoteConstantDefine.DEFAULT_ZHHK_LANGUAGE.equals(language)) {
				rb = ResourceBundle.getBundle("citydata", Locale.TRADITIONAL_CHINESE);
			} else {
				rb = ResourceBundle.getBundle("citydata", Locale.US);
			}
			RegionService rs = new RegionService();
			ProvinceService ps = new ProvinceService();
			CityService cs = new CityService();
			addressvo.setAddressid(useraddress.getAddressid());
			addressvo.setRegionid(useraddress.getRegionid());
			Region region = rs.queryByRegionid(useraddress.getRegionid());
			if(region!=null){
				try {
					addressvo.setRegionname(rb.getString(region.getCode()));
				} catch (Exception e) {
					addressvo.setRegionname(region.getName());
					log.error(e.getMessage());
				}
			}
			addressvo.setProvinceid(useraddress.getProvinceid());
			Province province = ps.queryByProvinceid(useraddress.getProvinceid());
			if(province!=null){
				try {
					addressvo.setProvincename(rb.getString(province.getCode()));
				} catch (Exception e) {
					addressvo.setProvincename(province.getName());
					log.error(e.getMessage());
				}
			}
			addressvo.setCityid(useraddress.getCityid());
			City city = cs.queryByCityid(useraddress.getCityid());
			if(city!=null){
				try {
					addressvo.setCityname(rb.getString(city.getCode()));
				} catch (Exception e) {
					addressvo.setCityname(city.getName());
					log.error(e.getMessage());
				}
			}
		}
		userinfo.setAddressvo(addressvo);
	}
	
	protected void readVersion()
	{
		AppVersionService avs = new AppVersionService();
		
		appversion = avs.query(phoneuser.getPlatform());
	}
	
	protected void initShares()
	{
		UserShareService svr = new UserShareService();
		
		List<UserShare> lst = svr.querybyShareUser(phoneuser.getPhoneuserid());

		List<Integer> pl = new ArrayList<Integer>();
		
		for ( UserShare us : lst )
		{
			Share s = new Share(us);
			s.setPhonenumber(us.getTouser());
			s.setCountrycode(us.getTousercountrycode());
			s.setDirection(1);
			
			shares.add(s);
			sharetophoneuserid.add(us.getTouserid());
			pl.add(us.getTouserid());
		}
		
		lst = svr.querybytoUser(phoneuser.getPhoneuserid());
		
		for ( UserShare us : lst )
		{
			Share s = new Share(us);
			s.setPhonenumber(us.getShareuser());
			s.setDirection(0);
			
			shares.add(s);
			if ( s.getStatus() == IRemoteConstantDefine.USER_SHARE_STATUS_NORMAL
					&& s.getSharedevicetype() == IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_ALL)
				sharephoneuserid.add(us.getShareuserid());
		}
		
		if ( phoneuser.getFamilyid() != null && phoneuser.getFamilyid() != 0 )
		{
			List<Integer> fpuidl = PhoneUserHelper.queryPhoneuseridbyfamilyid(phoneuser.getFamilyid());
			
			for ( Integer fuid : fpuidl )
			{
				if ( !sharephoneuserid.contains(fuid))
					sharephoneuserid.add(fuid);
				if ( !sharetophoneuserid.contains(fuid))
					sharetophoneuserid.add(fuid);
			}
		}
		
		PhoneUserService pus = new PhoneUserService();
		shareuses = pus.query(pl);
	}
	
	protected void initRemotesip()
	{
		IremotepasswordService svr = new IremotepasswordService();
		List<Remote> lst = svr.querybyPhoneUserid(sharephoneuserid);
		initRemotesip(lst);
	}
	
	private void initRemotesip(List<Remote> lst)
	{
		Set<String> ers = new HashSet<String>();
		
		for ( IremoteIP r : iremotesip)
			ers.add(r.getDeviceid());

		for ( Remote r : lst )
		{
			if ( ers.contains(r.getDeviceid()))
				continue;
			if ( r.getPhoneuserid() == null || r.getPhoneuserid() == 0 )
				continue;
			IremoteIP ip = new IremoteIP();
			try {
				PropertyUtils.copyProperties(ip, r);
			} catch (Throwable e) {
				log.error(e.getMessage() , e);
			} 
			iremotesip.add(ip);
			ers.add(r.getDeviceid());
		}
	}
	
	protected void queryApplianceStatus()
	{
		List<String> didlst = new ArrayList<String>(iremotesip.size());
		for ( IremoteIP r : iremotesip)
			didlst.add(r.getDeviceid());
		
		ZWaveDeviceService svr = new ZWaveDeviceService();
		appliancestatus = svr.querydevicestatusbydeviceid(didlst);
	}

	protected void updatedUser()
	{
		if (this.sendselfdata == false )
			this.sharephoneuserid.remove(new Integer(phoneuser.getPhoneuserid()));
		
		if ( lastsyntime != null && lastsyntime.length() != 0 )
			dlastsyntime = Utils.parseTime(lastsyntime) ;

		if ( this.sharephoneuserid.size() == 0 )
		{
			this.lastsyntime = Utils.formatTime(new Date());
			return ;
		}
		
		this.lastsyntime = Utils.formatTime(new Date());
		
		PhoneUserService svr = new PhoneUserService();
		List<PhoneUser> lst = svr.queryUpdatedUserbyPhoneuserid(sharephoneuserid, dlastsyntime);
		
		List<Integer> updateduser = new ArrayList<Integer>(lst.size());
		
		for ( PhoneUser u : lst )
			updateduser.add(u.getPhoneuserid());
		
		updatedRemote(updateduser);
		updatedRooms();
		updatedScene(updateduser , lst);
	}
	
	private void updatedScene(List<Integer> updateduser , List<PhoneUser> phoneuserlst)
	{
		if ( updateduser == null || updateduser.size() == 0 )
			return ;
		SceneService ss = new SceneService();
		List<com.iremote.domain.Scene> lst = ss.queryScenebyPhoneuserid(updateduser);
		this.scene = new ArrayList<Scene>(lst.size());
		
		for ( com.iremote.domain.Scene s : lst)
		{
			Scene sv = new Scene();
			sv.setIcon(s.getIcon());
			sv.setName(s.getName());
			sv.setSceneid(s.getSceneid());
			sv.setPhonenumber(getPhonenumber(s.getPhoneuserid() , phoneuserlst));
			sv.setScenedbid(s.getScenedbid());
			sv.setAssociationlist(s.getAssociationscenelist());
			sv.setTimerlist(s.getTimerlist());
			sv.setExecutorsetting(s.getExecutorsetting());
			
			if ( s.getCommandlist() != null && s.getCommandlist().size() > 0 )
			{
				sv.setCommandlist(new ArrayList<Command>(s.getCommandlist().size()));
				
				for ( com.iremote.domain.Command c : s.getCommandlist())
				{
					Command cc = new Command();
					cc.setApplianceid(c.getApplianceid());
					cc.setInfrareddeviceid(c.getInfrareddeviceid());
					cc.setZwavedeviceid(c.getZwavedeviceid());
					cc.setLaunchscenedbid(c.getLaunchscenedbid());
					cc.setCommandjson(c.getCommandjson());
					cc.setDelay(c.getDelay());
					cc.setDescription(c.getDescription());
					cc.setDeviceid(c.getDeviceid());
					cc.setIndex(c.getIndex());
					cc.setInfraredcode(transateByteArraytoIntArray(c.getInfraredcode()));
					cc.setZwavecommand(transateByteArraytoIntArray(c.getZwavecommand()));
					cc.setZwavecommands(transateByteArraytoIntArray(c.getZwavecommands()));
					
					sv.getCommandlist().add(cc);
				}
			}
			
			this.scene.add(sv);
		}
	}
	
	private String getPhonenumber(int phoneuserid , List<PhoneUser> lst)
	{
		for ( PhoneUser u : lst )
		{
			if ( u.getPhoneuserid() == phoneuserid )
				return u.getPhonenumber();
		}
		return null ;
	}
	
	private int[] transateByteArraytoIntArray(byte[] b)
	{
		if ( b == null )
			return null ;
		int[] ia = new int[b.length];
		
		for ( int i = 0 ; i < b.length ; i ++ )
			ia[i] = b[i] & 0xff;
		return ia;
	}
	
	private void updatedRooms()
	{
		RoomService rs = new RoomService();
		this.rooms = rs.querybyphoneuserid(sharephoneuserid);
	}
	
	protected void devicesharedtome()
	{
		ZWaveDeviceShareService svr = new ZWaveDeviceShareService();
		
		List<ZWaveDeviceShare> lst = svr.query(phoneuser.getPhoneuserid());  //query device share 
		
		if ( lst == null || lst.size() == 0 ) 
			return ;
		
		Set<String> remotedeviceidset = new HashSet<String>();
		Map<String , Set<Integer>> zwavedeviceidmap = new HashMap<String , Set<Integer>>();
		Map<String , Set<Integer>> infrareddeviceididmap = new HashMap<String , Set<Integer>>();
		
		for ( ZWaveDeviceShare zds : lst )  
		{
			if ( zds.getZwavedeviceid() != null && zds.getZwavedeviceid() != 0 )
				addItem(zwavedeviceidmap ,zds.getDeviceid() ,  zds.getZwavedeviceid());
			else if ( zds.getInfrareddeviceid() != null && zds.getInfrareddeviceid() != 0 )
				addItem(infrareddeviceididmap ,zds.getDeviceid() ,  zds.getInfrareddeviceid());
			
			remotedeviceidset.add(zds.getDeviceid());
		}

		IremotepasswordService ips = new IremotepasswordService();
		List<Remote> rl = ips.getIremotepassword(remotedeviceidset);  // query all remotes which are shared to current user . 
		
		initRemotesip(rl); 
		
		remotedeviceidset.clear();
		
		for (  RemoteData d : remote )
			remotedeviceidset.add(d.getDeviceid());
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		InfraredDeviceService ids = new InfraredDeviceService();
		
		for ( Remote r : rl )
		{
			if ( remotedeviceidset.contains(r.getDeviceid()))
				continue;
			if ( r.getPhoneuserid() == null || r.getPhoneuserid() == 0 )
				continue;

			RemoteData d = new RemoteData() ; //JSON.parseObject(r.getData(), RemoteData.class);
			d.setPhonenumber(r.getPhonenumber());
			d.setDeviceid(r.getDeviceid());
			d.setName(r.getName());
			d.setTimezone(r.getTimezone());
			d.setType(getRemoteType(r));
			d.setCapability(r.getCapability());

			List<Appliance> appliancelist = new ArrayList<Appliance>();
			
			Set<Integer> s = zwavedeviceidmap.get(r.getDeviceid());
			if ( s != null && s.size() > 0 )
			{
				List<ZWaveDevice> zdl = zds.query(s);
									
				for ( ZWaveDevice zd : zdl )
				{
					Appliance a = createAppliance(zd);
					if ( a == null )
						continue;
			
					if ( GatewayUtils.isCobbeLock(r))
						a.setWakeuptype(2);
					appliancelist.add(a);
					this.appliancestatus.add(zd);
				}
			}
			
			s = infrareddeviceididmap.get(r.getDeviceid());
			if ( s != null && s.size() > 0 )
			{
				List<InfraredDevice> idl = ids.query(s);
				for ( InfraredDevice id : idl )
				{
					Appliance a = createAppliance(id);
					if ( a == null )
						continue;

					appliancelist.add(a);
				}
			}
			
			d.setAppliancelist(appliancelist);

			if(d.getAppliancelist().size() > 0 )
				this.remote.add(d);
		}
		
	}
	
	private Appliance createAppliance(Object obj)
	{
		Appliance a = new Appliance();
		try {
			PropertyUtils.copyProperties(a, obj);
		} catch (Throwable e) {
			log.error(e.getMessage() , e);
			return null;
		} 
		return a ;
	}
	
	private void addItem(Map<String , Set<Integer>> map , String deviceid , int id)
	{
		Set<Integer> s = map.get(deviceid);
		if ( s == null )
		{
			s = new HashSet<Integer>();
			map.put(deviceid, s);
		}
		s.add(id);
	}
	
	private int getRemoteType(Remote r)
	{
		for ( String did : Utils.WAKEUP_GATEWAY_ID_PREFIX )
			if ( r.getDeviceid().startsWith(did))
				return IRemoteConstantDefine.IREMOTE_TYPE_COBBE_WIFI_LOCK;
		if ( r.getRemotetype() == 0 )
			return IRemoteConstantDefine.IREMOTE_TYPE_NORMAL;
		return IRemoteConstantDefine.IREMOTE_TYPE_COBBE_WIFI_LOCK;
	}
	
	private void updatedRemote(List<Integer> user)
	{
		if ( user == null || user.size() == 0 )
			return ;
		IremotepasswordService svr = new IremotepasswordService();
		List<Remote> lst = svr.querybyPhoneUserid(user);
		for ( Remote r : lst )
		{
			RemoteData d = new RemoteData();
			d.setDeviceid(r.getDeviceid());
			d.setPhonenumber(r.getPhonenumber());
			d.setTimezone(r.getTimezone());
			d.setName(r.getName());
			d.setType(getRemoteType(r));
			d.setCapability(r.getCapability());
			
			List<Object> lo = new LinkedList<Object>();
			ZWaveDeviceService zds = new ZWaveDeviceService();
			CameraService cs = new CameraService();
			InfraredDeviceService ids = new InfraredDeviceService();

			lo.addAll(zds.querybydeviceid(r.getDeviceid()));
			lo.addAll(cs.querybydeviceid(r.getDeviceid()));
			lo.addAll(ids.querybydeviceid(r.getDeviceid()));
			
			for ( Object obj : lo )
			{
				try {
					Appliance a = new Appliance();
					PropertyUtils.copyProperties(a, obj);
					if ( obj instanceof ZWaveDevice )
					{
						ZWaveDevice zd = ( ZWaveDevice)obj;
						
						if ( DEVICE_TYPE_DONT_SHOW.contains(zd.getDevicetype()))
							continue;
						
						if ( GatewayUtils.isCobbeLock(r))
							a.setWakeuptype(2);
						copysubdevice(a, zd );
						
						
						if ( zd.getAssociationscenelist() != null )
						{
							List<Associationscene> al = new ArrayList<Associationscene>();
							for ( Associationscene as : zd.getAssociationscenelist())
								if ( as.getScenetype() == IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION)
									al.add(as);
							a.setAssociationscenelist(al);
						}
					}
					d.getAppliancelist().add(a);
				} catch (Throwable e) {
					log.error(e.getMessage() , e);
				} 
			}
			
			this.remote.add(d);
		}
	}
	
	private void copysubdevice(Appliance a ,ZWaveDevice z)
	{
		if ( z.getzWaveSubDevices() == null || z.getzWaveSubDevices().size() == 0 )
			return ;
		if ( a.getSubdevice() == null )
			a.setSubdevice(new ArrayList<SubDevice>());
		for ( ZWaveSubDevice zsd : z.getzWaveSubDevices())
			a.getSubdevice().add(new SubDevice(zsd));
	}
	
	public String getLastsyntime() {
		return lastsyntime;
	}
	
	public void setLastsyntime(String lastsyntime) {
		this.lastsyntime = lastsyntime;
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public List<RemoteData> getRemote() {
		return remote;
	}
	public List<Scene> getScene() {
		return scene;
	}
	public List<Share> getShares() {
		return shares;
	}
	public List<IremoteIP> getIremotesip() {
		return iremotesip;
	}

	public List<ZWaveDevice> getAppliancestatus() {
		return appliancestatus;
	}

	public String getServerlastupdatetime() {
		return serverlastupdatetime;
	}

	public AppVersion getAppversion() {
		return appversion;
	}


	public List<Room> getRooms() {
		return rooms;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public Userinfo getUserinfo()
	{
		return userinfo;
	}

	static 
	{

		DEVICE_TYPE_DONT_SHOW.add(IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER_OUT);
		DEVICE_TYPE_DONT_SHOW.add(IRemoteConstantDefine.DEVICE_TYPE_DEHUMIDIFY_OUT);
		DEVICE_TYPE_DONT_SHOW.add(IRemoteConstantDefine.DEVICE_TYPE_FRESH_AIR_OUT);
		
	}
}
