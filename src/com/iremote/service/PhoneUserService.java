package com.iremote.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.Hibernate.HqlWrap;
import com.iremote.domain.PhoneUser;
import com.iremote.vo.RegistUserVO;

public class PhoneUserService {

	public int save(PhoneUser phoneuser)
	{
		return (Integer)HibernateUtil.getSession().save(phoneuser);
	}
	
	public PhoneUser query(int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(PhoneUser.class.getName());
		cw.add(ExpWrap.eq("phoneuserid", phoneuserid));
		return cw.uniqueResult();
	}
	
	public PhoneUser query(String phonenumber,int platform)
	{
		CriteriaWrap cw = new CriteriaWrap(PhoneUser.class.getName());
		cw.add(ExpWrap.eq("phonenumber", phonenumber));
		cw.add(ExpWrap.eq("platform", platform));
		return cw.uniqueResult();
	}
	
	public int queryTotalCount(String name,String phonenumber,String deviceid,Date validfrom,Date validthrough){
		HqlWrap hw = new HqlWrap();
		hw.add("from PhoneUser where 1=1 ");
		hw.addLikeClauseifnotnull("and name like ? ", name);
		hw.addLikeClauseifnotnull("and phonenumber like ? ", phonenumber);
		hw.addifnotnull("and createtime >= ? ", validfrom);
		hw.addifnotnull("and createtime <= ? ", validthrough);
		return hw.count();
	}
	
	public List<PhoneUser> queryPagePhoneUser(String name,String phonenumber,String deviceid,Date validfrom,Date validthrough,int start,int size){
		HqlWrap hw = new HqlWrap();
		hw.add("from PhoneUser where 1=1 ");
		hw.addLikeClauseifnotnull("and name like ? ", name);
		hw.addLikeClauseifnotnull("and phonenumber like ? ", phonenumber);
		hw.addifnotnull("and createtime >= ? ", validfrom);
		hw.addifnotnull("and createtime <= ? ", validthrough);
		hw.setFirstResult(start);
		hw.setMaxResults(size);
		
		
/*		String hql = "select p.name,p.phonenumber,r.deviceid from PhoneUser p left join Remote r on p.phoneuserid=r.phoneuserid where ";		
		if(!StringUtils.isEmpty(name)){
			hql += "and p.name like :name "; 
		}
		if(!StringUtils.isEmpty(phonenumber)){
			hql += "and p.phonenumber like :phonenumber "; 
		}
		if(!StringUtils.isEmpty(deviceid)){
			hql += "and r.deviceid like :deviceid "; 
		}
		Query query = HibernateUtil.getSession().createQuery(hql);
		if(!StringUtils.isEmpty(name)){
			query.setParameter("name", name);
		}		
		if(!StringUtils.isEmpty(phonenumber)){
			query.setParameter("phonenumber", phonenumber); 
		}
		if(!StringUtils.isEmpty(deviceid)){
			query.setParameter("deviceid", deviceid);
		}
		query.setFirstResult(start);
		query.setMaxResults(size);*/
		
		/*SELECT p.name,p.phonenumber,r.deviceid FROM phoneuser p LEFT  JOIN remote r  ON p.phoneuserid=r.phoneuserid WHERE p.name LIKE '%3%' ;*/
		
		/*List<PhoneUser> pus = HibernateUtil.getSession().createQuery("select * from PhoneUser where name like :name and phonenumber like :phonenumber")
                .setParameter("name", "%name%").setParameter("phonenumber", "%phonenumber%").setFirstResult(start).setMaxResults(size)
                .list();*/
		return hw.list();
	}
	
	public PhoneUser query(String countrycode ,String phonenumber , int platform)
	{
		return query(countrycode , phonenumber , new int[]{platform});
	}

	public PhoneUser querybymail(String mail,int platform)
	{
		CriteriaWrap cw = new CriteriaWrap(PhoneUser.class.getName());
		cw.add(ExpWrap.eq("mail", mail));
		cw.add(ExpWrap.eq("platform", platform));
		return cw.uniqueResult();
	}

