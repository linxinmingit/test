package com.yr.dao.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yr.entity.Menu;
import com.yr.entity.Permission;
import com.yr.entity.Picture;

@Repository("menuDaoImpl")
public class MenuDaoImpl implements MenuDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * ��ѯ�˵������Ĺ�����
	 * @return	List<Menu>
	 */
	public List<Menu> query(){
		String sql = "select * from menu where pid = 0";
		RowMapper<Menu> mapper = new BeanPropertyRowMapper<>(Menu.class);
		List<Menu> list = jdbcTemplate.query(sql, mapper);
		if(list.size() != 0){//������0���ʾ��ֵ
			return list;
		}
		return null;
	}
	
	/**
	 * ����id��ѯ�ò˵��µ������Ӳ˵�
	 * @return List<Menu>
	 */
	public List<Menu> queryChildrenById(Integer id){
		String sql = "select * from menu where pid = ?";
		RowMapper<Menu> mapper = new BeanPropertyRowMapper<>(Menu.class);
		List<Menu> list = jdbcTemplate.query(sql, mapper, id);
		if(list.size() != 0){//������0���ʾ��ֵ
			return list;
		}
		return null;
	}
	
	/**
	 * ��ѯ����ͷ��
	 * @return List<Picture>
	 */
	public List<Picture> queryPic(){
		String sql = "select * from picture";
		RowMapper<Picture> mapper = new BeanPropertyRowMapper<>(Picture.class);
		List<Picture> list =  jdbcTemplate.query(sql, mapper);
		return list;
	}
	
	/**
	 * ����id��ѯͷ��url
	 * @param id
	 * @return Picture
	 */
	public Picture queryPicById(Integer id){
		String sql = "select * from picture where id = ?;";
		RowMapper<Picture> mapper = new BeanPropertyRowMapper<>(Picture.class);
		return jdbcTemplate.queryForObject(sql, mapper, id);
	}
	
	/**
	 * ��Ӳ˵���Ϣ
	 */
	public void add(Menu menu){
		String sql = "insert into menu(name,pic,pid,url,level)values(?,?,?,?,?);";
		jdbcTemplate.update(sql,menu.getName(),menu.getPic(),menu.getPid(),menu.getUrl(),menu.getLevel());
	}
	
	/**
	 * ���ظ�id�Ĳ˵�����
	 * @param id
	 * @return Menu
	 */
	public Menu queryById(Integer id){
		String sql = "select * from menu where id = ?";
		RowMapper<Menu> mapper = new BeanPropertyRowMapper<>(Menu.class);
		return jdbcTemplate.queryForObject(sql, mapper, id);
	}
	
	/**
	 * ����pic��ȡͼ����id
	 * @param pic
	 * @return String
	 */
	public Integer getId(String pic){
		String sql = "select id from picture where url = ?";
		Integer id = jdbcTemplate.queryForObject(sql, Integer.class, pic);
		return id;
	}
	
	/**
	 * ����id�޸����е�����
	 * @param menu
	 */
 	public void update(Menu menu){
		String sql = "update menu set name=?,pic=?,pid=?,url=?,level=? where id = ?;";
		jdbcTemplate.update(sql, menu.getName(), menu.getPic(), menu.getPid(), menu.getUrl(), menu.getLevel(), menu.getId());
	}
 	
 	/**
	 * ����idɾ���˵�
	 * @param id
	 */
	public void delete(Integer id){
		String sql = "delete from menu where id = ?;";
		jdbcTemplate.update(sql, id);
	}
	
	/**
	 * ��ѯ���еĲ˵� 
	 * @return List<Menu>
	 */
	public List<Menu> queryAll(){
		String sql = "select * from menu";
		RowMapper<Menu> mapper = new BeanPropertyRowMapper<>(Menu.class);
		return jdbcTemplate.query(sql, mapper);
	}
	
	/**
	 * ���һ��id��ɾ�����������е��Ӳ˵�Ҳ��ɾ��
	 * @param pid
	 */
	public void deletes(Integer pid){
		String sql = "delete from menu where pid = ?;";
		jdbcTemplate.update(sql, pid);
	}
	
	/**
	 * ����id��ѯȨ�޶���
	 * @param id
	 * @return Permission
	 */
	public List<Permission> getPermissionById(Integer id, Integer frequency){
		String sql = "select * from permission where m_id = ?";
		List<Permission> list = null;
		Connection con = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			list = new ArrayList<>();
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				Permission permission = new Permission();
				permission.setId(resultSet.getInt("id"));
				permission.setName(resultSet.getString("name"));
				permission.setUrl(resultSet.getString("url"));
				permission.setMethod(resultSet.getString("method"));
				permission.setFrequency(frequency);
				list.add(permission);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				resultSet.close();
				preparedStatement.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
