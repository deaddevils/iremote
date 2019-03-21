package com.iremote.action.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.TimerHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.common.ServerRuntime;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.constant.CameraProductor;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.RemoteEvent;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.common.jms.vo.InfoChange;
import com.iremote.common.jms.vo.InfraredDeviceEvent;
import com.iremote.common.push.PushMessage;
import com.iremote.device.operate.IOperationTranslator;
import com.iremote.device.operate.OperationTranslatorStore;
import com.iremote.domain.Address;
import com.iremote.domain.Associationscene;
import com.iremote.domain.Camera;
import com.iremote.domain.City;
import com.iremote.domain.Command;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.DeviceExtendInfo;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.PhoneUserAttribute;
import com.iremote.domain.Province;
import com.iremote.domain.Region;
import com.iremote.domain.Remote;
import com.iremote.domain.Room;
import com.iremote.domain.RoomAppliance;
import com.iremote.domain.Scene;
import com.iremote.domain.Timer;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.AddressService;
import com.iremote.service.CameraService;
import com.iremote.service.CityService;
import com.iremote.service.DeviceExtendInfoService;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.PhoneUserAttributeService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.ProvinceService;
import com.iremote.service.RegionService;
import com.iremote.service.RemoteService;
import com.iremote.service.RoomService;
import com.iremote.service.SceneService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.vo.AddressVo;
import com.iremote.vo.Appliance;
import com.iremote.vo.RemoteData;
import com.iremote.vo.merge.AssociationsceneComparator;
import com.iremote.vo.merge.AssociationsceneMerge;
import com.iremote.vo.merge.RoomComparator;
import com.iremote.vo.merge.RoomMerge;
import com.iremote.vo.merge.SceneComparator;
import com.opensymphony.xwork2.Action;

public class SynchronizeDataAction extends QueryDataAction {

	private static Log log = LogFactory.getLog(SynchronizeDataAction.class);
	private static final Set<String> DEVICE_TYPE_DONT_DELETE = new HashSet<String>();
	private static final Set<String> DEVICE_TYPE_DELETE_CHECK = new HashSet<String>();
	
	private String myremote;
	private String myscene;
	private String myrooms;
	private String lastupdatetime;
	
	private boolean updateselfdata = false ;
	
	protected List<InfoChange> infochangeeventlist = new ArrayList<InfoChange>();
	private List<RemoteEvent> deleteremoteeventlist = new ArrayList<RemoteEvent>();
	private List<Timer> deletedtimerlist = new ArrayList<Timer>();
	private List<Timer> newtimerlist = new ArrayList<Timer>();
	
	private SceneService sceneservice = new SceneService();
	private List<ZWaveDevice> deletedzwavedevice = new ArrayList<ZWaveDevice>();
	private List<InfraredDevice> deletedinfrareddevice = new ArrayList<InfraredDevice>();

	@Override
	public String execute()
	{
		sharephoneuserid.add(phoneuser.getPhoneuserid());
		
		initShares();
		update();
		initRemotesip();
		queryApplianceStatus();
		updatedUser();
		devicesharedtome();
		sendInfochangedMessage();
		readVersion();

		sendJMSMessage();
		resetTimer();
		initUserInfo();
		
		return Action.SUCCESS;
	}
	protected void initUserInfo(){
		userinfo.setName(phoneuser.getName());
		userinfo.setAvatar(phoneuser.getAvatar());
		PhoneUserAttributeService puas = new PhoneUserAttributeService();
		PhoneUserAttribute usertitle = puas.querybyphoneuseridandcode(phoneuser.getPhoneuserid(), "hometitle");
		if(usertitle!=null){
			userinfo.setHometitle(usertitle.getValue());
		}
		
	}
	private void resetTimer()
	{
		TimerHelper.cancelTimer(this.deletedtimerlist);
		TimerHelper.addTimer(this.newtimerlist);
	}
	
