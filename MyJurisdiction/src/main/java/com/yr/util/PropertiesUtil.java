package com.yr.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * ��ȡ�����ļ�������
 * @author �ܶ���
 *
 */
public class PropertiesUtil {

	
	/**
	 * getValue()���������ļ���name�õ�value
	 * 
	 * @param key
	 *            �����ļ���name
	 * @return value �����ļ�value
	 * @throws IOException
	 */
	public static String getValue(String key) throws IOException {
		Properties prop = new Properties();
		// ���������ļ������Ƶõ������ļ�·��
		String filePath = PropertiesUtil.class.getClassLoader().getResource("db.properties").getPath();// jdbc.properties�����ļ�������
		InputStream in = new FileInputStream(filePath);// �ļ�������
		prop.load(in);// ��ȡ
		String value = prop.getProperty(key).trim();
		return value;// ����value
	}
	
	/**
	 * setValue()�������ļ���ֵ
	 * 
	 * @param key
	 *            �����ļ���name
	 * @param value
	 *            �����ļ�value
	 * @throws Exception
	 */
	
	public static void setValue(String key, String value) throws Exception {
		Properties prop = new Properties();
		// ���������ļ����ƣ��õ������ļ���·��
		String filePath = PropertiesUtil.class.getClassLoader().getResource("jdbc.properties").getPath();//jdbc.properties�����ļ�������
		System.out.println(filePath);
		InputStream in = new FileInputStream(filePath);// ������
		prop.load(in);
		in.close();// �ر�
		
		OutputStream fos = new FileOutputStream(filePath);
		//prop���ļ����������˼���ֵ
		prop.setProperty(key, value);// ��ֵ
		
		prop.store(fos, "ldkjgldjl");
		fos.close();
	}

}
