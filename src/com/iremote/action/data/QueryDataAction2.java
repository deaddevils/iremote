package com.iremote.action.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.iremote.action.helper.DeviceHelper;
import com.iremote.common.encrypt.AES;
import com.iremote.domain.*;
import com.iremote.service.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.partition.PartitionHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.encrypt.AES;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.Address;
import com.iremote.domain.AppVersion;
import com.iremote.domain.Associationscene;
import com.iremote.domain.Camera;
import com.iremote.domain.City;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.DeviceFunctionVersionCapability;
import com.iremote.domain.DeviceGroup;
import com.iremote.domain.DeviceGroupDetail;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.Partition;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.PhoneUserAttribute;
import com.iremote.domain.Province;
import com.iremote.domain.Region;
import com.iremote.domain.Remote;
import com.iremote.domain.Room;
import com.iremote.domain.RoomAppliance;
import com.iremote.domain.Timer;
import com.iremote.domain.UserShare;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveDeviceShare;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.service.AddressService;
import com.iremote.service.AppVersionService;
import com.iremote.service.CameraService;
import com.iremote.service.CityService;
import com.iremote.service.DeviceFunctionVersionCapabilityService;
import com.iremote.service.DeviceGroupService;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.PartitionService;
import com.iremote.service.PhoneUserAttributeService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.ProvinceService;
import com.iremote.service.RegionService;
import com.iremote.service.RemoteService;
import com.iremote.service.RoomService;
import com.iremote.service.SceneService;
import com.iremote.service.UserShareService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZWaveDeviceShareService;
import com.iremote.service.ZWaveSubDeviceService;
import com.iremote.vo.AddressVo;
import com.iremote.vo.Appliance;
import com.iremote.vo.ApplianceStatus;
import com.iremote.vo.Command;
import com.iremote.vo.DeviceGroupVo;
import com.iremote.vo.IremoteIP;
import com.iremote.vo.LockPwPackageVo;
import com.iremote.vo.PartitionStatusVO;
import com.iremote.vo.PartitionVo;
import com.iremote.vo.RemoteData;
import com.iremote.vo.Scene;
import com.iremote.vo.Share;
import com.iremote.vo.SubDevice;
import com.iremote.vo.Userinfo;
import com.iremote.vo.ZWaveSubDeviceVo;
import com.opensymphony.xwork2.Action;

public class QueryDataAction2 
{
	private static Log log = LogFactory.getLog(QueryDataAction2.class);
	private static final Set<String> DEVICE_TYPE_DONT_SHOW = new HashSet<String>();

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String lastsyntime;
	private PhoneUser phoneuser;  
	private Date dlastsyntime ;
	
	private List<RemoteData> remote = new ArrayList<RemoteData>() ;
	private List<Scene> scene = new ArrayList<Scene>() ;
	private List<Room> rooms = new ArrayList<Room>();
	private List<Share> shares = new ArrayList<Share>();
	private List<IremoteIP> iremotesip = new ArrayList<IremoteIP>();
	private List<ApplianceStatus> appliancestatus = new ArrayList<ApplianceStatus>();
	private AppVersion appversion ;
	private Userinfo userinfo = new Userinfo();
	private List<PhoneUser> sharetomeusers = new ArrayList<PhoneUser>();
	private List<PhoneUser> updatedusers = new ArrayList<PhoneUser>();
	private List<DeviceGroupVo> devicegroups = new ArrayList<DeviceGroupVo>();
	private List<PartitionVo> partition = new ArrayList<PartitionVo>();
	private List<LockPwPackageVo> lockpwpackage = new ArrayList<>();
	private List<PartitionStatusVO> partitionstatus = new ArrayList<>();
	
	private Set<Integer> phoneuserids_shareALLdevicetome = new HashSet<Integer>();
	private Set<Integer> updatedphoneuserids_shareALLdevicetome = new HashSet<Integer>();
	private List<PhoneUser> updatedphoneuser_shareALLdevicetome = new ArrayList<PhoneUser>() ;
	private Set<String> deviceids_shareALLdevicetome = new HashSet<String>();
	
	private Set<Integer> phoneuserids_shareSPECIALdevicetome = new HashSet<Integer>();
	private Set<Integer> updatedphoneuserids_shareSPECIALdevicetome = new HashSet<Integer>();
	private List<PhoneUser> updatedphoneuser_shareSPECIALdevicetome = new ArrayList<PhoneUser>() ;
	private Set<String> deviceids_shareSPECIALdevicetome = new HashSet<String>();
	private Set<Integer> zwavedeviceids_shareSPECIALdevicetome = new HashSet<Integer>();
	private Set<Integer> infrareddeviceids_shareSPECIALdevicetome = new HashSet<Integer>();
	private Set<Integer> cameraids_shareSPECIALdevicetome = new HashSet<Integer>();
	
	private List<Remote> remotes_shareALLdevicetome = new ArrayList<Remote>();
	private List<Remote> remotes_shareSPECIALdevicetome = new ArrayList<Remote>();
	
	private List<ZWaveDevice> zwavedevices_shareALLdevicetome = new ArrayList<ZWaveDevice>();
	private List<ZWaveDevice> zwavedevices_shareSPECIALdevicetome = new ArrayList<ZWaveDevice>();
	
