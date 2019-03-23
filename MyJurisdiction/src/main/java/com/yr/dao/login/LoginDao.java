package com.yr.dao.login;

import com.yr.entity.User;

public interface LoginDao {
	
	/**
	 * 进入数据库账号密码是否正确,返回布尔值
	 * @return	boolean
	 */
	boolean isVerification(User user);
}
