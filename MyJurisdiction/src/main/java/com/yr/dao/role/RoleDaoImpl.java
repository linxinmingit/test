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
	 * 根据权限关联表pid返回用户权限,用户权限中根据字段m_id获取菜单信息
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
	 * 以分页的形式查询数据
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
			if(page.getSearch() != null && !page.getSearch().equals("")){//如果搜索有值，则表示为模糊查询
				sql += " and name like '%" + page.getSearch() + "%' ";
			}
			setTotalCount(page, sql, con);//查询出总条数将值设置入page
			sql += " limit "+page.getLimitNum()+","+page.getPageSize();//用limit实现分页效果
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			List<Role> list = new ArrayList<>();//实例化一个集合,将查询出的数据装入集合,然后加入page
			while(resultSet.next()){
				Role role = new Role();
				role.setId(resultSet.getInt("id"));
				role.setName(resultSet.getString("name"));
				role.setDesc(resultSet.getString("desc"));
				List<RolePermission> list1 = getPermissionId(resultSet.getInt("id"));
				List<Permission> list2 = new ArrayList<>();//将权限拿入角色
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
				con.close();//关闭该链接
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return page;//最后返回page
	}
	
	/**
	 * 根据角色表id关联的权限id
	 * @param List<RolePermission>
	 */
	 public List<RolePermission> getPermissionId(Integer id){
		 String sql = "select pid from role_permission where rid = ?";
		 RowMapper<RolePermission> mapper = new BeanPropertyRowMapper<>(RolePermission.class);
		 List<RolePermission> list = jdbcTemplate.query(sql, mapper, id);
		 return list;
	 }
	 
	 /**
	  * 添加数据到角色表
	  * @param role
	  */
	 public void add(Role role){
		 String sql = "insert into role(name,`desc`)values(?,?);";
		 jdbcTemplate.update(sql,role.getName(),role.getDesc());
	 }
	 
	 /**
	  * 根据id查询Role(权限)对象
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
	  * 修改角色数据
	  * @param role
	  */
	 public void update(Role role){
		 String sql = "update role set name=?,`desc`=? where id = ?";
		 jdbcTemplate.update(sql, role.getName(), role.getDesc(), role.getId());
	 }
	 
	 /**
	  * 删除角色
	  * @param role
	  */
	 public void delete(Integer id){
		 String sql = "delete from role where id = ?";
		 jdbcTemplate.update(sql, id);
	 }
	 
	 /**
	  * 查询角色表的所有信息
	  * @return List<Role>
	  */
	 public List<Role> queryRole(){
		 String sql = "select * from role;";
		 RowMapper<Role> mapper = new BeanPropertyRowMapper<>(Role.class);
		 List<Role> list = jdbcTemplate.query(sql, mapper);
		 return list;
	 }
	 
	 /**
	  * 删除关联表指定角色数据
	  */
	 public void deleteRolePer(Integer id){
		 String sql = "delete from role_permission where rid = ?";
		 jdbcTemplate.update(sql, id);
	 }
	 
	 /**
	  * 将角色权限关联表加入指定数据
	  * @param rid
	  * @param pid
	  */
	 public void add(Integer rid, Integer pid){
		 String sql = "insert into role_permission(rid,pid)values(?,?)";
		 jdbcTemplate.update(sql, rid, pid);
	 }
	 
	 /**
	  * 根据id查询角色权限关联表数据
	  */
	 public List<RolePermission> queryRolePer(Integer id){
		 String sql = "select * from role_permission where rid = ?";
		 BeanPropertyRowMapper<RolePermission> mapper = new BeanPropertyRowMapper<>(RolePermission.class);
		 return jdbcTemplate.query(sql, mapper, id);
	 }
}
