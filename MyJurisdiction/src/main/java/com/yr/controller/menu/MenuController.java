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
	
	//�����⵽form���ύ����id,ֱ�ӽ�ֵ����request��
	@ModelAttribute
	public void getMenu(@RequestParam(value="id",required=false) Integer id,Map<String, Object> map){
		if(id !=null && id != 0){
			Menu menu = menuService.queryById(id);
			map.put("menu", menu); 
		}
	}
	
	/**
	 * ��ѯ���еĸ����˵��������ҵ���Ӧ���Ӽ��˵�������װ��һ��json��ʽ���ص�ҳ��
	 * @return	String
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value="/getJson", produces = "application/json; charset=utf-8")//produces���������������
	public String query(Locale locale) throws UnsupportedEncodingException{
		String locales = locale.toString();
		String json = menuService.query(locales);//��װ�õ�json�ַ�������
		return json;
	}
	
	/**
	 * ��ѯһ���˵���Ϣ
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="/menus", method=RequestMethod.GET)
	public List<Menu> queryRoot(){
		List<Menu> list = menuService.queryList();
		return list;
	}
	
	/**
	 * ���ݲ˵�id��������ֱ���Ӳ˵�
	 * @return List<Menu>
	 */
	@ResponseBody
	@RequestMapping(value="/menuById")
	public List<Menu> queryById(@RequestParam("id") Integer id){
		List<Menu> list = menuService.queryChildrenById(id);
		return list;
	}
	
	/**
	 * ���ݲ˵�id�����������е��Ӳ˵�
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
	 * ��ת���ҳ��
	 * @return String
	 */
	@RequestMapping(value="/menu")
	public String jumpAdd(Map<String, Object> map, @RequestParam(value="id", required=false) Integer id){
		if(id == null){//�����Ӽ�����ת
			map.put("menu", new Menu());//����һ���յĶ���
		}else{//���Ӽ�����ת
			map.put("pid", id);
			map.put("menu", new Menu());//����һ���յĶ���
		}
		return "menu/menuAdd";
	}
	
	/**
	 * �������е�ͼ��
	 * @return List<Picture>
	 */
	@RequestMapping(value="/getPic")
	@ResponseBody
	public List<Picture> getPic(){
		List<Picture> list = menuService.queryPic();
		return list;
	}
	
	/**
	 * ����˵��������
	 * @return String
	 */
	@RequestMapping(value="/menu",method=RequestMethod.POST)
	public String saveAdd(Menu menu){
		menuService.add(menu);//��Ӳ˵�
		return "redirect:/Jurisdictions/menu";
	}
	
	/**
	 * ��ת�޸�ҳ��
	 * @return String
	 */
	@RequestMapping(value="/menu/{id}",method=RequestMethod.GET)
	public String jumpUpdate(@PathVariable Integer id, Map<String, Object> map){
		Menu menu = menuService.queryById(id);
		map.put("menu", menu);//��ѯ���������map��
		map.put("picId", menuService.getId(menu.getPic()));//����ͷ���ֵ�õ�ͷ��id����ȥ��������
		return "menu/menuAdd";
	}
	
	/**
	 * �����޸�
	 * @return String
	 */
	@RequestMapping(value="/menu", method=RequestMethod.PUT)
	public String saveUpdate(Menu menu){
		menuService.update(menu);
		//��-BUG ��û�а취ˢ������ҳ��
		return "redirect:/Jurisdictions/menu";
	}
	
	/**
	 * ����id����ɾ��
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
	 * ��ѯ���еĲ˵� 
	 * @return List<Menu>
	 */
	@ResponseBody
	@RequestMapping(value="/queryAll")
	public List<Menu> queryAll(){
		return menuService.queryAll();
	}
	
	/**
	 * ��ѯȨ��
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getRole")
	public List<Permission> getPermissionById(@RequestParam("id") Integer id){
		Menu menu = menuService.queryById(id);
		return menuService.getPermissionById(id, menu.getLevel());//����id��ѯȨ��
	}
	
}