package com.iremote.action.share;

import java.util.*;
import java.util.Set;
import java.util.stream.Collectors;

import com.iremote.action.helper.PhoneUserBlueToothHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.push.PushMessage;
import com.iremote.domain.*;
import com.iremote.service.*;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class DeleteShareAction {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int shareid;
	private PhoneUser phoneuser;
	private List<Integer> familyphoneuserid = new ArrayList<Integer>();
	private static Log log = LogFactory.getLog(DeleteShareAction.class);
	
	public String execute()
	{
		
		UserShareService svr = new UserShareService();
		UserShare su = svr.query(shareid) ;
		
		if ( su == null )
		{
			resultCode = ErrorCodeDefine.SHARE_INVITATION_EXPIRED ;
			return Action.SUCCESS;
		}
		
		PhoneUserService pus = new PhoneUserService();
		PhoneUser shareuser = pus.query(su.getShareuserid()) ;
		PhoneUser touser = pus.query(su.getTouserid());
				
		if ( shareuser == null )
		{
			resultCode = ErrorCodeDefine.SHARE_INVITATION_EXPIRED ;
			return Action.SUCCESS;
		}

        HashSet<Integer> hadModifiedPhoneUserIdList = new HashSet<>();

        if (su.getSharetype() == IRemoteConstantDefine.USER_SHARE_TYPE_FAMILY && su.getStatus() == IRemoteConstantDefine.USER_SHARE_STATUS_NORMAL) {
			familyphoneuserid = PhoneUserHelper.queryPhoneuseridbyfamilyid(shareuser.getFamilyid());

			hadModifiedPhoneUserIdList.addAll(PhoneUserBlueToothHelper.modifyBlueToothPassword(familyphoneuserid, su.getTouserid()));
			hadModifiedPhoneUserIdList.addAll(PhoneUserBlueToothHelper.modifyFamilyShareBlueToothPassword(su.getTouserid(), familyphoneuserid));

			FamilyService fs = new FamilyService();
			Family f = fs.query(shareuser.getFamilyid());

			touser.setFamilyid(null);
			touser.setArmstatus(f.getArmstatus());

			List<UserShare> lst = svr.querybyShareUser(shareuser.getPhoneuserid());

			boolean fe = false;
			for (UserShare u : lst) {
				if (u.getShareid() == shareid)
					continue;
				if (u.getSharetype() != IRemoteConstantDefine.USER_SHARE_TYPE_FAMILY)
					continue;
				fe = true;
				break;
			}
			if (fe == false) {
				shareuser.setFamilyid(null);
				shareuser.setArmstatus(f.getArmstatus());
				fs.delete(f);
			}
		} else if (su.getSharetype() == IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL
				&& su.getSharedevicetype() == IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_SPECIFY) {
			List<Integer> zwaveDeviceIds = su.getUserShareDevices()
					.stream()
					.filter(s -> s.getZwavedeviceid() != null)
					.map(s -> s.getZwavedeviceid()).collect(Collectors.toList());
			List<ZWaveDevice> devices = new ZWaveDeviceService().query(zwaveDeviceIds, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK);
			hadModifiedPhoneUserIdList.addAll(PhoneUserBlueToothHelper.modifyBlueToothDevicePassword(devices, Arrays.asList(su.getTouserid())));

		} else if (su.getSharetype() == IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL
				&& su.getSharedevicetype() == IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_ALL) {

			hadModifiedPhoneUserIdList.addAll(PhoneUserBlueToothHelper.modifyBlueToothPassword(Arrays.asList(shareuser.getPhoneuserid()), touser.getPhoneuserid()));
		}

		deleteDoorlockAssociation(su);
		svr.delete(su);
		
		String alias = null ;
		if ( phoneuser.getPhoneuserid() == (su.getShareuserid()))
			alias = touser.getAlias();
		else 
			alias = shareuser.getAlias();
		
		shareuser.setLastupdatetime(new Date());
		
		if ( familyphoneuserid.size() == 0 ){
            if (hadModifiedPhoneUserIdList.size() == 0) {
                PushMessage.pushShareRequestDeleteMessage(alias, phoneuser.getPlatform(), su.getShareid());
            } else {
                PushMessage.pushInfoChangedMessage(findAlias(hadModifiedPhoneUserIdList, pus), phoneuser.getPlatform());
            }
        }
		else{
            hadModifiedPhoneUserIdList.addAll(familyphoneuserid);
			PushMessage.pushInfoChangedMessage(findAlias(hadModifiedPhoneUserIdList, pus), phoneuser.getPlatform());
		}
		
		return Action.SUCCESS;
	}

    private String[] findAlias(Set<Integer> phoneUserIdList, PhoneUserService pus) {
        List<String> alias = pus.queryAlias(phoneUserIdList);
        return alias.toArray(new String[0]);
    }

	private void deleteDoorlockAssociation(UserShare su){
		if(su.getSharetype()==IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL){//friend share
			int shareuserid = su.getShareuserid();
			int touserid = su.getTouserid();
			deletesceneone(shareuserid,touserid);
			
			deletepartitionone(shareuserid,touserid);
				
		}else{
			if(familyphoneuserid.size()>0){
				List<Integer> faids = familyphoneuserid; 
				if(faids.contains(su.getTouserid())){
					faids.remove(Integer.valueOf(phoneuser.getPhoneuserid()));
				}
				for(Integer shid:faids){
					deletesceneone(shid,su.getTouserid());
					deletepartitionone(shid,su.getTouserid());
				}
			}
		}
	}
	private void deletesceneone(int shareuserid ,int touserid){
		SceneService ss = new SceneService();
		ZWaveDeviceService zds = new ZWaveDeviceService();
		DoorlockAssociationService das = new DoorlockAssociationService();
		List<Scene> shareuserscene = ss.queryScenebyPhoneuserid(shareuserid);
		List<Integer> sceneidlist = new ArrayList<>();
		for(Scene s:shareuserscene){
			sceneidlist.add(s.getScenedbid());
		}
		IremotepasswordService svr = new IremotepasswordService();
		List<Remote> l = svr.querybyPhoneUserid(touserid);
		List<String> rl = new ArrayList<String>(l.size());
		for ( Remote r : l ){
			rl.add(r.getDeviceid());
		}
		List<ZWaveDevice> zwavelist = zds.querybydeviceid(rl);
		
		List<Integer> zwavedeviceid = new ArrayList<>(zwavelist.size());
		for(ZWaveDevice z:zwavelist){
			zwavedeviceid.add(z.getZwavedeviceid());
		}
		List<DoorlockAssociation> dlass = das.queryByZwavedeviceidAndObjtype(zwavedeviceid, 1);
		for(Iterator<DoorlockAssociation> it = dlass.iterator(); it.hasNext(); ){
			DoorlockAssociation next = (DoorlockAssociation)it.next();
			if(sceneidlist.contains(next.getObjid())){
				das.delete(next);
			}
		}
	}
	private void deletepartitionone(int shareuserid,int touserid){
		ZWaveDeviceService zds = new ZWaveDeviceService();
		IremotepasswordService svr = new IremotepasswordService();
		DoorlockAssociationService das = new DoorlockAssociationService();
		Set<Integer> partitionidset = new HashSet<>();
		PartitionService ps = new PartitionService();
		List<Partition> partitionlist1 = ps.queryParitionsByPhoneuserid(shareuserid);
		List<Remote> relist = svr.querybyPhoneUserid(shareuserid);
		List<String> reidlist = new ArrayList<String>(relist.size());
		for ( Remote r : relist ){
			reidlist.add(r.getDeviceid());
		}
		List<ZWaveDevice> dsclist = zds.querybydeviceidandtype(reidlist, IRemoteConstantDefine.DEVICE_TYPE_DSC);
		for(ZWaveDevice z:dsclist){
			if(z.getPartitions()!=null&&z.getPartitions().size()>0){
				for(Partition p:z.getPartitions()){
					partitionidset.add(p.getPartitionid());
				}
			}
		}
		for(Partition p:partitionlist1){
			partitionidset.add(p.getPartitionid());//partitions own by shareuser
		}
		List<Remote> l = svr.querybyPhoneUserid(touserid);
		List<String> rl = new ArrayList<String>(l.size());
		for ( Remote r : l ){
			rl.add(r.getDeviceid());
		}
		List<ZWaveDevice> zwavelist = zds.querybydeviceid(rl);
		List<Integer> zwavedeviceid = new ArrayList<>(zwavelist.size());
		for(ZWaveDevice z:zwavelist){
			zwavedeviceid.add(z.getZwavedeviceid());
		}
		List<DoorlockAssociation> dlasp = das.queryByZwavedeviceidAndObjtype(zwavedeviceid, 2);
		for(Iterator<DoorlockAssociation> it = dlasp.iterator(); it.hasNext(); ){
			DoorlockAssociation next = (DoorlockAssociation)it.next();
			if(partitionidset.contains(next.getObjid())){
				das.delete(next);
			}
		}
	}
	
	
	public int getResultCode() {
		return resultCode;
	}

	public void setShareid(int shareid) {
		this.shareid = shareid;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
	
	
	
}
