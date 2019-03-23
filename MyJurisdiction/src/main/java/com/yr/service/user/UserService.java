package com.yr.service.user;

import java.util.List;

import com.yr.entity.Page;
import com.yr.entity.Role;
import com.yr.entity.User;

public interface UserService {
	
	/**
	 * 根据id查询用户的所有数据
	 * @return
	 */
	User queryById(Integer id);
	
	/**
	 * 获取到页面的信息进行修改
	 * @param user
	 */
	void update(User user);
	
	/**
	 * 以分页的形式查询所有的数据
	 * @return Page<User>
	 */
	Page<User> query(Page<User> page);
	
	/**
	 * 添加用户信息
	 * @param user
	 */
	void add(User user);
	
	/**
	 * 修改用户的状态,停用改为启用,启用改为停用
	 * @param id
	 */
	void changeState(Integer id, Integer status);
	
	/**
	 * 删除用户
	 * @param id
	 */
	void delete(Integer id);
	
	/**
	 * 初始化用户密码
	 */
	void recovery(Integer id);
	
	/**
	  * 查询角色表的所有信息
	  * @return List<Role>
	  */
	List<Role> queryRole();
	
	/**
	 * 根据用户id查询对应的多个角色
	 * @param id
	 * @return List<Role>
	 */
	List<Role> queryRoleByUser(Integer id);
	
	/**
	 * 修改用户的角色
	 */
	void updateRole(Integer uid, Integer[] rid);
}
