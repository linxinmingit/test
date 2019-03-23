package com.yr.dao.login;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yr.entity.User;

@Repository("loginDaoImpl")
public class LoginDaoImpl implements LoginDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 进入数据库账号密码是否正确,返回布尔值
	 * @return	boolean
	 */
	public boolean isVerification(User user){
		String sql = "select count(*) from user where name = ? and passwd = ?";
		Integer ger = jdbcTemplate.queryForObject(sql, Integer.class,user.getName(),user.getPasswd());//该方法参数需要sql语句加指定类型的参数类型
		if(ger != 0){//如果存在该用户就表示账号密码正确
			return true;
		}
		return false;
	}
	
}
