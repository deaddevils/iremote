package com.iremote.action.helper;

public class CodeidHelper {
    //AC_123
    public static String getDevicetypeFromCodeid(String codeid){
        if ("_".contains(codeid)) {
            return codeid.split("_")[0];
        }
        return codeid;
    }

    public static Integer getCodeidFromCodeid(String codeid){
        if ("_".contains(codeid)) {
            return Integer.valueOf(codeid.split("_")[1]);
        }
        return Integer.valueOf(codeid);
    }
}
