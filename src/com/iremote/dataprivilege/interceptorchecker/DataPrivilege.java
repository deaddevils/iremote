package com.iremote.dataprivilege.interceptorchecker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

@Target(value ={ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataPrivilege
{
	DataPrivilegeType dataprivilgetype() ;
	String domain();
	String parameter() default "";
	String[] parameters() default "" ;
	String[] usertype() default {};
}
