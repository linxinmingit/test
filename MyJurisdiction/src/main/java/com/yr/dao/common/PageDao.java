package com.yr.dao.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

import com.yr.entity.Page;

public class PageDao{
	
	public void setTotalCount(Page<?> page, String sql, Connection con){//������������page��,���ó���ҳ��
		sql = "select count(*) from (" + sql +")t";//t��һ�����������
		try {
			PreparedStatement pre = con.prepareStatement(sql);
			ResultSet res = pre.executeQuery();
			res.next();
			int tni = res.getInt(1);
			page.setTotalCount(tni);//��ѯ����������ֵ������page
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
