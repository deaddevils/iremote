package com.iremote.common;

import com.iremote.common.encrypt.AES;
import com.iremote.common.encrypt.Tea;

import java.lang.reflect.InvocationTargetException;

public class PhoneUserBlueToothHelperTest {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String key1 = "kanguoshamoxiayu";
        String key2 = "kanguoshamoxiayy";
        String key3 = "kanguoshamoxiauu";

//        Tea.encrypt("sadf12341".getBytes(), 0, key1.getBytes(), 32);
        byte[] bytes2 = Tea.encryptByTea(key2.getBytes(), key1.getBytes());
        byte[] bytes3 = Tea.encryptByTea(key3.getBytes(), key1.getBytes());

        Utils.print("bytes2: ", key2.getBytes());
//        System.out.println(key1);
        System.out.println(AES.encrypt(key1.getBytes()));
        System.out.println(AES.encrypt(bytes2));
        System.out.println(AES.encrypt(bytes3));


//        byte[] keyy = AES.decrypt(key1);

        byte[] bytes = Tea.decryptByTea(bytes2, key1.getBytes());
        Utils.print("bytes2: ", bytes);
        System.out.println(new String(bytes));
        System.out.println(new String(key2.getBytes()));

//        System.out.println(key2);
//        System.out.println(key3);

     /*   String p = "asdfghfdsafsdafjklpoiuytr";

        byte[] bytes1 = p.getBytes();
        Utils.print("", bytes1);
        Tea tea = new Tea();
        byte[] key1 = AES.decrypt("Zb9WAjNARyVjHuuaGYkIqbHvDkvTw4vk54GQHk9TjSk=");
        byte[] bytes = tea.encryptByTea(bytes1, key1, 16);
        Utils.print("", bytes);
        byte[] decrypt = tea.decryptByTea(bytes,key1, 16);
        Utils.print("", decrypt);
        System.out.println(new String(decrypt));*/
    }
}