	private List<ZWaveSubDevice> zwavesubdevices_shareALLdevicetome = new ArrayList<ZWaveSubDevice>();
	private List<ZWaveSubDevice> zwavesubdevices_shareSPECIALdevicetome = new ArrayList<ZWaveSubDevice>();
	
	private PartitionService ps = new PartitionService();
	
	
	public String execute()
	{
		HibernateUtil.getSession().setFlushMode(FlushMode.MANUAL);

		initShares();
		
		readDeviceShare();
		
		initRemotesip();
		
		queryApplianceStatus();
		
		initupdatedUser();
		
		updatedDevice();
		
		queryPartitionStatus();
		
		updatedScene();
		
		updatedRoom();
		
		updatedDeviceGroup();
		
		readVersion();
		
		initUserInfo();
		initBlueToothLockPw();
		
		lastsyntime = Utils.formatTime(new Date());
				
		return Action.SUCCESS;
	}
	protected void initBlueToothLockPw() {
		BlueToothPasswordService blueService = new BlueToothPasswordService();
		List<BlueToothPassword> lockpwpackagelist = blueService.findByPhoneUserId(phoneuser.getPhoneuserid());
		if(lockpwpackagelist!=null){
			for(BlueToothPassword b : lockpwpackagelist){
				LockPwPackageVo lockPwPackageVo = new LockPwPackageVo();
				lockPwPackageVo.setZwavedeviceid(b.getZwavedeviceid());
				lockPwPackageVo.setPassword(Base64.encodeBase64String(AES.decrypt(b.getPassword())));
				lockpwpackage.add(lockPwPackageVo);
			}
		}
	
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
					log.error(e.getMessage(),e);
				}
			}
		}
		userinfo.setAddressvo(addressvo);
	}
	private void updatedDeviceGroup()
	{
		Set<Integer> pids = updateduserIds();
		DeviceGroupService dgs = new DeviceGroupService();
		List<DeviceGroup> lst = dgs.querybyphoneuserid(pids);
		
		if ( lst != null && lst.size() > 0 )
		{
			for ( DeviceGroup dg : lst )
			{
				if ( !hasDeviceGroupPrivilege(dg.getZwavedevices()))
					continue;
				
				DeviceGroupVo vo = new DeviceGroupVo();
				try
				{
					String phonenumber = findphonenumber(dg.getPhoneuserid());
					if ( StringUtils.isBlank(phonenumber))
						continue;
					
					PropertyUtils.copyProperties(vo, dg);

					vo.setPhonenumber(phonenumber);
					if ( dg.getPhoneuserid() != phoneuser.getPhoneuserid())
						vo.setZwavedevices(null);
					this.devicegroups.add(vo);
				}
				catch (Throwable e)
				{
					log.error(e.getMessage() , e);
				}
				
			}
		}
	}
	
	private boolean hasDeviceGroupPrivilege(List<DeviceGroupDetail> dgd)
	{
		for ( DeviceGroupDetail d : dgd)
		{
			for ( ApplianceStatus as : this.appliancestatus)
				if ( as.getZwavedeviceid() == d.getZwavedeviceid())
					return true ;
		}
		return false ;
	}

	
	private String findphonenumber(int phoneuserid)
	{
		if ( phoneuserid == phoneuser.getPhoneuserid())
			return phoneuser.getPhonenumber();
		
		for ( PhoneUser pu : sharetomeusers)
			if ( phoneuserid == pu.getPhoneuserid())
				return pu.getPhonenumber();
		return null ;
	}
	
	private void updatedRoom()
	{
		Set<Integer> pids = updateduserIds();
		
		RoomService rs = new RoomService();
		List<Room> rlst = rs.querybyphoneuserid(pids);
		
		for ( Room r : rlst )
		{
			if ( r.getAppliancelist() == null || r.getAppliancelist().size() == 0 )
				continue;
			if ( r.getPhoneuserid()  == phoneuser.getPhoneuserid())
				rooms.add(r);
			else 
			{
				Room r2 = new Room();
				for (RoomAppliance ra : r.getAppliancelist())
				{
					if ( hasPrivilege(ra.getDeviceid() , ra.getZwavedeviceid() , ra.getInfrareddeviceid() , ra.getZwavedeviceid() ))
						 r2.getAppliancelist().add(ra);
				}
				
				if ( r2.getAppliancelist().size() == 0 )
					continue;
				
				r2.setName(r.getName());
				r2.setPhonenumber(r.getPhonenumber());
				r2.setPhoneuserid(r.getPhoneuserid());
				r2.setRoomdbid(r.getRoomdbid());
				r2.setRoomid(r.getRoomid());
				
				rooms.add(r2);
			}
		}
	}
	
	private Set<Integer> updateduserIds()
	{
		Set<Integer> pids = new HashSet<Integer>();
		if ( phoneuser.getLastupdatetime().after(dlastsyntime))
			pids.add(phoneuser.getPhoneuserid());
		
		pids.addAll(updatedphoneuserids_shareALLdevicetome);
		pids.addAll(updatedphoneuserids_shareSPECIALdevicetome);
		
		return pids;
	}
	
	private void updatedScene()
	{
		Set<Integer> pids = updateduserIds();
		
		SceneService ss = new SceneService();
		List<com.iremote.domain.Scene> lst = ss.queryScenebyPhoneuserid(pids);
		
		for ( com.iremote.domain.Scene s : lst )
		{
			Scene sv = createScene(s);
			
			if ( s.getPhoneuserid() == phoneuser.getPhoneuserid() )
			{
				sv.setCommandlist(createSceneCommand(s.getCommandlist()));
				sv.setAssociationlist(s.getAssociationscenelist());
				sv.setTimerlist(s.getTimerlist());
				sv.setConditionlist(s.getConditionlist());
				if ( sv.getCommandlist().size() == 0 )
					continue;
			}
			else if ( phoneuserids_shareALLdevicetome.contains(s.getPhoneuserid()))
			{
				scene.add(sv);
				continue;
			}
			else if ( hasCommandPrivilege(s.getCommandlist()) == false )
				continue ;
			
			scene.add(sv);
		}
	}
	
	private boolean hasCommandPrivilege(List<com.iremote.domain.Command> commandlst)
	{
		for ( com.iremote.domain.Command c : commandlst)
		{
			if ( hasPrivilege(c.getDeviceid() , c.getZwavedeviceid() , c.getInfrareddeviceid() , null ))
				return true ;
		}
		return false ;
	}
	
	
	private boolean hasPrivilege(String deviceid , Integer zwavedeviceid , Integer infrareddeviceid , Integer cameraid)
	{
		if ( deviceids_shareALLdevicetome.contains(deviceid))
			return true ;
		if ( zwavedeviceid != null && zwavedeviceids_shareSPECIALdevicetome.contains(zwavedeviceid))
			return true ;
		if ( infrareddeviceid != null && infrareddeviceids_shareSPECIALdevicetome.contains(infrareddeviceid))
			return true;
		if ( cameraid != null && cameraids_shareSPECIALdevicetome.contains(cameraid))
			return true ;
		return false ;
	}
	
	private Scene createScene(com.iremote.domain.Scene s)
	{
		Scene sv = new Scene();
		sv.setIcon(s.getIcon());
		sv.setName(s.getName());
		sv.setSceneid(s.getSceneid());
		sv.setPhonenumber(getPhonenumber(s.getPhoneuserid()));
		sv.setScenedbid(s.getScenedbid());
		sv.setEnablestatus(s.getEnablestatus());
		sv.setStarttime(s.getStarttime());
		sv.setEndtime(s.getEndtime());
		sv.setWeekday(s.getWeekday());
		sv.setScenenotification(s.getScenenotification());
		sv.setExecutorsetting(s.getExecutorsetting());
		return sv ;
	}
	
	private List<Command> createSceneCommand(List<com.iremote.domain.Command> commandlst)
	{
		List<Command> lst = new ArrayList<Command>();
		
		for ( com.iremote.domain.Command c : commandlst)
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
			
			lst.add(cc);
		}
		return lst ;
	}
	
	private String getPhonenumber(int phoneuserid)
	{
		for ( PhoneUser u : sharetomeusers )
		{
			if ( u.getPhoneuserid() == phoneuserid )
				return u.getPhonenumber();
		}
		return null ;
	}
	
	private void updatedDevice()
	{
		Set<Integer> pids = new HashSet<Integer>();
		if ( phoneuser.getLastupdatetime().after(dlastsyntime))
			pids.add(phoneuser.getPhoneuserid());
		
		pids.addAll(updatedphoneuserids_shareALLdevicetome);
		
		RemoteService rs = new RemoteService();
		//List<Remote> lst = rs.querybyPhoneUserid(pids);
		
		List<Remote> lst = rs.filterbyPhoneUserid(remotes_shareALLdevicetome, pids);
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		CameraService cs = new CameraService();
		InfraredDeviceService ids = new InfraredDeviceService();
		List<Integer> pidlist = new ArrayList<>();
		
		long udtime = System.currentTimeMillis();
		
		List<String> ridlst = rs.getDeviceId(lst);
		//List<ZWaveDevice> zwavedevicelst = zds.querybydeviceid(ridlst);
		if ( zwavedevices_shareALLdevicetome != null && zwavedevices_shareALLdevicetome.size() > 0 )
		{
			for (ZWaveDevice zd: zwavedevices_shareALLdevicetome)
			{
				List<Associationscene> la = zd.getAssociationscenelist();
				if ( la != null && la.size() > 0)
					la.get(0).getCommandlist().size();
				
				zd.getCapability().size();
				zd.getzWaveSubDevices().size();
				zd.getTimer().size();
			}
		}
		List<InfraredDevice> infraredevicelst = ids.querybydeviceid(ridlst);
		List<Camera> cameralist = cs.querybydeviceid(ridlst);

		for ( Remote r : lst )
		{
			RemoteData rd = createRemoteData(r);
			remote.add(rd);
			
			List<ZWaveDevice> zls = zds.filterByDeviceid(zwavedevices_shareALLdevicetome, r.getDeviceid());
			
			for ( ZWaveDevice zd : zls )
			{
				appendAppliance(rd.getAppliancelist() ,createAppliance(r , zd , phoneuser.getPhoneuserid() == r.getPhoneuserid()));
				
				if ( IRemoteConstantDefine.DEVICE_TYPE_DSC.equals(zd.getDevicetype())){
					appendPartition(r.getPhonenumber() , zd);
					List<Partition> partitions = zd.getPartitions();
					if ( partitions != null && partitions.size() > 0 ){
						for ( Partition p : partitions){
							pidlist.add(p.getPartitionid());
						}
					}
				}
			}
			
			List<InfraredDevice> ils = ids.filterByDeviceid(infraredevicelst, r.getDeviceid());
			for ( InfraredDevice id : ils )
				appendAppliance(rd.getAppliancelist() ,createAppliance(id , phoneuser.getPhoneuserid() == r.getPhoneuserid() ));
		
			List<Camera> cls = cs.filterByDeviceid(cameralist, r.getDeviceid());
			for ( Camera c : cls )
				appendAppliance(rd.getAppliancelist() ,createAppliance(c, phoneuser.getPhoneuserid() == r.getPhoneuserid()));	
		}
				
			List<Partition> plist = ps.queryParitionsByPhoneuserid(phoneuser.getPhoneuserid());
			for(Partition p : plist){
				if(p.getZwavedevice()==null&&!pidlist.contains(p.getPartitionid())){
					appendZWavePartition(p.getPhoneuser().getPhonenumber() , p);
					pidlist.add(p.getPartitionid());
				}
			}
			
		for(Integer userid:updatedphoneuserids_shareALLdevicetome){
			List<Partition> pplist = ps.queryParitionsByPhoneuserid(userid);
			for(Partition p: pplist){
				if(p.getZwavedevice()==null&&!pidlist.contains(p.getPartitionid())){
				appendZWavePartition(p.getPhoneuser().getPhonenumber(),p);
				pidlist.add(p.getPartitionid());
				}
			}
		}
		
		if ( updatedphoneuserids_shareSPECIALdevicetome.size() == 0 )
			return ;
			
		List<String> lud = rs.queryDeviceidbyPhoneUserid(updatedphoneuserids_shareSPECIALdevicetome);
		
		Map<String , RemoteData> rdmap = new HashMap<String , RemoteData>();
		Map<String , Remote> rmap = new HashMap<String , Remote>();
		
		List<ZWaveDevice> zls = zds.query(zwavedeviceids_shareSPECIALdevicetome);
		for ( ZWaveDevice zd : zls )
		{
			if ( deviceids_shareALLdevicetome.contains(zd.getDeviceid()))
				continue;
			
			if ( !lud.contains(zd.getDeviceid()))
				continue;
			RemoteData rd = getorCreateRemoteData(rdmap , rmap , zd.getDeviceid());
			appendAppliance(rd.getAppliancelist() ,createAppliance(rmap.get(zd.getDeviceid()) , zd , false));
			if ( IRemoteConstantDefine.DEVICE_TYPE_DSC.equals(zd.getDevicetype())){
				appendPartition(rd.getPhonenumber() , zd);
				List<Partition> partitions = zd.getPartitions();
				if ( partitions != null && partitions.size() > 0 ){
					for ( Partition p : partitions){
						pidlist.add(p.getPartitionid());
					}
				}
			}
		}
		
		List<InfraredDevice> ils = ids.query(infrareddeviceids_shareSPECIALdevicetome);
		for ( InfraredDevice id : ils )
		{
			if ( deviceids_shareALLdevicetome.contains(id.getDeviceid()))
				continue;
			
			if ( !lud.contains(id.getDeviceid()))
				continue;
			RemoteData rd = getorCreateRemoteData(rdmap ,rmap, id.getDeviceid());
			appendAppliance(rd.getAppliancelist() ,createAppliance(id, false));
		}
		
		List<Camera> cls = cs.query(cameraids_shareSPECIALdevicetome);
		for ( Camera c : cls )
		{
			if ( deviceids_shareALLdevicetome.contains(c.getDeviceid()))
				continue;
			
			if ( !lud.contains(c.getDeviceid()))
				continue;
			RemoteData rd = getorCreateRemoteData(rdmap ,rmap, c.getDeviceid());
			appendAppliance(rd.getAppliancelist() ,createAppliance(c));
		}
		
		remote.addAll(rdmap.values());
	}
	
	

	private void appendPartition(String phonenumber,ZWaveDevice zd)
	{
		List<Partition> partitions = zd.getPartitions();
		if ( partitions == null || partitions.size() == 0 )
			return ;
		for ( Partition p : partitions)
		{
			PartitionVo pv = new PartitionVo();
			//PartitionStatusVO psv = new PartitionStatusVO();
			try {
				PropertyUtils.copyProperties(pv, p);
				//PropertyUtils.copyProperties(psv, p);
			} catch (Throwable e) {
				log.error(e.getMessage() , e);
				continue;
			} 
			pv.setPhonenumber(phonenumber);
			pv.setDsczwavedeviceid(zd.getZwavedeviceid());
			pv.setDelay(p.getDelay());
			if ( zd.getzWaveSubDevices() != null && zd.getzWaveSubDevices().size() > 0 )
			{
				List<ZWaveSubDevice> zwavesubdevicelist = new ArrayList<>();
				List<Integer> channelidlist = new ArrayList<>();
				for(ZWaveSubDevice zsd : zd.getzWaveSubDevices()){
					if ( zsd.getPartition() == null || zsd.getPartition().getPartitionid() != p.getPartitionid()||"".equals(zsd.getSubdevicetype()))
						continue;
					channelidlist.add(zsd.getChannelid());
				}
				Collections.sort(channelidlist);
				for(int i=0;i<channelidlist.size();i++){
					for ( ZWaveSubDevice zsd : zd.getzWaveSubDevices() ){
						if(zsd.getChannelid()==channelidlist.get(i)){
							zwavesubdevicelist.add(zsd);
						}
					}
				}
				for ( ZWaveSubDevice zsd : zwavesubdevicelist )
				{
					if ( zsd.getPartition() == null || zsd.getPartition().getPartitionid() != p.getPartitionid())
						continue;
					
					ZWaveSubDeviceVo zsdv = new ZWaveSubDeviceVo();
					zsdv.setChannelid(zsd.getChannelid());
					zsdv.setZwavedeviceid(zd.getZwavedeviceid());
					pv.getZwavedevices().add(zsdv);
				}
			}
			ZWaveDeviceService zds = new ZWaveDeviceService();
			List<ZWaveDevice> zwavelist = zds.querybypartitionid(p.getPartitionid());
			for(ZWaveDevice z : zwavelist){
				ZWaveSubDeviceVo zsdv = new ZWaveSubDeviceVo();
				zsdv.setZwavedeviceid(z.getZwavedeviceid());
				pv.getZwavedevices().add(zsdv);
			}
			CameraService cservice = new CameraService();
			List<Camera> cameralist = cservice.querybypartitionid(p.getPartitionid());
			for(Camera c:cameralist){
				ZWaveSubDeviceVo zsdv = new ZWaveSubDeviceVo();
				zsdv.setCameraid(c.getCameraid());
				pv.getZwavedevices().add(zsdv);
			}
			//this.partitionstatus.add(psv);//TODO alaways fill in
			this.partition.add(pv);
		}
	}
	private void appendZWavePartition(String phonenumber,Partition p){
		PartitionVo pv = new PartitionVo();
		PartitionStatusVO psv = new PartitionStatusVO();
		try {
			PropertyUtils.copyProperties(pv, p);
			PropertyUtils.copyProperties(psv, p);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error(e.getMessage() , e);
		} 
		pv.setPhonenumber(phonenumber);
		pv.setDsczwavedeviceid(null);
		pv.setDelay(p.getDelay());

		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> zwavelist = zds.querybypartitionid(p.getPartitionid());
		for(ZWaveDevice z : zwavelist){
			ZWaveSubDeviceVo zsdv2 = new ZWaveSubDeviceVo();
			zsdv2.setZwavedeviceid(z.getZwavedeviceid());
			pv.getZwavedevices().add(zsdv2);
		}
		CameraService cservice = new CameraService();
		List<Camera> cameralist = cservice.querybypartitionid(p.getPartitionid());
		for(Camera c:cameralist){
			ZWaveSubDeviceVo zsdv1 = new ZWaveSubDeviceVo();
			zsdv1.setCameraid(c.getCameraid());
			pv.getZwavedevices().add(zsdv1);
		}
		//this.partitionstatus.add(psv);
		this.partition.add(pv);
		
	}

	private RemoteData getorCreateRemoteData(Map<String , RemoteData> rdmap , Map<String , Remote> rmap ,String deviceid)
	{
		if ( rdmap.containsKey(deviceid) == false )
		{	
			RemoteService rs = new RemoteService();
			Remote r = rs.getIremotepassword(deviceid);
			
			rdmap.put(deviceid, createRemoteData(r));
			rmap.put(deviceid, r);
		}
		
		return rdmap.get(deviceid);
	}
	
	private void appendAppliance(List<Appliance> lst , Appliance a)
	{
		if ( a == null )
			return ;
		lst.add(a);
	}
	
	private Appliance createAppliance(InfraredDevice id , boolean includeassociation)
	{
		Appliance a = createAppliance(id);
		if ( includeassociation == false )
			a.setTimer(new ArrayList<Timer>());
		return a ;
	}
	
	private Appliance createAppliance(Remote r , ZWaveDevice zd , boolean includeassociation)
	{
		if ( DEVICE_TYPE_DONT_SHOW.contains(zd.getDevicetype()))
			return null ;
		
		Appliance a = createAppliance(zd);

		if ( GatewayUtils.isCobbeLock(r))
			a.setWakeuptype(2);

		copysubdevice(a, zd );
		copyCapability(a, zd);

		
		if ( includeassociation == false )
		{
			a.setAssociationscenelist(new ArrayList<Associationscene>());
			a.setTimer(new ArrayList<Timer>());
		}
		else 
		{
			if ( zd.getAssociationscenelist() != null )
			{
				List<Associationscene> al = new ArrayList<Associationscene>();
				for ( Associationscene as : zd.getAssociationscenelist())
					if ( as.getScenetype() == IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION)
						al.add(as);
				
				a.setAssociationscenelist(al);
			}
			readAssociationscene(a.getAssociationscenelist());
			a.getTimer().size() ; //force hibernate to load data ;
		}
		
		if ( StringUtils.isNotBlank(zd.getFunctionversion()))
		{
			DeviceFunctionVersionCapabilityService dfvs = new DeviceFunctionVersionCapabilityService();
			List<DeviceFunctionVersionCapability> dfl = dfvs.queryByVersion(zd.getDevicetype(), zd.getFunctionversion());
			
			if ( dfl != null )
			{
				for ( DeviceFunctionVersionCapability dfvc : dfl )
				{
					DeviceCapability dc = new DeviceCapability();
					dc.setCapabilitycode(dfvc.getCapabilitycode());
					a.getCapability().add(dc);
				}
			}
		}
		
		return a ;
	}
	
	private void readAssociationscene(List<Associationscene> lst)
	{
		if ( lst == null || lst.size() == 0 )
			return ;
		for ( Associationscene t : lst)
		{
			t.getCommandlist().size();
		}
	}
	
	private Appliance createAppliance(Camera c , boolean includeassociation)
	{
		Appliance a = createAppliance(c);

		if ( includeassociation == false )
		{
			a.setAssociationscenelist(new ArrayList<Associationscene>());
			a.setTimer(new ArrayList<Timer>());
		}
		else 
		{
			if ( c.getAssociationscenelist() != null )
			{
				List<Associationscene> al = new ArrayList<Associationscene>();
				for ( Associationscene as : c.getAssociationscenelist())
					if ( as.getScenetype() == IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION)
						al.add(as);
				a.setAssociationscenelist(al);
			}
		}
		
		return a ;
	}
	
	private Appliance createAppliance(ZWaveDevice zd)
	{
		Appliance a = new Appliance();
		a.setApplianceid(zd.getApplianceid());
		a.setArmstatus(zd.getArmstatus());
		a.setAssociationscenelist(zd.getAssociationscenelist());
		a.setBattery(zd.getBattery());
		a.setCapability(zd.getCapability());
		a.setDeviceid(zd.getDeviceid());
		a.setDevicetype(zd.getDevicetype());
		a.setMajortype(zd.getMajortype());
		a.setName(zd.getName());
		a.setNuid(zd.getNuid());
		a.setPartitionid(zd.getPartitionid());
		a.setStatus(zd.getStatus());
		a.setStatuses(zd.getStatuses());
		a.setTimer(zd.getTimer());
		a.setZwavedeviceid(zd.getZwavedeviceid());
		a.setRawcmd(getRawCmd(zd));
		return a; 
	}

	private List<DeviceRawCmd> getRawCmd(ZWaveDevice zd) {
		return new DeviceRawCmdService().queryByZwaveDeviceId(zd.getZwavedeviceid());
	}

	private Appliance createAppliance(Object obj)
	{
		Appliance a = new Appliance();
		try
		{
			PropertyUtils.copyProperties(a, obj);
		} 
		catch (Throwable t)
		{
			log.error(t.getMessage() , t);
			return null ;
		}
		return a ;
	}
	
	private RemoteData createRemoteData(Remote r )
	{
		RemoteData d = new RemoteData();
		d.setDeviceid(r.getDeviceid());
		d.setPhonenumber(r.getPhonenumber());
		d.setTimezone(r.getTimezone());
		d.setName(r.getName());
		d.setType(getRemoteType(r));
		d.setCapability(r.getCapability());
		r.getCapability().size();  //force hibernate to load capability from database now.
		d.setRemotetype(r.getRemotetype());
		
		d.setStatus(r.getStatus());
		d.setPowertype(r.getPowertype());
		d.setNetwork(r.getNetwork());
		d.setIversion(r.getIversion());
		d.setNetworkintensity(r.getNetworkintensity());
		d.setBattery(r.getBattery());
		return d ;
	}
	
	
	private void initupdatedUser()
	{
		if ( StringUtils.isNotBlank(lastsyntime) )
			dlastsyntime = Utils.parseTime(lastsyntime) ;
		else 
			dlastsyntime = Utils.parseTime("2000-01-01 00:00:00") ;
		
		if ( phoneuser.getLastupdatetime().after(dlastsyntime))
			updatedusers.add(createPhoneUser(phoneuser));

		PhoneUserService pus = new PhoneUserService();
		List<PhoneUser> lst = pus.queryUpdatedUserbyPhoneuserid(phoneuserids_shareALLdevicetome, null);
		
		sharetomeusers.add(createPhoneUser(phoneuser));

		for ( PhoneUser pu : lst )
		{
			sharetomeusers.add(createPhoneUser(pu));
			if ( pu.getLastupdatetime().after(dlastsyntime)  )
			{
				updatedphoneuser_shareALLdevicetome.add(pu);
				updatedphoneuserids_shareALLdevicetome.add(pu.getPhoneuserid());
				updatedusers.add(createPhoneUser(pu));
			}
		}

		lst = pus.queryUpdatedUserbyPhoneuserid(phoneuserids_shareSPECIALdevicetome, null);
		
		for ( PhoneUser pu : lst )
		{
			sharetomeusers.add(createPhoneUser(pu));
			if ( pu.getLastupdatetime().after(dlastsyntime)  )
			{
				updatedphoneuser_shareSPECIALdevicetome.add(pu);
				updatedphoneuserids_shareSPECIALdevicetome.add(pu.getPhoneuserid());
				updatedusers.add(createPhoneUser(pu));
			}
		}
	}
	
	private PhoneUser createPhoneUser(PhoneUser pu )
	{
		PhoneUser p = new PhoneUser();
		p.setPhonenumber(pu.getPhonenumber());
		p.setPhoneuserid(pu.getPhoneuserid());
		p.setSmscount(null);
		p.setCallcount(null);
		return p ;
	}
	
	private void readDeviceShare()
	{
		ZWaveDeviceShareService svr = new ZWaveDeviceShareService();
		
		List<ZWaveDeviceShare> lst = svr.query(phoneuser.getPhoneuserid());  //query device share 
		
		if ( lst == null || lst.size() == 0 ) 
			return ;
		
		for ( ZWaveDeviceShare zds : lst )
		{
			deviceids_shareSPECIALdevicetome.add(zds.getDeviceid());
			if ( zds.getZwavedeviceid() != null )
				zwavedeviceids_shareSPECIALdevicetome.add(zds.getZwavedeviceid());
			else if ( zds.getInfrareddeviceid() != null )
				infrareddeviceids_shareSPECIALdevicetome.add(zds.getInfrareddeviceid());
			else if ( zds.getCameraid() != null )
				cameraids_shareSPECIALdevicetome.add(zds.getCameraid());
		}
		
		RemoteService rs = new RemoteService();
		List<Integer> puids = rs.queryOwnerId(deviceids_shareSPECIALdevicetome);
		phoneuserids_shareSPECIALdevicetome.addAll(puids);

	}
	
	private void initShares()
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
				phoneuserids_shareALLdevicetome.add(us.getShareuserid());
		}
		
		if ( phoneuser.getFamilyid() != null && phoneuser.getFamilyid() != 0 )
		{
			List<Integer> fpuidl = PhoneUserHelper.queryPhoneuseridbyfamilyid(phoneuser.getFamilyid());
			
			if ( fpuidl != null )
				phoneuserids_shareALLdevicetome.addAll(fpuidl);
		}
	}
	
	
	private void initRemotesip()
	{
		Set<Integer> pids = new HashSet<Integer>();
		pids.add(phoneuser.getPhoneuserid());
		pids.addAll(phoneuserids_shareALLdevicetome);
		
		IremotepasswordService svr = new IremotepasswordService();
		remotes_shareALLdevicetome = svr.querybyPhoneUserid(pids);
		initRemotesip(remotes_shareALLdevicetome);
		
		for ( Remote r : remotes_shareALLdevicetome )
			deviceids_shareALLdevicetome.add(r.getDeviceid());
		
		remotes_shareSPECIALdevicetome = svr.getIremotepassword(deviceids_shareSPECIALdevicetome);
		initRemotesip(remotes_shareSPECIALdevicetome);
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
			ip.setBattery(r.getBattery());
			ip.setDeviceid(r.getDeviceid());
			ip.setIp(r.getIp());
			ip.setIversion(r.getIversion());
			ip.setMac(r.getMac());
			ip.setNetwork(r.getNetwork());
			ip.setNetworkintensity(r.getNetworkintensity());
			ip.setPowertype(r.getPowertype());
			ip.setSsid(r.getSsid());
			ip.setStatus(r.getStatus());
			ip.setTemperature(r.getTemperature());
			ip.setVersion(r.getVersion());

			iremotesip.add(ip);
			ers.add(r.getDeviceid());
		}
	}
	
	private void queryApplianceStatus()
	{
		ZWaveDeviceService svr = new ZWaveDeviceService();
		zwavedevices_shareALLdevicetome = svr.querybydeviceid(deviceids_shareALLdevicetome);
		
		ZWaveSubDeviceService zsds = new ZWaveSubDeviceService();
		zwavesubdevices_shareALLdevicetome = zsds.queryByZwaveDeviceid(svr.getZWaveDeviceid(zwavedevices_shareALLdevicetome));
		
		for ( ZWaveDevice zd : zwavedevices_shareALLdevicetome )
			this.appliancestatus.add(createApplianceStatus(zd , zsds.filterByDeviceid(zwavesubdevices_shareALLdevicetome, zd.getZwavedeviceid())));
		
		zwavedevices_shareSPECIALdevicetome = svr.query(zwavedeviceids_shareSPECIALdevicetome);
		zwavesubdevices_shareSPECIALdevicetome = zsds.queryByZwaveDeviceid(svr.getZWaveDeviceid(zwavedevices_shareSPECIALdevicetome));
		
		for ( ZWaveDevice zd : zwavedevices_shareSPECIALdevicetome )
		{
			if ( !deviceids_shareALLdevicetome.contains(zd.getDeviceid()))
				this.appliancestatus.add(createApplianceStatus(zd, zsds.filterByDeviceid(zwavesubdevices_shareSPECIALdevicetome, zd.getZwavedeviceid())));
		}
		
		CameraService cs = new CameraService();
		List<Camera> lc = cs.querybydeviceid(deviceids_shareALLdevicetome);
		
		for ( Camera c : lc )
			this.appliancestatus.add(createApplianceStatus(c));
		
		lc = cs.query(cameraids_shareSPECIALdevicetome);
		
		for ( Camera c : lc )
		{
			if ( !deviceids_shareALLdevicetome.contains(c.getDeviceid()))
				this.appliancestatus.add(createApplianceStatus(c));
		}
	}
	
	private void queryPartitionStatus(){
		Set<Partition> partitions = PartitionHelper.getAllPartitionByPhoneuserid(phoneuser.getPhoneuserid());
		for(Partition p:partitions){
			PartitionStatusVO psv = new PartitionStatusVO();
			try {
				PropertyUtils.copyProperties(psv, p);
			} catch (Throwable e) {
				log.error(e.getMessage() , e);
				continue;
			} 
			this.partitionstatus.add(psv);
		}
	}
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private ApplianceStatus createApplianceStatus(ZWaveDevice src , List<ZWaveSubDevice> zwavesubdevicelst)
	{
		ApplianceStatus as = new ApplianceStatus();

			as.setDeviceid(src.getDeviceid());       
			as.setDevicetype(src.getDevicetype()); 
			as.setNuid(src.getNuid()); 
			as.setZwavedeviceid(src.getZwavedeviceid());
			as.setStatus(src.getStatus());
			as.setStatuses(src.getStatuses()); 
			as.setFstatus(src.getFstatus());
			as.setWarningstatus(src.getWarningstatus());
			as.setWarningstatuses(src.getWarningstatuses());
			as.setBattery(src.getBattery());
			as.setEnablestatus(src.getEnablestatus());
			as.setArmstatus(src.getArmstatus());

		
		if(src.getLastactivetime()!=null){
			as.setLastactivetime(sdf.format(src.getLastactivetime()));
		}

		List<ZWaveSubDevice> zwavesubdevicelist = new ArrayList<>();
		List<ZWaveSubDevice> result = new ArrayList<>();
		
		List<Integer> channelidlist = new ArrayList<>();
		for(ZWaveSubDevice zsd : zwavesubdevicelst){
			if (!DeviceHelper.isUsedSubDeviceStatus(src.getDevicetype()) && zsd.getPartition() == null )
				continue;
			channelidlist.add(zsd.getChannelid());
		}
		Collections.sort(channelidlist);
		for(int i=0;i<channelidlist.size();i++){
			for ( ZWaveSubDevice zsd : zwavesubdevicelst ){
				if(zsd.getChannelid()==channelidlist.get(i)){
					zwavesubdevicelist.add(zsd);
				}
			}
		}
		
		for(ZWaveSubDevice z:zwavesubdevicelist){
			if(DeviceHelper.isUsedSubDeviceStatus(src.getDevicetype()) || !StringUtils.isEmpty(z.getSubdevicetype())){
				result.add(z);
			}
		}
		as.setSubdevice(result);
		return as ;
	}
	
	private ApplianceStatus createApplianceStatus(Object src)
	{
		ApplianceStatus as = new ApplianceStatus();
		try {
			PropertyUtils.copyProperties(as, src);
		} catch (Throwable e) {
			log.error(e.getMessage() , e);
		} 
		return as ;
	}
	
	protected void readVersion()
	{
		AppVersionService avs = new AppVersionService();
		
		appversion = avs.query(phoneuser.getPlatform());
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

	private void copysubdevice(Appliance a ,ZWaveDevice z)
	{
		if ( z.getzWaveSubDevices() == null || z.getzWaveSubDevices().size() == 0 )
			return ;
		if ( a.getSubdevice() == null )
			a.setSubdevice(new ArrayList<SubDevice>());
		for ( ZWaveSubDevice zsd : z.getzWaveSubDevices())
			a.getSubdevice().add(new SubDevice(zsd));
	}

	private void copyCapability(Appliance a ,ZWaveDevice z)
	{
		if (z.getCapability() == null)
			return ;
		a.setCapability(new ArrayList<>());
		for (DeviceCapability deviceCapability : z.getCapability()) {
			if (!IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK.equals(z.getDevicetype())
					|| !IRemoteConstantDefine.DEVICE_CAPABILITY_DIA_ABLE_SHOW_FOR_APP.contains(deviceCapability.getCapabilitycode())){
				a.getCapability().add(new DeviceCapability(
						z,
						deviceCapability.getCapabilitycode(),
						deviceCapability.getCapabilitycode() == IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_KEY_3
								? Base64.encodeBase64String(AES.decrypt(deviceCapability.getCapabilityvalue()))
								: deviceCapability.getCapabilityvalue()
						)
				);
			}
		}
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
	
	public int getResultCode()
	{
		return resultCode;
	}
	public List<RemoteData> getRemote()
	{
		return remote;
	}
	public List<Scene> getScene()
	{
		return scene;
	}
	public List<Room> getRooms()
	{
		return rooms;
	}
	public List<Share> getShares()
	{
		return shares;
	}
	public List<IremoteIP> getIremotesip()
	{
		return iremotesip;
	}
	public List<ApplianceStatus> getAppliancestatus()
	{
		return appliancestatus;
	}
	public AppVersion getAppversion()
	{
		return appversion;
	}
	public Userinfo getUserinfo()
	{
		return userinfo;
	}
	public void setLastsyntime(String lastsyntime)
	{
		this.lastsyntime = lastsyntime;
	}
	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
	
	static 
	{
		DEVICE_TYPE_DONT_SHOW.add(IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER_OUT);
		DEVICE_TYPE_DONT_SHOW.add(IRemoteConstantDefine.DEVICE_TYPE_DEHUMIDIFY_OUT);
		DEVICE_TYPE_DONT_SHOW.add(IRemoteConstantDefine.DEVICE_TYPE_FRESH_AIR_OUT);
	}

	public List<PhoneUser> getSharetomeusers()
	{
		return sharetomeusers;
	}

	public List<PhoneUser> getUpdatedusers()
	{
		return updatedusers;
	}

	public List<DeviceGroupVo> getDevicegroups()
	{
		return devicegroups;
	}

	public List<PartitionVo> getPartition() {
		return partition;
	}

	//App deletes all devices data but syn time while user logout, as a result user could not get any device data when he login again.
	//Comment this parameter to avoid this bug . 
	public String getLastsyntime2() {
		return lastsyntime;
	}

	public List<PartitionStatusVO> getPartitionstatus() {
		return partitionstatus;
	}
	public List<LockPwPackageVo> getLockpwpackage() {
		return lockpwpackage;
	}
	
}
