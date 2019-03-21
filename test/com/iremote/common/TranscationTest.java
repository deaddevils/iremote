package com.iremote.common;

import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.PhoneUser;
import com.iremote.service.PhoneUserService;
import com.iremote.test.db.Db;
import org.hibernate.Hibernate;

public class TranscationTest {
    public static void main(String[] args) {
        new Thread(()->{
            System.out.println("Thread 1");

            Db.init();
            PhoneUserService pus = new PhoneUserService();
            PhoneUser pu = pus.query(441);
            pu.setName("Thread 1 name1");
            HibernateUtil.commit();
            System.out.println("Thread 1: write name");

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            HibernateUtil.getSession().evict(pu);
            HibernateUtil.getSession().clear();
            HibernateUtil.beginTransaction();
            PhoneUserService pus1 = new PhoneUserService();
            PhoneUser pu1 = pus1.query(441);
            System.out.println("Thread 1:"+ pu1.getName());
            HibernateUtil.commit();
        }).start();

        new Thread(() -> {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Thread 2");

            Db.init();

            PhoneUserService pus = new PhoneUserService();
            PhoneUser pu = pus.query(441);
            System.out.println("Thread 2:"+pu.getName());
            pu.setName("Thread 2 name1");
            HibernateUtil.commit();


        }).start();
    }
}
