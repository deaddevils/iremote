package com.iremote.action.amazon.smarthome.util;

import java.util.Arrays;

public class ColorConvert {
    public static float[] hsb2rgb(float[] hsb) {
        float[] rgb = new float[3];
        for (int offset = 240, i = 0; i < 3; i++, offset -= 120) {
            float x = Math.abs((hsb[0] + offset) % 360 - 240);
            if (x <= 60) rgb[i] = 255;
            else if (60 < x && x < 120) rgb[i] = ((1 - (x - 60) / 60) * 255);
            else rgb[i] = 0;
        }
        for (int i = 0; i < 3; i++)
            rgb[i] += (255 - rgb[i]) * (1 - hsb[1]);
        for (int i = 0; i < 3; i++)
            rgb[i] *= hsb[2];
        return rgb;
    }

    public static float[] rgb2hsb(int rgbR, int rgbG, int rgbB) {
        assert 0 <= rgbR && rgbR <= 255;
        assert 0 <= rgbG && rgbG <= 255;
        assert 0 <= rgbB && rgbB <= 255;
        int[] rgb = new int[]{rgbR, rgbG, rgbB};
        Arrays.sort(rgb);
        int max = rgb[2];
        int min = rgb[0];
        float hsbB = max / 255.0f;
        float hsbS = max == 0 ? 0 : (max - min) / (float) max;
        float hsbH = 0;
        if (max == rgbR && rgbG >= rgbB) {
            hsbH = (rgbG - rgbB) * 60f / (max - min) + 0;
        } else if (max == rgbR && rgbG < rgbB) {
            hsbH = (rgbG - rgbB) * 60f / (max - min) + 360;
        } else if (max == rgbG) {
            hsbH = (rgbB - rgbR) * 60f / (max - min) + 120;
        } else if (max == rgbB) {
            hsbH = (rgbR - rgbG) * 60f / (max - min) + 240;
        }
        return new float[]{hsbH, hsbS, hsbB};
    }


    public static void main(String[] args) {
        float[] floats = hsb2rgb(new float[]{0, 1.0f, 1.0f});
        System.out.println(floats);
    }
}
