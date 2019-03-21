package com.iremote.common.Hibernate;

import java.util.ArrayList;
import java.util.List;

import com.iremote.thirdpart.wcj.vo.Lock;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.transform.Transformers;

public class CriteriaWrap implements QueryWrap{
	
	private String className;
	private String alias ;
	private List<Criterion>  criterion = new ArrayList<Criterion>();
	private List<Order> order = new ArrayList<Order>();
	private int first = 0 ;
	private int maxResult = 0 ;
	private List<String> field = new ArrayList<String>();
	private LockMode lockMode;
	
	public CriteriaWrap(String clsName , String alias)
	{
		this.className = clsName ;
		this.alias = alias ;
	}
	
	public CriteriaWrap(String clsName)
	{
		this.className = clsName ;
	}
	
	public CriteriaWrap addifNotNull(Criterion cri)
	{
		if ( cri == null )
			return this ;
		criterion.add(cri);
		return this ;
	}
	
	public CriteriaWrap add(Criterion cri)
	{
		criterion.add(cri);
		return this ;
	}
	
	public CriteriaWrap addOrder(Order order)
	{
		this.order.add(order);
		return this ;
	}
	
	public CriteriaWrap addFields(String[] field)
	{
		if ( field == null || field.length == 0 )
			return this;
		for ( int i = 0 ; i < field.length ; i ++ )
			this.field.add(field[i]);
		return this ;
	}
	
	public void setFirstResult(int first)
	{
		this.first = first;
	}
	
	public void setMaxResults(int maxResult)
	{
		this.maxResult = maxResult;
	}

	private ProjectionList createProjectionList()
	{
		if ( this.field == null || this.field.size() == 0 )
			return null;
		ProjectionList pl = Projections.projectionList();
		
		for(String f : this.field)
			pl.add(Property.forName(f).as(f));
		
		return pl ;
	}
	
	private Criteria CreateCriteria()
	{
		Criteria cri = null ;

		if ( this.alias == null || this.alias.length() == 0 )
			cri = HibernateUtil.getSession().createCriteria(this.className);
		else 
			cri = HibernateUtil.getSession().createCriteria(this.className , this.alias);

		if (lockMode != null)
			cri.setLockMode(lockMode);
		if ( this.field != null && this.field.size() > 0 )
			cri.setProjection(createProjectionList());
		for(Criterion crt: criterion)
			cri.add(crt);
		for(Order ord : order)
			cri.addOrder(ord);
		return cri ;
	}
	
	public  <T> T uniqueResult()
	{
		Object o = CreateCriteria().uniqueResult() ;
		if ( o == null )
			return null ;
		return (T)o;
	}
	
	@SuppressWarnings("rawtypes")
	public <T> List<T> list( Class clz)
	{
		Criteria cri = CreateCriteria();
		cri.setFirstResult(first);
		if ( maxResult != 0 )
			cri.setMaxResults(maxResult);
		if ( clz != null )
			cri.setResultTransformer(Transformers.aliasToBean(clz));
		return cri.list() ;
	}
	
	public <T> List<T> list()
	{
		return list(null);
	}
	
	public <T> T first()
	{
		List<T> lst = list();
		if ( lst == null || lst.size() == 0 )
			return null ;
		return lst.iterator().next();
	}
	
	public int count()
	{
		Criteria cri = CreateCriteria();
		Object obj = cri.setProjection(Projections.rowCount()).uniqueResult();
		
		int count = 0 ;
		if ( obj instanceof Long )
			count = ((Long)obj).intValue();
		else 
			count = ((Integer)obj).intValue();
			
		return count;
	}

	public List<Criterion> getCriterion() {
		return criterion;
	}

	public String getAlias() {
		return alias;
	}

	public void setLockMode(LockMode lockMode) {
		this.lockMode = lockMode;
	}

	public static void main(String arg[])
	{
//		ProgramDAO dao = new ProgramDAO();
//		
//		Object obj = dao.getSession().createCriteria(Privilegeaction.class.getName())
//													.setProjection(Projections.rowCount())
//													.uniqueResult();
//		Long i = (Long) obj;
//		System.out.println(i.intValue());
//
//		obj = dao.getSession().createCriteria(Privilegeaction.class.getName())
//							  .add(Expression.like("name", "p%"))
//							  .list();
//		List lst = (List)obj ;
//		System.out.println(lst.size());
	}
}
