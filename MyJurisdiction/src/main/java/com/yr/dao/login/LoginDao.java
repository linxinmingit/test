package com.yr.dao.login;

import com.yr.entity.User;

public interface LoginDao {
	
	/**
	 * �������ݿ��˺������Ƿ���ȷ,���ز���ֵ
	 * @return	boolean
	 */
	boolean isVerification(User user);
}
