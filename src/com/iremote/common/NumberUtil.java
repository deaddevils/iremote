package com.iremote.common;

public class NumberUtil {
    public static byte[] intToByte4(int i) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (i & 0xFF);
        targets[2] = (byte) (i >> 8 & 0xFF);
        targets[1] = (byte) (i >> 16 & 0xFF);
        targets[0] = (byte) (i >> 24 & 0xFF);
        return targets;
    }

    public static int byte4ToInt(byte[] bytes, int off) {
        int b0 = bytes[off] & 0xFF;
        int b1 = bytes[off + 1] & 0xFF;
        int b2 = bytes[off + 2] & 0xFF;
        int b3 = bytes[off + 3] & 0xFF;
        return (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
    }

    public static byte[] hexToBytes(String hexString) {
        if (hexString.length() % 2 != 0) {
            return null;
        }
        int length = hexString.length() / 2;
        byte[] bytes = new byte[length];

        for (int i = 0; i < length; i++) {
            String s = hexString.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(s, 16);
        }
        return bytes;
    }

    public static byte[] long2byte(long res) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = 64 - (i + 1) * 8;
            buffer[i] = (byte) ((res >> offset) & 0xff);
        }
        return buffer;
    }

    public static long byteArrayToLong(byte[] b) {
        long values = 0;
        for (int i = 0; i < 8; i++) {
            values <<= 8;
            values |= (b[i] & 0xff);
        }
        return values;
    }

    public static boolean compare(double par1, double par2, String symbol) {
        switch (symbol) {
            case IRemoteConstantDefine.MATH_SYMBOL_EQ :
                return par1 == par2;
            case IRemoteConstantDefine.MATH_SYMBOL_GE :
                return par1 >= par2;
            case IRemoteConstantDefine.MATH_SYMBOL_GT :
                return par1 > par2;
            case IRemoteConstantDefine.MATH_SYMBOL_LE :
                return par1 <= par2;
            case IRemoteConstantDefine.MATH_SYMBOL_LT :
                return par1 < par2;
            default:
                return false;
//                throw new IllegalArgumentException(String.format("the math symbol(%s) is illegal", symbol));
        }
    }

    public static void main(String[] args) {
//        byte[] b = hexToBytes("F0FE6B4C6E0A");
//         System.out.println(b);

//        Integer integer = Integer.valueOf("01111111", 2);
//        System.out.println(integer);
        System.out.println(compare(1, 1.00, "="));
        System.out.println(1.01f == (float)1);
    }
}
