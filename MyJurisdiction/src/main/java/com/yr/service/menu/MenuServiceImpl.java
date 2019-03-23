package com.yr.service.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yr.dao.menu.MenuDao;
import com.yr.entity.Menu;
import com.yr.entity.Permission;
import com.yr.entity.Picture;

@Service("menuServiceImpl")
public class MenuServiceImpl implements MenuService{
	@Autowired
	@Qualifier("menuDaoImpl")
	private MenuDao menuDao;
	
	/**
	 * 查询所有的根级菜单根级并找到对应的子集菜单将其组装成一个json格式返回到页面
	 * @return	String
	 */
	public String query(String locale){
		String json = null;
		List<Menu> list = menuDao.query();
		if(list != null){//也就是最少有一条数据
			json = "{'contentManagement':";
			for (int i = 0; i < list.size(); i++) {
				Menu menu = list.get(i);//获得循环的对象
				if(i == 0){//表示第一次进入
					if(locale.equals("en_US")){//英文
						json = json + "[{'title': '"+menu.getNameUs()+"','icon': '"+menu.getPic()+"','href': '"+menu.getUrl()+"','spread': false";
					}else{//中文
						json = json + "[{'title': '"+menu.getName()+"','icon': '"+menu.getPic()+"','href': '"+menu.getUrl()+"','spread': false";
					}
				}else{
					if(locale.equals("en_US")){
						json = json + ",{'title': '"+menu.getNameUs()+"','icon': '"+menu.getPic()+"','href': '"+menu.getUrl()+"','spread': false";
					}else{
						json = json + ",{'title': '"+menu.getName()+"','icon': '"+menu.getPic()+"','href': '"+menu.getUrl()+"','spread': false";
					}
				}
				List<Menu> list1 = menuDao.queryChildrenById(menu.getId());//获得根导航栏下的子导航栏
				if(list1 != null){//也就是最少存在一个子导航栏
					json = query1(json,list1,menu,locale);
				}else{//表示没有子导航栏
					json = json + "}";
				}
				if(i == list.size()-1){//如果当循环为最后一次循环,则满足条件
					json += "]";
				}
			}
			json = json + "}";
		}
		return json;
	}
	
	//执行递归,组合json字符串
	public String query1(String json, List<Menu> list, Menu menu, String locale){
		for (int j = 0; j < list.size(); j++) {
			Menu menu1 = list.get(j);
			menu1.setUrl(menu.getUrl()+menu1.getUrl());
			if(j == 0){//第一次进入
				if(locale.equals("en_US")){//英文
					json += ",'children':[{'title': '"+menu1.getNameUs()+"','icon': '"+menu1.getPic()+"','href': '"+menu1.getUrl()+"','spread': false";
				}else{//中文
					json += ",'children':[{'title': '"+menu1.getName()+"','icon': '"+menu1.getPic()+"','href': '"+menu1.getUrl()+"','spread': false";
				}
			}else{
				if(locale.equals("en_US")){
					json += ",{'title': '"+menu1.getNameUs()+"','icon': '"+menu1.getPic()+"','href': '"+menu1.getUrl()+"','spread': false";
				}else{
					json += ",{'title': '"+menu1.getName()+"','icon': '"+menu1.getPic()+"','href': '"+menu1.getUrl()+"','spread': false";
				}
			}
			List<Menu> list1 = menuDao.queryChildrenById(menu1.getId());
			if(list1 != null){//如果不等于null则继续执行该方法
				json = query1(json,list1,menu1,locale);
			}else{
				json += "}";
			}
			if(j == list.size()-1){//如果当循环为最后一次循环,则满足条件
				json += "]}";
			}
		}
		return json;
	}
	
	/**
	 * 查询一级菜单返回到jsp展示
	 * @return String
	 */
	public List<Menu> queryList(){
		List<Menu> list = menuDao.query();
		for (Menu menu : list) {//循环集合，判断是否有子类，有就将booleans属性改为true
			List<Menu> list1 = menuDao.queryChildrenById(menu.getId());
			if(list1 != null){
				menu.setBooleans(true);
			}
		}
		return list;
	}
	
