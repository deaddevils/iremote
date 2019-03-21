package com.iremote.common.Hibernate;

import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

@SuppressWarnings("rawtypes")
public class ExpWrap {

	public static SimpleExpression like(String propertyName , Object value)
	{
		
		if (value == null )
			return null ;
		if ( value instanceof String )
		{
			String str = (String)value ;
			str = str.trim();
			if ( str.length() == 0 )
				return null ;
			if ( !str.startsWith("%"))
				str = "%"+str;
			if ( !str.endsWith("%"))
				str += "%";
			return Restrictions.like(propertyName, str).ignoreCase();
		}
		
		return Restrictions.like(propertyName, value).ignoreCase();
	}
	
	public static Criterion in(String propertyName, int[] values)
	{
		if ( values == null || values.length == 0 )
			return null ;
		Integer[] v = new Integer[values.length];
		for ( int i = 0 ; i < values.length; i ++ )
			v[i] = values[i];
		return in( propertyName , v);
	}
	
	public static Criterion in(String propertyName, Object[] values)
	{
		if ( values == null || values.length == 0 )
			return null ;
		return Restrictions.in(propertyName, values);
	}

	public static Criterion in(String propertyName, Collection values)
	{
		if ( values == null || values.size() == 0 )
			return null ;
		return Restrictions.in(propertyName, values);
	}
	
	public static SimpleExpression eqifnotZero(String propertyName , int value)
	{
		if ( value == 0 )
			return null ;
		return Restrictions.eq(propertyName, value);
	}
	
	public static SimpleExpression ignoreCaseEq(String propertyName , Object value)
	{
		if (value == null )
			return null ;
		if ( value instanceof String )
		{
			String str = (String)value ;
			str = str.trim();
			if ( str.length() == 0 )
				return null ;
			return Restrictions.eq(propertyName, str).ignoreCase();
		}
		return Restrictions.eq(propertyName, value).ignoreCase();
	}
	
	public static SimpleExpression eq(String propertyName , Object value)
	{
		if (value == null )
			return null ;
		if ( value instanceof String )
		{
			String str = (String)value ;
			str = str.trim();
			if ( str.length() == 0 )
				return null ;
			return Restrictions.eq(propertyName, str);
		}
		return Restrictions.eq(propertyName, value);
	}
	
   
	public static SimpleExpression ne(String propertyName, Object value)
    {
		if (value == null )
			return null ;
		if ( value instanceof String )
		{
			String str = (String)value ;
			str = str.trim();
			if ( str.length() == 0 )
				return null ;
			return Restrictions.ne(propertyName, str);
		}
		return Restrictions.ne(propertyName, value);
    }

    public static SimpleExpression gt(String propertyName, Object value)
    {
    	if ( value == null)
			return null ;
		if ( value instanceof String )
		{
			String str = (String)value ;
			str = str.trim();
			if ( str.length() == 0 )
				return null ;
			return Restrictions.gt(propertyName, str);
		}    	
		return Restrictions.gt(propertyName, value);
    }
    
    public static SimpleExpression lt(String propertyName, Object value)
    {
    	if ( value == null)
			return null ;
		if ( value instanceof String )
		{
			String str = (String)value ;
			str = str.trim();
			if ( str.length() == 0 )
				return null ;
			return Restrictions.lt(propertyName, str);
		}  
		return Restrictions.lt(propertyName, value);
    }

    public static SimpleExpression le(String propertyName, Object value)
    {
    	if ( value == null)
			return null ;
		if ( value instanceof String )
		{
			String str = (String)value ;
			str = str.trim();
			if ( str.length() == 0 )
				return null ;
			return Restrictions.le(propertyName, str);
		}      	
		return Restrictions.le(propertyName, value);
    }

    public static SimpleExpression ge(String propertyName, Object value)
    {
    	if ( value == null)
			return null ;
		if ( value instanceof String )
		{
			String str = (String)value ;
			str = str.trim();
			if ( str.length() == 0 )
				return null ;
			return Restrictions.ge(propertyName, str);
		}      	
		return Restrictions.ge(propertyName, value);
    }
    
    public static Criterion bw(String propertyName, Object value1 , Object value2)
    {
    	if ( value1 == null || value2 == null)
			return null ;

		return Restrictions.between(propertyName, value1 , value2);
    }
    
    public static Criterion isnull(String propertyName)
    {
    	if ( propertyName == null)
			return null ;
    	return Restrictions.isNull(propertyName);
    }

    public static Criterion not(Criterion expression)
    {
    	if ( expression == null )
    		return null ;
    	return Restrictions.not(expression);
    }
    
    public static Criterion or(Criterion expression1 , Criterion expression2)
    {
    	if ( expression1 == null &&  expression2 == null)
    		return null ;
    	if ( expression1 == null )
    		return expression2;
    	else if ( expression2 == null )
    		return expression1;
    	return Restrictions.or(expression1 , expression2);
    }
    
    public static Criterion or(Criterion[] criterion)
    {
    	if ( criterion == null || criterion.length == 0  )
    		return null ;
    	
    	if ( criterion.length == 1 )
    		return criterion[0] ;
 
    	Disjunction dis = Restrictions.disjunction();
    	for ( int i = 0 ; i <  criterion.length ; i ++ )
    		if ( criterion[i] != null )
    			dis.add(criterion[i]);

    	return dis ;
    }
    
    public static Criterion or(List<Criterion> criterion)
    {
    	if ( criterion == null || criterion.size() == 0  )
    		return null ;
    	
    	if ( criterion.size() == 1 )
    		return criterion.get(0) ;
    	
    	Criterion cri = Restrictions.or(criterion.get(0), criterion.get(1)) ;
    	for ( int i = 2 ; i < criterion.size() ; i ++ )
    		cri = Restrictions.or(cri , criterion.get(i));
    	return cri ;
    }
    
    public static Criterion and(Criterion expression1 , Criterion expression2)
    {
    	if ( expression1 == null &&  expression2 == null)
    		return null ;
    	if ( expression1 == null )
    		return expression2;
    	else if ( expression2 == null )
    		return expression1;
    	return Restrictions.and(expression1 , expression2);
    }
}
