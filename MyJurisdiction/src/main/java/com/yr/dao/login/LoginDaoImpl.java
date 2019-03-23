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
	 * �������ݿ��˺������Ƿ���ȷ,���ز���ֵ
	 * @return	boolean
	 */
	public boolean isVerification(User user){
		String sql = "select count(*) from user where name = ? and passwd = ?";
		Integer ger = jdbcTemplate.queryForObject(sql, Integer.class,user.getName(),user.getPasswd());//�÷���������Ҫsql����ָ�����͵Ĳ�������
		if(ger != 0){//������ڸ��û��ͱ�ʾ�˺�������ȷ
			return true;
		}
		return false;
	}
	
}
