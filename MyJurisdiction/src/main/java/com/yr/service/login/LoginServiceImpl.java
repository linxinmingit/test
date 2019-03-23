package com.yr.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yr.dao.login.LoginDao;
import com.yr.dao.user.UserDao;
import com.yr.entity.User;

@Service("loginServiceImpl")
public class LoginServiceImpl implements LoginService{
	@Autowired
	@Qualifier("loginDaoImpl")
	private LoginDao loginDao;
	@Autowired
	@Qualifier("userDaoImpl")
	private UserDao userDao;
	
	/**
	 * 进入数据库账号密码是否正确,返回布尔值
	 * @return	boolean
	 */
	public boolean isVerification(User user){
		return loginDao.isVerification(user);
	}
	
	/**
	 * 根据名字查询对象
	 * @param name
	 * @return User
	 */
	public User queryByName(String name){
		return userDao.queryByName(name);
	}
}
