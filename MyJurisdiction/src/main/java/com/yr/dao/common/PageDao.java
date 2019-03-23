package com.yr.dao.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

import com.yr.entity.Page;

public class PageDao{
	
	public void setTotalCount(Page<?> page, String sql, Connection con){//将总条数设入page中,并得出总页数
		sql = "select count(*) from (" + sql +")t";//t是一个虚拟表命名
		try {
			PreparedStatement pre = con.prepareStatement(sql);
			ResultSet res = pre.executeQuery();
			res.next();
			int tni = res.getInt(1);
			page.setTotalCount(tni);//查询出多少条将值设置入page
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
