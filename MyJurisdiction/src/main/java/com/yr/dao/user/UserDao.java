package com.yr.dao.user;

import java.util.List;

import com.yr.entity.Addr;
import com.yr.entity.Page;
import com.yr.entity.Role;
import com.yr.entity.User;

public interface UserDao {
	
	/**
	 * 根据用户名字查询用户所有数据
	 * @return	User
	 */
	User queryByName(String name);
	
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
	 * 根据id查询地址对象
	 * @param id
	 * @return Addr
	 */
	Addr getAddr(Integer id);
	
	/**
	 * 根据user的addr属性的id修改地址表
	 * @param user
	 */
	void updateForAddr(User user);
	
	/**
	 * 以分页查询所有数据
	 * @return List<User>
	 */
	Page<User> query(Page<User> page);
	
	/**
	 * 添加用户信息
	 * @param user
	 */
	Integer add(User user);
	
	/**
	 * 添加用户的时候默认添加将用户id和普通角色id关联
	 */
	void addUserRole(Integer id);
	
	/**
	 * 添加用户地址,并得到他的id绑定到用户
	 * @param user
	 * @return Integer
	 */
	Integer addByAddr(User user);
	
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
	 * 根据用户id查询对应的多个角色
	 * @param id
	 * @return List<Role>
	 */
	List<Role> queryRoleByUser(Integer id);
	
	/**
	 * 删除关联表用户所对应的角色
	 * @param id
	 */
	void deleteUserRole(Integer id);
	
	/**
	 * 根据用户的id添加相应的权限到关联表
	 * @param uid	用户id
	 * @param rid	权限id
	 */
	void addRole(Integer uid, Integer rid);
}