	/**
	 * 根据菜单id查出下面的直接子菜单
	 * @return List<Menu>
	 */
	public List<Menu> queryChildrenById(Integer id){
		List<Menu> list = menuDao.queryChildrenById(id);
		for (Menu menu : list) {//循环集合，判断是否有子类，有就将booleans属性改为true
			List<Menu> list1 = menuDao.queryChildrenById(menu.getId());
			if(list1 != null){
				menu.setBooleans(true);
			}
		}
		return list;
	}
	
	/**
	 * 根据菜单id查出所有的下面的所有子菜单
	 * @return List<Menu>
	 */
	public List<Menu> queryChildrenByIds(Integer id){
		List<Menu> list1 = menuDao.queryChildrenById(id);//循环list的时候不能对List进行增加删除,改变他的长度
		List<Menu> list = new ArrayList<>();
		list.addAll(list1);//将list1中数据导入list中
		if(list1 != null){//不为null循环集合
			for (Menu menu : list1) {//循环集合，判断是否有子类，有就将booleans属性改为true
				recursion(list, menu);//进行递归
			}
		}
		return list;
	}
	
	/**
	 * 进行递归查询是否有菜单点,有子菜单加入集合
	 * @return List<Menu>
	 */
	public void recursion(List<Menu> list, Menu menu){
		List<Menu> list1 = menuDao.queryChildrenById(menu.getId());//判断是否存在子节点,有就有集合,没有就为Null
		if(list1 != null){//如果不等于Null则满足条件
			menu.setBooleans(true);//有子菜单设置为true
			for (Menu menu1 : list1) {//循环集合
				list.add(menu1);
				recursion(list, menu1);//再判断是否有子菜单
			}
		}
	}
	
	/**
	 * 查询所有头像
	 * @return List<Picture>
	 */
 	public List<Picture> queryPic(){
		return menuDao.queryPic();
	}
 	
 	/**
	 * 添加菜单信息
	 */
	public void add(Menu menu){
		menu.setPic(menuDao.queryPicById(Integer.valueOf(menu.getPic())).getUrl());//获取头像表中的值
		Menu menus = queryById(menu.getPid());//获取父级的对象
		menu.setLevel(menus.getLevel()+1);//添加的菜单级别是父级级别+1
		menuDao.add(menu);
	}
	
	/**
	 * 返回该id的对象
	 * @param id
	 * @return Menu
	 */
	public Menu queryById(Integer id){
		return menuDao.queryById(id);
	}
	
	/**
	 * 根据pic获取图标表的id
	 * @param pic
	 * @return String
	 */
	public Integer getId(String pic){
		return menuDao.getId(pic);
	}
	
	/**
	 * 根据id修改所有的数据
	 * @param menu
	 */
	public void update(Menu menu){
		menu.setPic(menuDao.queryPicById(Integer.valueOf(menu.getPic())).getUrl());
		Menu menus = queryById(menu.getPid());//获取父级的对象
		menu.setLevel(menus.getLevel()+1);//添加的菜单级别是父级级别+1	这里改变级别
		menuDao.update(menu);
	}
	
	/**
	 * 根据id删除菜单,如果一个id被删除则下面所有的子菜单也被删除
	 * @param id
	 */
	public void delete(Integer id){
		menuDao.delete(id);
		menuDao.deletes(id);
	}
	
	/**
	 * 查询所有的菜单 
	 * @return List<Menu>
	 */
	public List<Menu> queryAll(){
		return menuDao.queryAll();
	}
	
	/**
	 * 根据id查询权限对象
	 * @param id
	 * @return Permission
	 */
	public List<Permission> getPermissionById(Integer id, Integer frequency){
		return menuDao.getPermissionById(id, frequency);
	}
}
