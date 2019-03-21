package com.iremote.jpush;

import cn.com.isurpass.iremote.manage.ReloadOemProductorSettingAction;
import cn.jpush.api.JPushClient;
import com.iremote.common.encrypt.AES;
import com.iremote.common.push.PushMessage;
import com.iremote.common.push.PushMessageThread;
import com.iremote.test.db.Db;

public class JpushContent {
    public static void main(String[] args) {
        Db.init();
        ReloadOemProductorSettingAction a = new ReloadOemProductorSettingAction();
        a.execute();
        //PushMessageThread.jpushmap.put(platform, new JPushClient[]{ new JPushClient( AES.decrypt2Str(masterkey), AES.decrypt2Str(appkey), 60 * 60 * 24)});  // jwzh
//        PushMessageThread.initPushClient(22, AES.encrypt2Str("3a5917acfbc93dace5ae9b68"), AES.encrypt2Str("9acaec4ed591a14d65b3a141"));
        PushMessageThread.initPushClient(22, AES.encrypt2Str("718e6868db08b2b668127927"), AES.encrypt2Str("6f27509deb2a098e06fa3a1c"));
        PushMessage.pushSoundMessage("913365af1b9b4544ace930747df1308bb43" , 22, "推送测试", 1, "ring1.mp3");
//        PushMessage.pushSoundMessage("4b7ac087cf4445e7997a990234b0cf1bc54d" , 22, "推送测试", 1, "ring1.mp3");
        Db.commit();

    }
}
