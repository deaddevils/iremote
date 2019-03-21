package com.iremote.service;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.test.db.Db;

public class UserShareTest {
    public static void main(String[] args) {
        Db.init();

       /* UserShareService uss = new UserShareService();
        List<Integer> usercodelist = uss.queryUsercodeByFromUserAsc(121, IRemoteConstantDefine.USER_SHARE_TYPE_FAMILY);
        System.out.println(usercodelist);
        if (usercodelist == null || usercodelist.size() == 0) {
            //usercode 1
            return;
        }

        for (int i = 0 ; i < usercodelist.size(); i++) {
            if (usercodelist.get(i) != i+2) {
                System.out.println(i+2);
                break;
            }
        }*/

    }
}
