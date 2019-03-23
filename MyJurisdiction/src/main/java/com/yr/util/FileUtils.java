package com.yr.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.util.StringUtils;

public class FileUtils {
	
	/**
	 * �ҵ�fromͼƬ��·��,����toͼƬ·������,��to�������ǵ�from����		�ƶ�ͼƬ
	 * @param from
	 * @param to
	 */
	public static void fileCover(String from, String to){
		if(!StringUtils.isEmpty(from) && !StringUtils.isEmpty(to)){//StringUtils.isEmpty��spring�Ĺ����࣬���Բ�Ϊ�գ�Ҳ��ΪNull(ע��:�ɼ�ֵһ��Ҫдǰ��)
			File files = new File(from);//��Ҫ���ĵ�ͼƬfile����
			File file = new File(to);//��Ҫ�ϴ���ͼƬfile����
			if(file.exists()){//�ж��Ƿ����
				if(!files.getParentFile().exists()){//�������Ŀ¼û�оʹ���Ŀ¼
					files.getParentFile().mkdirs();
				}
				if(!files.exists()){//��������ļ������ھʹ����ļ�
					try {
						files.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				FileInputStream inputStream = null;
				FileOutputStream fileOutputStream = null;
				try {
					inputStream = new FileInputStream(file);//��������������
					byte[] bytes = new byte[inputStream.available()];//����һ���������������ļ����ȵ�byte����
					inputStream.read(bytes);//����byte��
					fileOutputStream = new FileOutputStream(files);//���������
					fileOutputStream.write(bytes);//��������
					file.delete();//���Ǻ�ԭ����ͼƬɾ����
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					try {
						fileOutputStream.close();//�ر���
						inputStream.close();//�ر���
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * �����ļ�������
	 * @param fileName
	 */
	public static byte[] getFileFlow(String fileName){
		File file = new File(fileName);
        //���ļ�����byte����
        FileInputStream inputStream = null;
        byte[] data = null;
		try {
			inputStream = new FileInputStream(file);
			data = new byte[(int)file.length()];
		    inputStream.read(data);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
	/**
	 * ���ص�ǰʱ����������Ψһ��
	 * synchronized ʹ�û�����ͬʱ���룬Ҳ�����ļ�����������ͬ
	 * @return Long
	 */
	public synchronized static Long getTimeStamp(){
		return System.currentTimeMillis();
	}
	
	/**
	 * ����ļ�·��ɾ��
	 */
	public static void delete(String path){
		File file = new File(path);
		if(file.exists()){//�ж��Ƿ����,���ھ�ɾ��
			file.delete();
		}
	}
}
