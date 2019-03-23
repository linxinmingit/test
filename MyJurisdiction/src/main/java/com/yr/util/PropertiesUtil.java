package com.yr.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 读取属性文件工具类
 * @author 熊定坤
 *
 */
public class PropertiesUtil {

	
	/**
	 * getValue()根据属性文件的name得到value
	 * 
	 * @param key
	 *            属性文件的name
	 * @return value 属性文件value
	 * @throws IOException
	 */
	public static String getValue(String key) throws IOException {
		Properties prop = new Properties();
		// 根据属性文件的名称得到属性文件路径
		String filePath = PropertiesUtil.class.getClassLoader().getResource("db.properties").getPath();// jdbc.properties属性文件的名称
		InputStream in = new FileInputStream(filePath);// 文件输入流
		prop.load(in);// 读取
		String value = prop.getProperty(key).trim();
		return value;// 返回value
	}
	
	/**
	 * setValue()向属性文件设值
	 * 
	 * @param key
	 *            属性文件的name
	 * @param value
	 *            属性文件value
	 * @throws Exception
	 */
	
	public static void setValue(String key, String value) throws Exception {
		Properties prop = new Properties();
		// 根据属性文件名称，得到属性文件的路径
		String filePath = PropertiesUtil.class.getClassLoader().getResource("jdbc.properties").getPath();//jdbc.properties属性文件的名称
		System.out.println(filePath);
		InputStream in = new FileInputStream(filePath);// 输入流
		prop.load(in);
		in.close();// 关闭
		
		OutputStream fos = new FileOutputStream(filePath);
		//prop向文件里面设置了键与值
		prop.setProperty(key, value);// 设值
		
		prop.store(fos, "ldkjgldjl");
		fos.close();
	}

}
