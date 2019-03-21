package com.iremote.common;

public class Temperature {
    private int c;
    private int f;
    private byte[] cmds;

    public int getC() {
        return c;
    }

    public int getF() {
        return f;
    }

    public Temperature(byte[] cmds) {
        this.cmds = cmds;
    }

    public Temperature calculate() {
        int tmp = cmds[3] & 0xff ;
        int size = tmp & 0x7 ;
        int scale = ( tmp >> 3 ) & 0x3 ;
        int precision = ( tmp >> 5 ) & 0x7;

        int temperature = Utils.readsignedint(cmds, 4, 4 + size);
        temperature /= Math.pow(10, precision);

        if ( scale == 0 ) {
            c = temperature;
            f = (int)Utils.celsius2fahrenheit(c);
        }
        else {
            f = temperature;
            c = (int)Utils.fahrenheit2celsius(f);
        }
        return this;
    }
}
