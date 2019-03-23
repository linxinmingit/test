package com.yr.dao.menu;

import java.util.List;

import com.yr.entity.Menu;
import com.yr.entity.Permission;
import com.yr.entity.Picture;

public interface MenuDao {
	
	/**
	 * 查询菜单根级的功能栏
	 * @return	List<Menu>
	 */
	List<Menu> query();
	
	/**
	 * 根据id查询该菜单下的所有子菜单
	 * @return List<Menu>
	 */
	List<Menu> queryChildrenById(Integer id);
	
	/**
	 * 查询所有头像
	 * @return List<Picture>
	 */
	List<Picture> queryPic();
	
	/**
	 * 根据id查询头像url
	 * @param id
	 * @return Picture
	 */
	Picture queryPicById(Integer id);
	
	/**
	 * 添加菜单信息
	 */
	void add(Menu menu);
	
	/**
	 * 返回该id的菜单对象
	 * @param id
	 * @return Menu
	 */
	Menu queryById(Integer id);
	
	/**
	 * 根据pic获取图标表的id
	 * @param pic
	 * @return String
	 */
	Integer getId(String pic);
	
	/**
	 * 根据id修改所有的数据
	 * @param menu
	 */
	void update(Menu menu);
	
	/**
	 * 根据id删除菜单
	 * @param id
	 */
	void delete(Integer id);
	
	/**
	 * 查询所有的菜单 
	 * @return List<Menu>
	 */
	List<Menu> queryAll();
	
	/**
	 * 如果一个id被删除则下面所有的子菜单也被删除
	 * @param pid
	 */
	void deletes(Integer pid);
	
	/**
	 * 根据id查询权限对象
	 * @param id
	 * @return Permission
	 */
	List<Permission> getPermissionById(Integer id, Integer frequency);
}
