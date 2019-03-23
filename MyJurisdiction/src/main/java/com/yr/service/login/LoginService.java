package com.yr.service.login;

import com.yr.entity.User;

public interface LoginService {
	
	/**
	 * �������ݿ��˺������Ƿ���ȷ,���ز���ֵ
	 * @return	boolean
	 */
	boolean isVerification(User user);
	
	/**
	 * �������ֲ�ѯ����
	 * @param name
	 * @return User
	 */
	User queryByName(String name);
}
