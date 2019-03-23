package com.yr.service.login;

import com.yr.entity.User;

public interface LoginService {
	
	/**
	 * 进入数据库账号密码是否正确,返回布尔值
	 * @return	boolean
	 */
	boolean isVerification(User user);
	
	/**
	 * 根据名字查询对象
	 * @param name
	 * @return User
	 */
	User queryByName(String name);
}
