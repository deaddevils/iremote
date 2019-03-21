package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.Hibernate.HqlWrap;
import com.iremote.domain.Partition;
import org.hibernate.Query;

public class PartitionService extends BaseService<Partition> {
    public Partition query(int partitionid)
    {
        CriteriaWrap cw = new CriteriaWrap(Partition.class.getName());
        cw.add(ExpWrap.eq("partitionid", partitionid));
        return cw.uniqueResult();
    }

	public List<Integer> queryParitionidByPhoneuserList(Collection<Integer> phoneuserList) {
		if (phoneuserList == null || phoneuserList.size() == 0) {
			return new ArrayList<>();
		}
		CriteriaWrap cw = new CriteriaWrap(Partition.class.getName());
		cw.add(ExpWrap.in("phoneuser.phoneuserid", phoneuserList));
		cw.addFields(new String[]{"partitionid"});
		return cw.list();
	}

	public List<Partition> querypartitionbyzwavedeviceid(int zwavedeviceid )
	{
		HqlWrap hw = new HqlWrap();
		hw.add("from Partition where ");
		hw.addifnotnull(" zwavedevice.zwavedeviceid = :zwavedeviceid ", "zwavedeviceid", zwavedeviceid );
		return hw.list();
	}

	public List<Partition> queryParitionsByPhoneuserid(int phoneuserid){
		CriteriaWrap cw = new CriteriaWrap(Partition.class.getName());
		cw.add(ExpWrap.eq("phoneuser.phoneuserid", phoneuserid));
		return cw.list();
	}

	public List<Partition> queryParitionsByPhoneuserid(Collection<Integer> phoneuserid)
	{
		if ( phoneuserid == null || phoneuserid.size() == 0 )
			return new ArrayList<Partition>();
		
		CriteriaWrap cw = new CriteriaWrap(Partition.class.getName());
		cw.add(ExpWrap.in("phoneuser.phoneuserid", phoneuserid));
		return cw.list();
	}
	
	public Partition queryFirstParitionByPhoneuserid(int phoneuserid){
		List<Partition> partitions = queryParitionsByPhoneuserid(phoneuserid);
		if (partitions !=null && partitions.size() != 0){
			return partitions.get(0);
		}
		return null;
	}
	
	public Partition queryParitionByZwavedeviceidAndDscpartitionid(int zwavedeviceid,int dscpartitionid){
		CriteriaWrap cw = new CriteriaWrap(Partition.class.getName());
		cw.add(ExpWrap.eq("zwavedevice.zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("dscpartitionid", dscpartitionid));
		return cw.uniqueResult();
	}

	public void changeOwner(int dest, int orgl) {
		String hql = "update Partition set phoneuserid = :dest where phoneuserid = :orgl";
		Query query = HibernateUtil.getSession().createQuery(hql);
		query.setInteger("dest",  dest);
		query.setInteger("orgl",  orgl);
		query.executeUpdate();
	}
}