	public PhoneUser query(String mail , int status , int platform)
	{
		return query(mail , status , new int[]{platform});
	}

	public PhoneUser query(String mail, int status , int[] platform)
	{
		CriteriaWrap cw = new CriteriaWrap(PhoneUser.class.getName());
		cw.add(ExpWrap.eq("mail", mail));
		cw.add(ExpWrap.eq("status", status));
		cw.add(ExpWrap.in("platform", platform));
		return cw.uniqueResult();
	}


	public PhoneUser query(String countrycode ,String phonenumber , int[] platform)
	{
		CriteriaWrap cw = new CriteriaWrap(PhoneUser.class.getName());
		cw.add(ExpWrap.eq("countrycode", countrycode));
		cw.add(ExpWrap.eq("phonenumber", phonenumber));
		cw.add(ExpWrap.in("platform", platform));
		return cw.uniqueResult();
	}
	
	public List<PhoneUser> query(Collection<Integer> phoneuserid)
	{
		if ( phoneuserid == null || phoneuserid.size() == 0 )
			return new ArrayList<PhoneUser>();
		CriteriaWrap cw = new CriteriaWrap(PhoneUser.class.getName());
		cw.add(ExpWrap.in("phoneuserid", phoneuserid));
		return cw.list();
	}

	public List<PhoneUser> querybyfamiliyid(Integer familyid)
	{
		if ( familyid == null )
			return new ArrayList<PhoneUser>();
		CriteriaWrap cw = new CriteriaWrap(PhoneUser.class.getName());
		cw.add(ExpWrap.eq("familyid", familyid));
		return cw.list();
	}

	public List<Integer> queryldListByFamilyId(Integer familyid)
	{
		if ( familyid == null )
			return new ArrayList<>();
		CriteriaWrap cw = new CriteriaWrap(PhoneUser.class.getName());
		cw.add(ExpWrap.eq("familyid", familyid));
		cw.addFields(new String[]{"phoneuserid"});
		return cw.list();
	}
	
	public List<PhoneUser> queryUpdatedUserbyPhoneuserid(Collection<Integer> phoneuserid , Date lastsyntime)
	{
		if ( phoneuserid == null || phoneuserid.size() == 0 )
			return new ArrayList<PhoneUser>();
		CriteriaWrap cw = new CriteriaWrap(PhoneUser.class.getName());
		cw.add(ExpWrap.in("phoneuserid", phoneuserid));
		cw.addifNotNull(ExpWrap.ge("lastupdatetime", lastsyntime));
		return cw.list();
	}
	@Deprecated
	public List<String> queryAliasbyPhoneuseid(Collection<Integer> phoneuserid)
	{
		if ( phoneuserid == null || phoneuserid.size() == 0 )
			return new ArrayList<String>();
		CriteriaWrap cw = new CriteriaWrap(PhoneUser.class.getName());
		cw.add(ExpWrap.in("phoneuserid", phoneuserid));
		cw.addFields(new String[]{"alias"});
		return cw.list();
	}
	
	public List<String> queryAlias(Collection<Integer> phoneuserid)
	{
		if ( phoneuserid == null || phoneuserid.size() == 0 )
			return new ArrayList<String>();
		CriteriaWrap cw = new CriteriaWrap(PhoneUser.class.getName());
		cw.add(ExpWrap.in("phoneuserid", phoneuserid));
		cw.addFields(new String[]{"alias"});
		return cw.list();
	}
	
	public void updatePassword(String countrycode ,String loginname , int platform , String password)
	{
		PhoneUser user = query(countrycode ,loginname , platform);
		if ( user == null )
			return ;
		user.setPassword(password);
	}

	public void updatePassword(String mail ,int status , int platform , String password)
	{
		PhoneUser user = query(mail ,status , platform);
		if ( user == null )
			return ;
		user.setPassword(password);
	}

}
