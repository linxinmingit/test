package com.yr.controller.menu;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yr.entity.Menu;
import com.yr.entity.Permission;
import com.yr.entity.Picture;
import com.yr.service.menu.MenuService;

@Controller
@RequestMapping("/Jurisdictions/menu")
public class MenuController {
	@Autowired
	@Qualifier("menuServiceImpl")
	private MenuService menuService;
	
	//如果检测到form表单提交带有id,直接将值存入request中
	@ModelAttribute
	public void getMenu(@RequestParam(value="id",required=false) Integer id,Map<String, Object> map){
		if(id !=null && id != 0){
			Menu menu = menuService.queryById(id);
			map.put("menu", menu); 
		}
	}
	
	/**
	 * 查询所有的根级菜单根级并找到对应的子集菜单将其组装成一个json格式返回到页面
	 * @return	String
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value="/getJson", produces = "application/json; charset=utf-8")//produces解决中文乱码问题
	public String query(Locale locale) throws UnsupportedEncodingException{
		String locales = locale.toString();
		String json = menuService.query(locales);//组装好的json字符串返回
		return json;
	}
	
	/**
	 * 查询一级菜单信息
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="/menus", method=RequestMethod.GET)
	public List<Menu> queryRoot(){
		List<Menu> list = menuService.queryList();
		return list;
	}
	
	/**
	 * 根据菜单id查出下面的直接子菜单
	 * @return List<Menu>
	 */
	@ResponseBody
	@RequestMapping(value="/menuById")
	public List<Menu> queryById(@RequestParam("id") Integer id){
		List<Menu> list = menuService.queryChildrenById(id);
		return list;
	}
	
	/**
	 * 根据菜单id查出下面的所有的子菜单
	 * @param id
	 * @return List<Menu>
	 */
	@ResponseBody
	@RequestMapping(value="/menuByIds")
	public List<Menu> queryByIds(@RequestParam("id") Integer id){
		List<Menu> list = menuService.queryChildrenByIds(id);
		return list;
	}

	/**
	 * 跳转添加页面
	 * @return String
	 */
	@RequestMapping(value="/menu")
	public String jumpAdd(Map<String, Object> map, @RequestParam(value="id", required=false) Integer id){
		if(id == null){//不带子集的跳转
			map.put("menu", new Menu());//设置一个空的对象
		}else{//带子集的跳转
			map.put("pid", id);
			map.put("menu", new Menu());//设置一个空的对象
		}
		return "menu/menuAdd";
	}
	
	/**
	 * 返回所有的图标
	 * @return List<Picture>
	 */
	@RequestMapping(value="/getPic")
	@ResponseBody
	public List<Picture> getPic(){
		List<Picture> list = menuService.queryPic();
		return list;
	}
	
	/**
	 * 保存菜单添加数据
	 * @return String
	 */
	@RequestMapping(value="/menu",method=RequestMethod.POST)
	public String saveAdd(Menu menu){
		menuService.add(menu);//添加菜单
		return "redirect:/Jurisdictions/menu";
	}
	
	/**
	 * 跳转修改页面
	 * @return String
	 */
	@RequestMapping(value="/menu/{id}",method=RequestMethod.GET)
	public String jumpUpdate(@PathVariable Integer id, Map<String, Object> map){
		Menu menu = menuService.queryById(id);
		map.put("menu", menu);//查询出对象放入map中
		map.put("picId", menuService.getId(menu.getPic()));//根据头像的值得到头像id传出去用来回显
		return "menu/menuAdd";
	}
	
	/**
	 * 保存修改
	 * @return String
	 */
	@RequestMapping(value="/menu", method=RequestMethod.PUT)
	public String saveUpdate(Menu menu){
		menuService.update(menu);
		//熊-BUG 有没有办法刷新整个页面
		return "redirect:/Jurisdictions/menu";
	}
	
	/**
	 * 根据id进行删除
	 * @param id
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value="/menu", method=RequestMethod.DELETE)
	public Map<String, Object> delete(@RequestParam Integer id){
		menuService.delete(id);
		Map<String, Object> map = new HashMap<>();
		return map;
	}
	
	/**
	 * 查询所有的菜单 
	 * @return List<Menu>
	 */
	@ResponseBody
	@RequestMapping(value="/queryAll")
	public List<Menu> queryAll(){
		return menuService.queryAll();
	}
	
	/**
	 * 查询权限
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getRole")
	public List<Permission> getPermissionById(@RequestParam("id") Integer id){
		Menu menu = menuService.queryById(id);
		return menuService.getPermissionById(id, menu.getLevel());//根据id查询权限
	}
	
}