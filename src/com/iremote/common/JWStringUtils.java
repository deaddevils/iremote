package com.iremote.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JWStringUtils {
    private static Log log = LogFactory.getLog(JWStringUtils.class);

    public static String subStringbybyte(String str, int bytesize) {
        if (str == null)
            return null;

        try {
            byte[] b = str.getBytes("UTF-8");
            if (b == null || b.length <= bytesize)
                return str;

//			for ( int i = 0 ; i < b.length ; i ++ )
//				System.out.println(String.format("%d,%s,%s", i , Integer.toHexString(b[i] & 0xff) , Integer.toHexString(b[i] & 0xC0)));
//			System.out.println("----");

            for (; bytesize >= 0 && (b[bytesize] & 0xC0) == 0x80; bytesize--)
                ;//System.out.println(String.format("%s,%s", Integer.toHexString(b[bytesize] & 0xff) , Integer.toHexString(b[bytesize] & 0xC0)));
            if (bytesize <= 0)
                return "";
            byte[] b2 = new byte[bytesize];
            System.arraycopy(b, 0, b2, 0, b2.length);
            return new String(b2, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }

        return "";
    }

    public static String abbreviate(String str, int maxWidth) {
        if (str == null)
            return str;
        try {
            byte[] b = str.getBytes("UTF-8");
            if (b == null || b.length <= maxWidth)
                return str;
            if (maxWidth < 4)
                return "...";
            return subStringbybyte(str, maxWidth - 3) + "...";
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    public static String toHexString(byte[] content) {
        if (content == null || content.length == 0)
            return "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < content.length; i++) {
            if (content[i] >= 0 && content[i] <= 0x0f)
                sb.append("0");
            sb.append(Integer.toHexString(content[i] & 0xff));
        }
        return sb.toString();
    }

    public static byte[] hexStringtobyteArray(String content) {
        if (StringUtils.isBlank(content))
            return new byte[]{};
        byte[] rst = new byte[content.length() / 2];
        for (int i = 0; i < content.length() - 1; i += 2)
            rst[i / 2] = Integer.valueOf(content.substring(i, i + 2), 16).byteValue();
        return rst;
    }

    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean isNotEmpty(List list) {
        return list != null && list.size() != 0;
    }

    public static boolean isEmpty(List list) {
        return !isNotEmpty(list);
    }
}
