package com.yr.service.role;

import java.util.List;

import com.yr.entity.Page;
import com.yr.entity.Role;
import com.yr.entity.RolePermission;

public interface RoleService {
	
	 /**
	  * 以分页的形式查询数据
	  * @param page
	  * @return Page<Role>
	  */
	 Page<Role> query(Page<Role> page);
	 
	 /**
	  * 添加数据到角色表
	  * @param role
	  */
	 void add(Role role);
	 
	 /**
	  * 根据id查询Role(权限)对象
	  * @param id
	  * @return Role
	  */
	 Role queryById(Integer id);
	 
	 /**
	  * 修改角色数据
	  * @param role
	  */
	 void update(Role role);
	 
	 /**
	  * 删除角色
	  * @param role
	  */
	 void delete(Integer id);
	 
	 /**
	  * 查询角色表的所有信息
	  * @return List<Role>
	  */
	 List<Role> queryRole();
	 
	 /**
	  * 给角色赋权限
	  * @param id
	  */
	 void rolePermission(Integer rid, Integer[] pid);
	 
	 /**
	  * 根据id查询角色权限关联表数据
	  */
	 List<RolePermission> queryRolePer(Integer id);
}
