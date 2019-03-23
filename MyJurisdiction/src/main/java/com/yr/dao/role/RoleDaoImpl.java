package com.yr.dao.role;

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

import com.yr.dao.common.PageDao;
import com.yr.entity.Page;
import com.yr.entity.Permission;
import com.yr.entity.Role;
import com.yr.entity.RolePermission;

@Repository("roleDaoImpl")
public class RoleDaoImpl extends PageDao implements RoleDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * ����Ȩ�޹�����pid�����û�Ȩ��,�û�Ȩ���и����ֶ�m_id��ȡ�˵���Ϣ
	 * @return Permission
	 */
 	public Permission queryPer(Integer id){
		String sql = "select * from permission where id = ?;";
		Permission permission = null;
		Connection con = null;
		PreparedStatement pre = null;
		ResultSet resultSet = null;
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			pre = con.prepareStatement(sql);
			pre.setInt(1, id);
			resultSet = pre.executeQuery();
			resultSet.next();
			permission = new Permission();
			permission.setId(resultSet.getInt("id"));
			permission.setName(resultSet.getString("name"));
			permission.setUrl(resultSet.getString("url"));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				resultSet.close();
				pre.close();
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return permission;
	}
	
	/**
	 * �Է�ҳ����ʽ��ѯ����
	 * @param page
	 * @return Page<Deposit>
	 */
	public Page<Role> query(Page<Role> page) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			String sql = "select * from role where 1 = 1";
			if(page.getSearch() != null && !page.getSearch().equals("")){//���������ֵ�����ʾΪģ����ѯ
				sql += " and name like '%" + page.getSearch() + "%' ";
			}
			setTotalCount(page, sql, con);//��ѯ����������ֵ������page
			sql += " limit "+page.getLimitNum()+","+page.getPageSize();//��limitʵ�ַ�ҳЧ��
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			List<Role> list = new ArrayList<>();//ʵ����һ������,����ѯ��������װ�뼯��,Ȼ�����page
			while(resultSet.next()){
				Role role = new Role();
				role.setId(resultSet.getInt("id"));
				role.setName(resultSet.getString("name"));
				role.setDesc(resultSet.getString("desc"));
				List<RolePermission> list1 = getPermissionId(resultSet.getInt("id"));
				List<Permission> list2 = new ArrayList<>();//��Ȩ�������ɫ
				for (RolePermission integer : list1) {
					Permission permission = queryPer(integer.getPid());
					list2.add(permission);
				}
				role.setPermission(list2);
				list.add(role);
			}
			page.setDataList(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				resultSet.close();
				preparedStatement.close();
				con.close();//�رո�����
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return page;//��󷵻�page
	}
	
	/**
	 * ���ݽ�ɫ��id������Ȩ��id
	 * @param List<RolePermission>
	 */
	 public List<RolePermission> getPermissionId(Integer id){
		 String sql = "select pid from role_permission where rid = ?";
		 RowMapper<RolePermission> mapper = new BeanPropertyRowMapper<>(RolePermission.class);
		 List<RolePermission> list = jdbcTemplate.query(sql, mapper, id);
		 return list;
	 }
	 
	 /**
	  * ������ݵ���ɫ��
	  * @param role
	  */
	 public void add(Role role){
		 String sql = "insert into role(name,`desc`)values(?,?);";
		 jdbcTemplate.update(sql,role.getName(),role.getDesc());
	 }
	 
	 /**
	  * ����id��ѯRole(Ȩ��)����
	  * @param id
	  * @return Role
	  */
	 public Role queryById(Integer id){
		 String sql = "select * from role where id = ?";
		 RowMapper<Role> mapper = new BeanPropertyRowMapper<>(Role.class);
		 Role role = jdbcTemplate.queryForObject(sql, mapper, id);
		 return role;
	 }
	 
	 /**
	  * �޸Ľ�ɫ����
	  * @param role
	  */
	 public void update(Role role){
		 String sql = "update role set name=?,`desc`=? where id = ?";
		 jdbcTemplate.update(sql, role.getName(), role.getDesc(), role.getId());
	 }
	 
	 /**
	  * ɾ����ɫ
	  * @param role
	  */
	 public void delete(Integer id){
		 String sql = "delete from role where id = ?";
		 jdbcTemplate.update(sql, id);
	 }
	 
	 /**
	  * ��ѯ��ɫ���������Ϣ
	  * @return List<Role>
	  */
	 public List<Role> queryRole(){
		 String sql = "select * from role;";
		 RowMapper<Role> mapper = new BeanPropertyRowMapper<>(Role.class);
		 List<Role> list = jdbcTemplate.query(sql, mapper);
		 return list;
	 }
	 
	 /**
	  * ɾ��������ָ����ɫ����
	  */
	 public void deleteRolePer(Integer id){
		 String sql = "delete from role_permission where rid = ?";
		 jdbcTemplate.update(sql, id);
	 }
	 
	 /**
	  * ����ɫȨ�޹��������ָ������
	  * @param rid
	  * @param pid
	  */
	 public void add(Integer rid, Integer pid){
		 String sql = "insert into role_permission(rid,pid)values(?,?)";
		 jdbcTemplate.update(sql, rid, pid);
	 }
	 
	 /**
	  * ����id��ѯ��ɫȨ�޹���������
	  */
	 public List<RolePermission> queryRolePer(Integer id){
		 String sql = "select * from role_permission where rid = ?";
		 BeanPropertyRowMapper<RolePermission> mapper = new BeanPropertyRowMapper<>(RolePermission.class);
		 return jdbcTemplate.query(sql, mapper, id);
	 }
}
