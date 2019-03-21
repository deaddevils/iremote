package com.iremote.common.Hibernate;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil 
{
	private static Log log = LogFactory.getLog(HibernateUtil.class);
	
	  private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	  private static final ThreadLocal<Transaction> txthreadLocal = new ThreadLocal<Transaction>();
	  private static Map<String, SessionFactory> sessionFactorymap = new HashMap<String , SessionFactory>();

	private static final String DB_PATH_KEY = "iremote.db.path";
	  private static void init(String key)
	  {
	        try {  
	             Configuration cfg = new Configuration();    
	             cfg.configure();    
	        	             
				initDB(cfg);

	             ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
	             SessionFactory sessionFactory = cfg.buildSessionFactory(sr);
	             sessionFactorymap.put(key, sessionFactory);
	        }  
	        catch (Throwable ex) {    
	        	log.error("Initial SessionFactory creation failed." , ex);  
	            throw new ExceptionInInitializerError(ex);  
	        } 
	  }

	private static void initDB(Configuration cfg) {
		Properties properties = System.getProperties();
		if (!properties.containsKey(DB_PATH_KEY)) {
			ResourceBundle resource = ResourceBundle.getBundle("db");

			cfg.setProperty("hibernate.connection.username", resource.getString("jdbc.username"));
			cfg.setProperty("hibernate.connection.password", resource.getString("jdbc.password"));
			cfg.setProperty("hibernate.connection.url", resource.getString("jdbc.url"));
		}else{
			try {
				FileInputStream in = new FileInputStream(properties.getProperty(DB_PATH_KEY));
				properties.load(in);
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    public static void destroyall()
	  {
		  for ( SessionFactory sf : sessionFactorymap.values())
			  sf.close();
		  sessionFactorymap.clear();
	  }
	  
//	  public static void useJunitTestDatabase()
//	  {
//		  try {  
//	             Configuration cfg = new Configuration();    
//	             cfg.configure();    
//	             
//	             Properties p = new Properties();
//	             p.setProperty("hibernate.connection.url", "jdbc:mysql://127.0.0.1/iremote_junit?useUnicode=true&amp;characterEncoding=utf-8");
//	             cfg.addProperties(p);
//
//	             ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
//	             sessionFactory = cfg.buildSessionFactory(sr);
//	        }  
//	        catch (Throwable ex) {    
//	        	log.error("Initial SessionFactory creation failed." , ex);  
//	            throw new ExceptionInInitializerError(ex);  
//	        }
//	  }
	  
	  public static void prepareSession(String key)
	  {
		  if ( threadLocal.get() != null )
		  {
			  log.warn("session not closed");
			  closeSession();
		  }
		  threadLocal.set(getSessionFactory(key).openSession());
	  }

	  public static Session getSession() throws HibernateException
	  {
		  Session session = threadLocal.get();

		  if ( session == null || session.isOpen() == false )
			  log.error("session not open");

		  return session;
	  }

	  public static void beginTransaction()
	  {
		  Session session = getSession();
		  if ( session == null )
		  {
			  log.error("session is null");
			  return ;
		  }

		  Transaction tx = session.beginTransaction();
		  txthreadLocal.set(tx);
		  SessionTrackerManager.getInstance().track(new SessionTrackException(), session);
	  }

	  public static void closeSession()throws HibernateException
	  {
	    Session session = (Session)threadLocal.get();
	    threadLocal.set(null);

	    if (session != null)
	    {
	    	session.close();
	    }
	  }

	  public static void commit()
	  {
		  Transaction tx = txthreadLocal.get();
		  if ( tx == null )
		  {
			  log.error("transaction is null");
			  return ;
		  }
		  tx.commit();

		  txthreadLocal.set(null);
		  SessionTrackerManager.getInstance().remove(getSession());
	  }

	  public static void rollback()
	  {
		  Transaction tx = txthreadLocal.get();
		  if ( tx == null )
		  {
			  log.error("transaction is null");
			  return ;
		  }
		  tx.rollback();
		  txthreadLocal.set(null);
		  SessionTrackerManager.getInstance().remove(getSession());
	  }

	  private static SessionFactory getSessionFactory(String key)
	  {
		  SessionFactory sessionFactory = sessionFactorymap.get(key);
		  if ( sessionFactory == null )
		  {
			  init(key);
			  sessionFactory = sessionFactorymap.get(key);
		  }
		  return sessionFactory;
	  }
	}