	private void sendJMSMessage()
	{
		for ( InfoChange ic : infochangeeventlist)
			JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED, ic );
		for ( RemoteEvent re : deleteremoteeventlist)
			JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_DELETE_REMOTE, re );
	}
	
	private void update()
	{
		if ( lastupdatetime == null || lastupdatetime.length() == 0 )
			return ;
		Date d = Utils.parseTime(lastupdatetime) ;
		if ( d == null )
			return ;
		
		if ( phoneuser == null )
			return ;

		this.sendselfdata = false;

		if ( d.before(phoneuser.getLastupdatetime()))
		{
			serverlastupdatetime = Utils.formatTime(phoneuser.getLastupdatetime());
			resultCode = ErrorCodeDefine.SYNCHRONIZED_DATA_CRASH;
			return ;
		}
		
		this.updateselfdata = true;
		if ( myremote != null && myremote.length() > 0 )
		{
			JSONArray jar = (JSONArray)JSON.parse(myremote);
			if ( checkRemoteData(jar) == false )
			{
				resultCode = ErrorCodeDefine.PARMETER_ERROR;
				return ;
			}
			updateRemote(jar);
		}
		
		updateScene();
		
		if ( myrooms != null && myrooms.length() > 0 )
		{
			updateRooms();
		}

		deleteDevice();
		
		//phoneuser.setScene(myscene);
		phoneuser.setLastupdatetime(new Date());
	}
	
	private void deleteDevice()
	{
		ZWaveDeviceService svr = new ZWaveDeviceService();
		for ( ZWaveDevice zd : this.deletedzwavedevice)
		{
			DeviceHelper.clearZwaveDevice(zd);
			svr.delete(zd);
			onDeleteZwaveDevice(zd);
		}
		
		InfraredDeviceService ids = new InfraredDeviceService();
		for ( InfraredDevice id : deletedinfrareddevice)
		{
			DeviceHelper.clearInfraredDevice(id);
			ids.delete(id);
			onDeleteInfraredDevice(id);
		}
	}
	
	public void updateRooms()
	{
		RoomService rs = new RoomService();
		List<Room> dblst = rs.querybyphoneuserid(phoneuser.getPhoneuserid());
		List<Room> pglst = JSON.parseArray(myrooms, Room.class);
		
		for ( Room r : pglst)
		{
			if ( r.getAppliancelist() != null )
				for ( ListIterator<RoomAppliance> it = r.getAppliancelist().listIterator() ; it.hasNext() ; )
				{
					RoomAppliance ra = it.next();
					if ( StringUtils.isBlank(ra.getApplianceid()) 
							|| StringUtils.isBlank(ra.getDeviceid())
							|| StringUtils.isBlank(ra.getMajortype()))
						it.remove();
				}
		}
		
		List<Room> rlst = new ArrayList<Room>();
		rlst.addAll(dblst);
		
		Utils.merge(dblst, pglst, new RoomComparator() , new RoomMerge());
		
		for ( Room r : dblst)
		{
			if ( r.getPhoneuserid() == 0 )
			{
				r.setPhonenumber(phoneuser.getPhonenumber());
				r.setPhoneuserid(phoneuser.getPhoneuserid());
			}
			for ( RoomAppliance ra :r.getAppliancelist())
			{
				if ( ra.getRoom() == null )
					ra.setRoom(r);
				fillRoomApplianceId(ra);
			}
			rs.saveOrUpdate(r);
		}
		
		rlst.removeAll(dblst);
		
		for ( Room r : rlst)
			rs.delete(r);
	}
	
	private void fillRoomApplianceId(RoomAppliance ra)
	{
		if ( IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE.equals(ra.getMajortype()) && ra.getZwavedeviceid() == null )
		{
			ZWaveDeviceService zds = new ZWaveDeviceService();
			ZWaveDevice zd = zds.querybydeviceidapplianceid(ra.getDeviceid(), ra.getApplianceid());
			if ( zd != null )
				ra.setZwavedeviceid(zd.getZwavedeviceid());
		}
		else if ( IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED.equals(ra.getMajortype()) && ra.getInfrareddeviceid() == null )
		{
			InfraredDeviceService ids = new InfraredDeviceService();
			InfraredDevice id = ids.query(ra.getDeviceid(), ra.getApplianceid());
			if ( id != null )
				ra.setInfrareddeviceid(id.getInfrareddeviceid());
		}
		else if ( IRemoteConstantDefine.DEVICE_MAJORTYPE_CAMERA.equals(ra.getMajortype()) && ra.getCameraid() == null )
		{
			CameraService cs = new CameraService();
			Camera c = cs.query(ra.getDeviceid(), ra.getApplianceid());
			if ( c != null )
				ra.setCameraid(c.getCameraid());
		}
	}
	
	public void updateScene()
	{
		if ( myscene != null && myscene.length() > 0 )
		{
			JSONArray jar = (JSONArray)JSON.parse(myscene);
			for ( int i = 0 ; i < jar.size() ; i ++ )
			{
				JSONObject jr = jar.getJSONObject(i);
				jr.put("phonenumber", phoneuser.getPhonenumber());
			}
			myscene = jar.toJSONString();
			updateScene(myscene);
		}
	}
	
	public void updateScene(String scenejson)
	{
		SceneService ss = new SceneService();
		List<com.iremote.domain.Scene> olst = ss.queryScenebyPhoneuserid(phoneuser.getPhoneuserid());
		
		List<com.iremote.domain.Scene> lst = JSON.parseArray(scenejson, com.iremote.domain.Scene.class);
		clearid(lst);
		
		List<com.iremote.domain.Scene> rl = Utils.merge(olst, lst, new SceneComparator(), new SceneComparator());
		
		for ( com.iremote.domain.Scene s : olst )
		{
			s.setPhoneuserid(phoneuser.getPhoneuserid());
			s.setScenetype(IRemoteConstantDefine.SCENE_TYPE_SCENE);
			
			if ( s.getCommandlist() != null )
			{
				for ( Command c : s.getCommandlist())
				{
					c.setScene(s);
					fillcommand(c);
				}
				removebadcommand(s.getCommandlist());
			}
			ss.saveOrUpdate(s);
		}
		
		for ( com.iremote.domain.Scene s : rl )
			ss.delete(s);
	}
	
	private void clearid(List<com.iremote.domain.Scene> lst)
	{
		if ( lst == null )
			return ;
		for (com.iremote.domain.Scene s : lst)
		{
			s.setScenedbid(0);
			if ( s.getAssociationscenelist() != null )
			{
				for ( Associationscene as : s.getAssociationscenelist())
					clearAssociationsceneid(as);
			}
			if ( s.getTimerlist() != null )
			{
				for ( Timer t : s.getTimerlist())
					clearTimerid(t);
			}
			if ( s.getCommandlist() != null )
			{
				for ( Command c : s.getCommandlist())
					clearCommandid(c);
			}
		}
	}
	
	private void clearAssociationsceneid(Associationscene as)
	{
		if ( as == null )
			return ;
		as.setAssociationsceneid(0);
		if ( as.getScene() != null )
			as.getScene().setScenedbid(0);
		if ( as.getCommandlist() != null )
		{
			for (Command c : as.getCommandlist())
				clearCommandid(c);
		}
	}
	
	private void clearTimerid(Timer t)
	{
		if ( t == null )
			return ;
		t.setTimerid(0);
		if ( t.getScene() != null )
			t.getScene().setScenedbid(0);
	}
	
	private void clearCommandid(Command c)
	{
		if ( c == null )
			return ;
		c.setCommandid(0);
		if ( c.getScene() != null )
			c.getScene().setScenedbid(0);
		if ( c.getAssociationscene() != null )
			c.getAssociationscene().setAssociationsceneid(0);
	}
	
	private void clearid(RemoteData d)
	{
		if ( d.getAppliancelist() != null )
		{
			for ( Appliance a : d.getAppliancelist())
			{
				if ( a.getAssociationscenelist() != null )
				{
					for ( Associationscene as : a.getAssociationscenelist())
						clearAssociationsceneid(as);
				}
				if ( a.getTimer() != null )
				{
					for ( Timer t : a.getTimer())
						clearTimerid(t);
				}
				
			}
		}
	}
	
	private boolean checkRemoteData(JSONArray jar)
	{
		if ( jar.size() != 0 )
			return true ;
		
		RemoteService rs = new RemoteService();
		if ( rs.queryRemotecountByPhoneuserid(phoneuser.getPhoneuserid()) > 1 )
			return false ;
		return true;
	}
	
	protected void updateRemote(JSONArray jar)
	{
		IremotepasswordService svr = new IremotepasswordService();
		Set<String> mr = new HashSet<String>();

		for ( int i = 0 ; i < jar.size() ; i ++ )
		{
			JSONObject jr = jar.getJSONObject(i);
			RemoteData d = JSON.toJavaObject(jr, RemoteData.class);
			clearid(d);
			
			String deviceid = d.getDeviceid();
			if ( deviceid == null )
				continue;
			Remote r = svr.getIremotepassword(deviceid);
			if ( r == null || r.getPhoneuserid() == null || phoneuser.getPhoneuserid() != r.getPhoneuserid())
			{
				if ( !deviceid.startsWith("V"))
					continue;
				r = createVirtualRemote(deviceid);
				svr.save(r);
			}
			r.setName(d.getName());
			//r.setData(jr.toJSONString());
			r.setLastupdatetime(new Date());
			mr.add(deviceid);
			
			updateZWaveAppliance(r , d.getAppliancelist());
			updateInfraredAppliance(r , d.getAppliancelist());
			updateCamera(r , d.getAppliancelist());
			
			infochangeeventlist.add(new InfoChange(r.getDeviceid() , new Date() , 0));
		}
		
		List<Remote> lst = svr.querybyPhoneUserid(Arrays.asList(phoneuser.getPhoneuserid()));
		for ( Remote r : lst )
		{
			if ( !mr.contains(r.getDeviceid()) )
			{
				r.setPhonenumber(null);
				r.setPhoneuserid(null);
			
				onRemoveRemote(r);
				deleteremoteeventlist.add( new RemoteEvent(r.getDeviceid() , new Date(),phoneuser.getPhoneuserid() , 0));
			}
		}
	}
	
	protected Remote createVirtualRemote(String deviceid)
	{
		Remote r = new Remote();
		r.setDeviceid(deviceid);
		r.setCreatetime(new Date());
		r.setLastupdatetime(new Date());
		r.setPhonenumber(phoneuser.getPhonenumber());
		r.setPhoneuserid(phoneuser.getPhoneuserid());
		r.setStatus(IRemoteConstantDefine.REMOTE_STATUS_ONLINE);
		return r;
	}
	
	protected void onRemoveRemote(Remote remote)
	{
		
	}
	
	protected void onAddZwaveDevice(ZWaveDevice device)
	{
		if (  IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(device.getDevicetype()))
		{
			device.setCapability(new ArrayList<DeviceCapability>());
			device.getCapability().add(new DeviceCapability(device , 4));
			if ( ServerRuntime.getInstance().getSystemcode() != IRemoteConstantDefine.PLATFORM_NORTH_AMERICAN )
				device.getCapability().add(new DeviceCapability(device , 1));
		}
		DeviceHelper.readDeviceProductor(device);
	}
	
	private void saveDeviceExtendInfo(ZWaveDevice device , Appliance a)
	{
		DeviceExtendInfo dei = new DeviceExtendInfo();
		dei.setZwavedeviceid(device.getZwavedeviceid());
		dei.setZwaveproductormessage(JSON.toJSONString(a.getAppendmessage()));
		
		DeviceExtendInfoService svr = new DeviceExtendInfoService();
		svr.save(dei);
	}
	
	protected void onDeleteZwaveDevice(ZWaveDevice device)
	{
//		this.deletedtimerlist.addAll(device.getTimer());
//		deleteTimerScene(device.getTimer());
		
//		SceneService ss = new SceneService();
//		if ( device.getAssociationscenelist() != null )
//			for ( Associationscene as : device.getAssociationscenelist())
//				if ( as.getScene() != null )
//					ss.delete(as.getScene());
		
		ZWaveDeviceEvent zde = new ZWaveDeviceEvent(device.getZwavedeviceid() , device.getDeviceid() , device.getNuid() ,IRemoteConstantDefine.EVENT_DELETE_ZWAVE_DEVICE, new Date() , 0);
		zde.setWarningstatuses(device.getWarningstatuses());
		zde.setDevicetype(device.getDevicetype());
		zde.setApplianceid(device.getApplianceid());
		JMSUtil.sendmessage(IRemoteConstantDefine.EVENT_DELETE_ZWAVE_DEVICE,zde);
	}
	
	protected void onAddInfraredDevice(InfraredDevice device)
	{
		
	}
	
	protected void onDeleteInfraredDevice(InfraredDevice device)
	{
//		this.deletedtimerlist.addAll(device.getTimer());
//		deleteTimerScene(device.getTimer());
		JMSUtil.sendmessage(IRemoteConstantDefine.EVENT_DELETE_INFRARED_DEVICE, new InfraredDeviceEvent(device.getInfrareddeviceid() , device.getDeviceid() , device.getApplianceid()));
	}
	
	protected void updateInfraredAppliance(Remote remote , List<Appliance> appliancelist)
	{
		String deviceid = remote.getDeviceid();
		InfraredDeviceService svr = new InfraredDeviceService();
		
		List<InfraredDevice> lst = svr.querybydeviceid(deviceid);
		List<InfraredDevice> remainlst = new ArrayList<InfraredDevice>();
		
		for ( Appliance a : appliancelist )
		{
			if ( !IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED .equals(a.getMajortype()))
				continue;
			
			InfraredDevice d = findbyapplianceid(lst ,a.getApplianceid());
			
			boolean newdevice = false ;
			if ( d == null )
			{
				newdevice = true;
				d = new InfraredDevice();
			}
			else 
				remainlst.add(d);
		
			d.setDeviceid(deviceid);
			d.setApplianceid(a.getApplianceid());
			d.setCodeid(a.getCodeid());
			d.setCodeindex(a.getCodeindex());
			d.setCodelibery(a.getCodelibery());
			d.setControlmodeid(a.getControlmodeid());
			d.setDevicetype(a.getDevicetype());
			d.setMajortype(a.getMajortype());
			d.setName(a.getName());
			d.setProductorid(a.getProductorid());
			
			setDeviceTimer(d , a);
			
			svr.saveOrUpdate(d);
			
			if ( newdevice == true )
				onAddInfraredDevice(d);
		}
		
		lst.removeAll(remainlst);
		
		this.deletedinfrareddevice.addAll(lst);
//		for ( InfraredDevice d : lst )
//		{
//			DeviceHelper.clearInfraredDevice(d);
//			svr.delete(d);
//			onDeleteInfraredDevice(d);
//		}
		
		HibernateUtil.getSession().flush();
	}
	
	protected void updateZWaveAppliance(Remote remote , List<Appliance> appliancelist)
	{
		String deviceid = remote.getDeviceid();
		
		ZWaveDeviceService svr = new ZWaveDeviceService();
		List<ZWaveDevice> lst = svr.querybydeviceid(deviceid);
		List<ZWaveDevice> remainlst = new ArrayList<ZWaveDevice>();
				
		for ( Appliance a : appliancelist )
		{
			if ( !IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE.equals(a.getMajortype()))
				continue;
			
			if ( a.getNuid() == 0 )
			{
				log.error("nuid can't be 0 ");
				continue;
			}
			
			boolean newdevice = false ;
			ZWaveDevice z = findbynuid(lst ,a.getNuid());
			if ( z == null )
			{
				newdevice = true;
				z = new ZWaveDevice();
			}
			else 
				remainlst.add(z);
			
			setZWaveDeviceData(z , a , newdevice);
			z.setDeviceid(deviceid);
			
			svr.saveOrUpdate(z);

			if ( newdevice == true )
			{
				onAddZwaveDevice(z);
				saveDeviceExtendInfo(z , a);
			}
		}
		
		lst.removeAll(remainlst);
		
		for ( ListIterator<ZWaveDevice> lit = lst.listIterator() ; lit.hasNext() ; )
		{
			ZWaveDevice zd = lit.next();
			if ( DEVICE_TYPE_DONT_DELETE.contains(zd.getDevicetype()))
				lit.remove();
			else if ( DEVICE_TYPE_DELETE_CHECK.contains(zd.getDevicetype()) && zd.getNuid() > 1000 )
				lit.remove();
		}
		
		deletedzwavedevice.addAll(lst);
		
////		CommandService cs = new CommandService();
//		for ( ZWaveDevice z : lst )
//		{
//			DeviceHelper.clearZwaveDevice(z);
//			svr.delete(z);
////			cs.deletebyApplianceid(z.getApplianceid());
//			onDeleteZwaveDevice(z);
//		}
		
		HibernateUtil.getSession().flush();
	}
	
	protected void setZWaveDeviceData(ZWaveDevice z , Appliance a , boolean newdevice )
	{
		if ( newdevice )
		{
			z.setStatus(Utils.getDeviceDefaultStatus(a.getDevicetype()));
			z.setStatuses(Utils.getDeviceDefaultStatuses(a.getDevicetype()));
			z.setBattery(100);
			
			setZWavedeviceProductor(z , a);
		}

		updateAssociation(z , a , newdevice);
		setDeviceTimer(z , a);
		setCapablity(z,a);
		
		z.setApplianceid(a.getApplianceid());
		z.setNuid(a.getNuid());
		z.setDevicetype(a.getDevicetype());
		z.setName(a.getName());
	}
	
	private void setZWavedeviceProductor(ZWaveDevice z , Appliance a)
	{
		int[] am = a.getAppendmessage();
		if ( am == null || am.length == 0 )
			return ;
		byte[] bam =new byte[am.length];
		for ( int i = 0 ; i < am.length ; i ++ )
			bam[i] = (byte)am[i];
		
		byte[] bp = TlvWrap.readTag(bam, TagDefine.TAG_PRODUCTOR, 0);
		if ( bp != null )
			z.setProductor(JWStringUtils.toHexString(bp));
		bp = TlvWrap.readTag(bam, TagDefine.TAG_MODEL, 0);
		if ( bp != null )
			z.setModel(JWStringUtils.toHexString(bp));
	}  
	
	
	protected void updateAssociation(ZWaveDevice z , Appliance a , boolean newdevice)
	{
		SceneService ss = new SceneService();
		
		if ( !newdevice ) 
		{
			if ( z.getAssociationscenelist() == null )
				z.setAssociationscenelist(new ArrayList<Associationscene>());
			
			List<Associationscene> al = new ArrayList<Associationscene>();
			
			for ( Associationscene as : z.getAssociationscenelist() )
				if ( as.getScenetype() == IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION )
					al.add(as);
			
			List<Associationscene> lst =Utils.merge(al, a.getAssociationscenelist(), new AssociationsceneComparator() , new AssociationsceneMerge() );

			for ( Associationscene ra : lst )
				if ( ra.getScenetype() == IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION )
				{
					z.getAssociationscenelist().remove(ra);
					if ( ra.getScene() != null )
						ss.delete(ra.getScene());
				}
			for ( Associationscene ra : al )
				if ( !z.getAssociationscenelist().contains(ra))
				{
					ra.setScenetype(IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION);
					z.getAssociationscenelist().add(ra);
				}
		}
		
		if ( z.getAssociationscenelist() != null )
		{
			for ( Associationscene as : z.getAssociationscenelist())
			{
				if ( as.getScenetype() != IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION )
					continue;
				if ( as.getZwavedevice() == null )
					as.setZwavedevice(z);
				
				Association2scene(z , as);
				
				if ( as.getScene().getCommandlist() != null )
				{
					for ( Command c : as.getScene().getCommandlist())
					{
						if ( c.getAssociationscene() == null )
							c.setAssociationscene(as);
						if ( c.getScene() == null )
							c.setScene(as.getScene());
						if ( StringUtils.isNotEmpty(c.getStarttime()))
							c.setStartsecond(Utils.time2second(c.getStarttime()));
						if ( StringUtils.isNotEmpty(c.getEndtime()))
							c.setEndsecond(Utils.time2second(c.getEndtime()));
						fillcommand(c);
					}
					removebadcommand(as.getScene().getCommandlist());
				}
				ss.saveOrUpdate(as.getScene());
			}
		}
	}
	
	private void removebadcommand(List<Command> lst)
	{
		ListIterator<Command> it = lst.listIterator();
		for ( ; it.hasNext(); )
		{
			Command c = it.next() ;
			if ( c.getZwavecommand() == null && c.getZwavecommands() == null && c.getInfraredcode() == null && c.getLaunchscenedbid() == null )
				it.remove();
		}
	}
	
	private void Association2scene(ZWaveDevice z ,Associationscene as)
	{
		if ( as.getScene() == null )
		{
			as.setScenetype(IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION);
			as.setScene(new Scene());
			
			as.getScene().setAssociationscenelist(Arrays.asList(new Associationscene[]{as}));
			//as.getScene().setPhoneuserid(this.phoneuser.getPhoneuserid());
			as.getScene().setSceneid(Utils.createtoken());
			as.getScene().setScenetype(IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION);
			sceneservice.save(as.getScene());
			
			IOperationTranslator ot = OperationTranslatorStore.getInstance().getOperationTranslator(z.getMajortype(), z.getDevicetype());
			if ( ot != null )
			{
				ot.setZWavedevice(z);
				ot.setStatus(as.getStatus());
				as.setDevicestatus(ot.getDeviceStatus());
				as.setOperator(as.getOperator());
			}
		}
		if ( as.getScene().getCommandlist() == null )
			as.getScene().setCommandlist(new ArrayList<Command>());
		as.getScene().getCommandlist().addAll(as.getCommandlist());
	}
	
	private void fillcommand(Command c)
	{
		if ( c.getZwavedevice() == null && c.getInfrareddevice() == null ) 
		{
			if ( StringUtils.isNotBlank(c.getDeviceid()) && StringUtils.isNotBlank(c.getApplianceid()))
			{
				InfraredDeviceService ifs = new InfraredDeviceService();
				c.setInfrareddevice(ifs.query(c.getDeviceid(), c.getApplianceid()));
				if ( c.getInfrareddevice() == null )
				{
					ZWaveDeviceService zds = new ZWaveDeviceService();
					c.setZwavedevice(zds.querybydeviceidapplianceid(c.getDeviceid(), c.getApplianceid()));
				}
			}
		}
		
		IOperationTranslator ot = null ;
		if ( c.getZwavedevice() != null )
		{
			ot = OperationTranslatorStore.getInstance().getOperationTranslator(c.getZwavedevice().getMajortype(), c.getZwavedevice().getDevicetype());
			if ( ot != null )
			{
				ot.setZWavedevice(c.getZwavedevice());
				if ( c.getZwavecommand() != null )
					ot.setCommand(c.getZwavecommand());
				else if ( c.getZwavecommands() != null )
					ot.setCommand(c.getZwavecommands());
				if ( StringUtils.isNotBlank(c.getCommandjsonstr()))
					ot.setCommandjson(c.getCommandjsonstr());
				if ( c.getZwavecommand() == null 
						&& c.getZwavecommands() == null 
						&& (c.getLaunchscenedbid() == null || c.getLaunchscenedbid() == 0 ) )
				{
					c.setZwavecommand(ot.getCommand());
					c.setZwavecommands(ot.getCommands());
				}
			}
		}
		else if ( c.getInfrareddevice() != null )
		{
			ot = OperationTranslatorStore.getInstance().getOperationTranslator(c.getInfrareddevice().getMajortype(), c.getInfrareddevice().getDevicetype());
			if ( ot != null )
			{
				ot.setInfrareddevice(c.getInfrareddevice());
				ot.setCommand(c.getInfraredcode());
				if ( StringUtils.isNotBlank(c.getCommandjsonstr()))
					ot.setCommandjson(c.getCommandjsonstr());
				if ( c.getInfraredcode() == null && c.getLaunchscenedbid() == null )
					c.setInfraredcode(ot.getCommand());
			}
		}
		if ( ot != null )
		{
			c.setCommandjsonstr(ot.getCommandjson());
		}
	}
	
	private void setCapablity(ZWaveDevice z , Appliance a)
	{
		if ( a.getCapability() != null )
			for ( DeviceCapability dc : a.getCapability())
				dc.setDevicecapabilityid(0);
		if ( z.getCapability() != null )
		{
			z.getCapability().clear();
			if ( a.getCapability() != null )
				z.getCapability().addAll(a.getCapability());
		}
		else 
			z.setCapability(a.getCapability());
		
		if ( z.getCapability() != null )
			for ( DeviceCapability dc : z.getCapability())
				dc.setZwavedevice(z);
	}
	
	protected void setDeviceTimer(InfraredDevice z , Appliance a)
	{
		if ( z.getTimer() != null )
		{
			deletedtimerlist.addAll(z.getTimer());
			deleteTimerScene(z.getTimer());
			
			z.getTimer().clear();
			if ( a.getTimer() != null )
				z.getTimer().addAll(a.getTimer());
		}
		else 
			z.setTimer(a.getTimer());
		if ( z.getTimer() != null )
		{
			for ( Timer t : z.getTimer())
			{
				if ( t.getZwavedevice() == null )
				{
					t.setInfrareddevice(z);
					this.newtimerlist.add(t);
				}
				timer2Scene(t);
			}
		}
	}
	
	private void deleteTimerScene(List<Timer> lst)
	{
		if ( lst == null || lst.size() == 0 )
			return ;
		for ( Timer t : lst)
		{
			if ( t.getScene() != null )
				sceneservice.delete(t.getScene());
		}
	}

	private void setDeviceTimer(ZWaveDevice z , Appliance a)
	{
		if ( z.getTimer() != null )
		{
			deletedtimerlist.addAll(z.getTimer());
			deleteTimerScene(z.getTimer());
			
			z.getTimer().clear();
			if ( a.getTimer() != null )
				z.getTimer().addAll(a.getTimer());
		}
		else 
			z.setTimer(a.getTimer());
		if ( z.getTimer() != null )
		{
			for ( Timer t : z.getTimer())
			{
				t.setTimerid(0);
				t.setExcutetime(Utils.time2excutetime(t.getTime()));
				if ( t.getZwavedevice() == null )
				{
					t.setZwavedevice(z);
					this.newtimerlist.add(t);
				}
				timer2Scene(t);
			}
		}
	}
	
	private void timer2Scene(Timer t)
	{
		if ( t.getScene() != null )
			return ;
			
		t.setScene(new Scene());
		Scene s = t.getScene();
		s.setSceneid(Utils.createtoken());
		s.setScenetype(IRemoteConstantDefine.SCENE_TYPE_TIMER);
		s.setTimerlist(new ArrayList<Timer>());
		s.getTimerlist().add(t);
		
		sceneservice.save(s);
		
		s.setCommandlist(new ArrayList<Command>());
		
		Command c = new Command();
		s.getCommandlist().add(c);
		c.setScene(s);
		c.setZwavedevice(t.getZwavedevice());
		c.setInfrareddevice(t.getInfrareddevice());
		c.setIndex(0);
		c.setDelay(0);

		if ( t.getZwavedevice() != null )
		{
			c.setApplianceid(t.getZwavedevice().getApplianceid());
			c.setDeviceid(t.getZwavedevice().getDeviceid());
			c.setZwavecommand(t.getZwavecommand());
			
			IOperationTranslator ot = OperationTranslatorStore.getInstance().getOperationTranslator(t.getZwavedevice().getMajortype(), t.getZwavedevice().getDevicetype());
			if ( ot != null )
			{
				ot.setZWavedevice(c.getZwavedevice());
				if ( c.getZwavecommand() != null )
					ot.setCommand(c.getZwavecommand());
				else if ( c.getZwavecommands() != null )
					ot.setCommand(c.getZwavecommands());
				c.setCommandjsonstr(ot.getCommandjson());
			}
		}
		else if ( t.getInfrareddevice() != null )
		{
			c.setApplianceid(t.getInfrareddevice().getApplianceid());
			c.setDeviceid(t.getInfrareddevice().getDeviceid());
			c.setInfraredcode(t.getInfraredcode());
								
			IOperationTranslator ot = OperationTranslatorStore.getInstance().getOperationTranslator(t.getInfrareddevice().getMajortype(), t.getInfrareddevice().getDevicetype());
			if ( ot != null )
			{
				ot.setInfrareddevice(c.getInfrareddevice());
				ot.setCommand(c.getInfraredcode());
				c.setCommandjsonstr(ot.getCommandjson());
			}
		}
		t.setCommandjsonstr(c.getCommandjsonstr());
	}
	
	protected void updateCamera(Remote remote , List<Appliance> appliancelist)
	{
		String deviceid = remote.getDeviceid();
		
		CameraService svr = new CameraService();
		List<Camera> lst = svr.querybydeviceid(deviceid);
		List<Camera> remainlst = new ArrayList<Camera>();
				
		for ( Appliance a : appliancelist )
		{
			if ( !IRemoteConstantDefine.DEVICE_MAJORTYPE_CAMERA.equals(a.getMajortype()))
				continue;

			Camera c = findCamerabyapplianceid(lst ,a.getApplianceid());
			if ( c == null )
			{
				c = new Camera(); 
				c.setEnablestatus(IRemoteConstantDefine.DEVICE_ENABLE_STATUS_ENABLE);
			}
			else 
				remainlst.add(c);
			
			c.setDeviceid(deviceid);
			c.setApplianceid(a.getApplianceid());
			c.setApplianceuuid(a.getApplianceuuid());
			c.setDevicetype(a.getDevicetype());
			c.setName(a.getName());
			c.setProductorid(a.getProductorid());
			c.setName(a.getName());
			
			svr.saveOrUpdate(c);
		}
		
		lst.removeAll(remainlst);
	
		for ( Camera z : lst )
			if ( !CameraProductor.dahualechange.getProductor().equals(z.getProductorid()) )
				svr.delete(z);
		
		HibernateUtil.getSession().flush();
	}
	
	private Camera findCamerabyapplianceid(List<Camera> lst , String applianceid )
	{
		for ( Camera c : lst)
			if ( applianceid.equals(c.getApplianceid()) )
				return c ;
		return null ;
	}
	
	private InfraredDevice findbyapplianceid(List<InfraredDevice> lst , String applianceid )
	{
		for ( InfraredDevice z : lst)
			if ( applianceid.equals(z.getApplianceid()) )
				return z ;
		return null ;
	}
	
	private ZWaveDevice findbynuid(List<ZWaveDevice> lst , int nuid )
	{
		for ( ZWaveDevice z : lst)
			if ( z.getNuid() == nuid )
				return z ;
		return null ;
	}
	
	private void sendInfochangedMessage()
	{
		if ( this.updateselfdata == false )
			return ;
		
		sharetophoneuserid.add(phoneuser.getPhoneuserid());
		
		if ( sharetophoneuserid.size() == 0 )
			return ;

		PhoneUserService pus = new PhoneUserService();
		List<String> al = pus.queryAlias(sharetophoneuserid); 
		
		PushMessage.pushInfoChangedMessage(al.toArray(new String[0]) , phoneuser.getPlatform());
	}

	public void setMyremote(String myremote) {
		this.myremote = myremote;
	}
	public void setMyscene(String myscene) {
		this.myscene = myscene;
	}
	public void setLastupdatetime(String lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}

	public void setMyrooms(String myrooms) {
		this.myrooms = myrooms;
	}

	static 
	{
		DEVICE_TYPE_DELETE_CHECK.add(IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER);
		DEVICE_TYPE_DELETE_CHECK.add(IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK);
		DEVICE_TYPE_DELETE_CHECK.add(IRemoteConstantDefine.DEVICE_TYPE_WALLREADER);
		DEVICE_TYPE_DELETE_CHECK.add(IRemoteConstantDefine.DEVICE_TYPE_GARAGEDOOR);

		DEVICE_TYPE_DONT_DELETE.add(IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER_OUT);
		DEVICE_TYPE_DONT_DELETE.add(IRemoteConstantDefine.DEVICE_TYPE_DEHUMIDIFY);
		DEVICE_TYPE_DONT_DELETE.add(IRemoteConstantDefine.DEVICE_TYPE_AIR_QUALITY);
		DEVICE_TYPE_DONT_DELETE.add(IRemoteConstantDefine.DEVICE_TYPE_DEHUMIDIFY_OUT);
		DEVICE_TYPE_DONT_DELETE.add(IRemoteConstantDefine.DEVICE_TYPE_FRESH_AIR_OUT);
		DEVICE_TYPE_DONT_DELETE.add(IRemoteConstantDefine.DEVICE_TYPE_FRESH_AIR_IN);
		DEVICE_TYPE_DONT_DELETE.add(IRemoteConstantDefine.DEVICE_TYPE_WATER_METER);
		DEVICE_TYPE_DONT_DELETE.add(IRemoteConstantDefine.DEVICE_TYPE_TUJIA_ELECTRIC_METER);

	}
}
