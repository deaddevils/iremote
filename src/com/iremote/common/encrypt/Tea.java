package com.iremote.common.encrypt;

public class Tea {

	/**
	 * ����
	 * @param content ֻ�ܼ���ǰ8���ֽڳ���
	 * @param offset
	 * @param key0
	 * @param times
	 * @return
	 */
	private static byte[] encrypt(byte[] content, int offset, byte[] key0, int times) {// timesΪ��������
		int[] tempInt = byteToInt(content, offset);
		int y = tempInt[0], z = tempInt[1], sum = 0, i;
		int delta = 0x9e3779b9; // �����㷨��׼����ֵ
        int[] key = byteToInt(key0, 0);
        int a = key[0], b = key[1], c = key[2], d = key[3];
		for (i = 0; i < times; i++) {
			sum += delta;
			y += ((z << 4) + a) ^ (z + sum) ^ ((z >>> 5) + b);
			z += ((y << 4) + c) ^ (y + sum) ^ ((y >>> 5) + d);
		}
		tempInt[0] = y;
		tempInt[1] = z;
		return intToByte(tempInt, 0);
	}

	/**
	 * ����
	 * @param encryptContent ֻ�ܽ���ǰ8���ֽڳ���
	 * @param offset
	 * @param key0
	 * @param times
	 * @return
	 */
	private static byte[] decrypt(byte[] encryptContent, int offset, byte[] key0, int times) {
		int[] tempInt = byteToInt(encryptContent, offset);
		int y = tempInt[0], z = tempInt[1], sum, i;
		int delta = 0x9e3779b9; // �����㷨��׼����ֵ
        int[] key = byteToInt(key0, 0);
        int a = key[0], b = key[1], c = key[2], d = key[3];
        sum = delta << (int) (Math.log(times) / Math.log(2));
		for (i = 0; i < times; i++) {
			z -= ((y << 4) + c) ^ (y + sum) ^ ((y >>> 5) + d);
			y -= ((z << 4) + a) ^ (z + sum) ^ ((z >>> 5) + b);
			sum -= delta;
		}
		tempInt[0] = y;
		tempInt[1] = z;

		return intToByte(tempInt, 0);
	}

	// byte[]������ת��int[]������, ��λ��ǰ
	private static int[] byteToInt(byte[] content, int offset) {

		int[] result = new int[content.length >> 2]; // ����2��n�η� == ����nλ ��
														// content.length / 4 ==
														// content.length >> 2
		for (int i = 0, j = offset; j < content.length; i++, j += 4) {
			result[i] = transform(content[j + 3]) | transform(content[j + 2]) << 8 | transform(content[j + 1]) << 16
					| (int) content[j] << 24;
		}
		return result;

	}

	// int[]������ת��byte[]������, ��λ��ǰ
	private static byte[] intToByte(int[] content, int offset) {
		byte[] result = new byte[content.length << 2]; // ����2��n�η� == ����nλ ��
														// content.length * 4 ==
														// content.length << 2
		for (int i = 0, j = offset; j < result.length; i++, j += 4) {
			result[j + 3] = (byte) (content[i] & 0xff);
			result[j + 2] = (byte) ((content[i] >> 8) & 0xff);
			result[j + 1] = (byte) ((content[i] >> 16) & 0xff);
			result[j] = (byte) ((content[i] >> 24) & 0xff);
		}
		return result;
	}

	// ��ĳ�ֽڱ����ͳɸ������轫��ת���޷�������
	private static int transform(byte temp) {
		int tempInt = (int) temp;
		if (tempInt < 0) {
			tempInt += 256;
		}
		return tempInt;
	}

	public static byte[] encryptByTea(byte[] content, byte[] key) {
		return encryptByTea(content, key, 32);
	}
	
	public static byte[] encryptByTea(byte[] content , byte[] key ,int times)
	{
		int n = 8 - content.length % 8; // ��temp��λ������8�ı���,��Ҫ����λ��
		byte[] encryptStr = new byte[content.length + n];
		System.arraycopy(content, 0, encryptStr, 0, content.length);
        fillChar(encryptStr, (byte) n, content.length, n);
        byte[] result = new byte[encryptStr.length];
		for (int offset = 0; offset < result.length; offset += 8) {
			byte[] tempEncrpt = encrypt(encryptStr, offset, key, times);
			System.arraycopy(tempEncrpt, 0, result, offset, 8);
		}
		return result;
	}

    private static void fillChar(byte[] content, byte byteChar, int offset, int length) {
        for (int i = 0; i < length; i++) {
            content[offset + i] = byteChar;
        }
    }

	public static byte[] decryptByTea(byte[] secretInfo, byte[] key) {
		return decryptByTea(secretInfo, key, 32);
	}

	// ͨ��TEA�㷨������Ϣ
	public static byte[] decryptByTea(byte[] secretInfo, byte[] key, int times)
	{
		byte[] tempDecrypt = new byte[secretInfo.length];
		for (int offset = 0; offset < secretInfo.length; offset += 8)
		{
            byte[] decryptStr = decrypt(secretInfo, offset, key, times);
			System.arraycopy(decryptStr, 0, tempDecrypt, offset, 8);
		}

		return tempDecrypt;
	}
}
