package com.yr.service.menu;

import java.util.List;
import java.util.Locale;

import com.yr.entity.Menu;
import com.yr.entity.Permission;
import com.yr.entity.Picture;

public interface MenuService {
	
	/**
	 * 查询所有的根级菜单根级并找到对应的子集菜单将其组装成一个json格式返回到页面
	 * @return	String
	 */
	String query(String locale);
	
	/**
	 * 查询返回到jsp展示
	 * @return String
	 */
	List<Menu> queryList();
	
	/**
	 * 根据菜单id查出下面的直接子菜单
	 * @return List<Menu>
	 */
	List<Menu> queryChildrenById(Integer id);
	
	/**
	 * 根据菜单id查出所有的下面的所有子菜单
	 * @return List<Menu>
	 */
	List<Menu> queryChildrenByIds(Integer id);
	
	/**
	 * 查询所有头像
	 * @return List<Picture>
	 */
	List<Picture> queryPic();
	
	/**
	 * 添加菜单信息
	 */
	void add(Menu menu);
	
	/**
	 * 返回该id的对象
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
	 * 根据id查询权限对象
	 * @param id
	 * @return Permission
	 */
	List<Permission> getPermissionById(Integer id, Integer frequency);
}
