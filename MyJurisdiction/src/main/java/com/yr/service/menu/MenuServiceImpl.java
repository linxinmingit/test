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
	 * ��ѯ���еĸ����˵��������ҵ���Ӧ���Ӽ��˵�������װ��һ��json��ʽ���ص�ҳ��
	 * @return	String
	 */
	public String query(String locale){
		String json = null;
		List<Menu> list = menuDao.query();
		if(list != null){//Ҳ����������һ������
			json = "{'contentManagement':";
			for (int i = 0; i < list.size(); i++) {
				Menu menu = list.get(i);//���ѭ���Ķ���
				if(i == 0){//��ʾ��һ�ν���
					if(locale.equals("en_US")){//Ӣ��
						json = json + "[{'title': '"+menu.getNameUs()+"','icon': '"+menu.getPic()+"','href': '"+menu.getUrl()+"','spread': false";
					}else{//����
						json = json + "[{'title': '"+menu.getName()+"','icon': '"+menu.getPic()+"','href': '"+menu.getUrl()+"','spread': false";
					}
				}else{
					if(locale.equals("en_US")){
						json = json + ",{'title': '"+menu.getNameUs()+"','icon': '"+menu.getPic()+"','href': '"+menu.getUrl()+"','spread': false";
					}else{
						json = json + ",{'title': '"+menu.getName()+"','icon': '"+menu.getPic()+"','href': '"+menu.getUrl()+"','spread': false";
					}
				}
				List<Menu> list1 = menuDao.queryChildrenById(menu.getId());//��ø��������µ��ӵ�����
				if(list1 != null){//Ҳ�������ٴ���һ���ӵ�����
					json = query1(json,list1,menu,locale);
				}else{//��ʾû���ӵ�����
					json = json + "}";
				}
				if(i == list.size()-1){//�����ѭ��Ϊ���һ��ѭ��,����������
					json += "]";
				}
			}
			json = json + "}";
		}
		return json;
	}
	
	//ִ�еݹ�,���json�ַ���
	public String query1(String json, List<Menu> list, Menu menu, String locale){
		for (int j = 0; j < list.size(); j++) {
			Menu menu1 = list.get(j);
			menu1.setUrl(menu.getUrl()+menu1.getUrl());
			if(j == 0){//��һ�ν���
				if(locale.equals("en_US")){//Ӣ��
					json += ",'children':[{'title': '"+menu1.getNameUs()+"','icon': '"+menu1.getPic()+"','href': '"+menu1.getUrl()+"','spread': false";
				}else{//����
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
			if(list1 != null){//���������null�����ִ�и÷���
				json = query1(json,list1,menu1,locale);
			}else{
				json += "}";
			}
			if(j == list.size()-1){//�����ѭ��Ϊ���һ��ѭ��,����������
				json += "]}";
			}
		}
		return json;
	}
	
	/**
	 * ��ѯһ���˵����ص�jspչʾ
	 * @return String
	 */
	public List<Menu> queryList(){
		List<Menu> list = menuDao.query();
		for (Menu menu : list) {//ѭ�����ϣ��ж��Ƿ������࣬�оͽ�booleans���Ը�Ϊtrue
			List<Menu> list1 = menuDao.queryChildrenById(menu.getId());
			if(list1 != null){
				menu.setBooleans(true);
			}
		}
		return list;
	}
	
	/**
	 * ���ݲ˵�id��������ֱ���Ӳ˵�
	 * @return List<Menu>
	 */
	public List<Menu> queryChildrenById(Integer id){
		List<Menu> list = menuDao.queryChildrenById(id);
		for (Menu menu : list) {//ѭ�����ϣ��ж��Ƿ������࣬�оͽ�booleans���Ը�Ϊtrue
			List<Menu> list1 = menuDao.queryChildrenById(menu.getId());
			if(list1 != null){
				menu.setBooleans(true);
			}
		}
		return list;
	}
	
	/**
	 * ���ݲ˵�id������е�����������Ӳ˵�
	 * @return List<Menu>
	 */
	public List<Menu> queryChildrenByIds(Integer id){
		List<Menu> list1 = menuDao.queryChildrenById(id);//ѭ��list��ʱ���ܶ�List��������ɾ��,�ı����ĳ���
		List<Menu> list = new ArrayList<>();
		list.addAll(list1);//��list1�����ݵ���list��
		if(list1 != null){//��Ϊnullѭ������
			for (Menu menu : list1) {//ѭ�����ϣ��ж��Ƿ������࣬�оͽ�booleans���Ը�Ϊtrue
				recursion(list, menu);//���еݹ�
			}
		}
		return list;
	}
	
	/**
	 * ���еݹ��ѯ�Ƿ��в˵���,���Ӳ˵����뼯��
	 * @return List<Menu>
	 */
	public void recursion(List<Menu> list, Menu menu){
		List<Menu> list1 = menuDao.queryChildrenById(menu.getId());//�ж��Ƿ�����ӽڵ�,�о��м���,û�о�ΪNull
		if(list1 != null){//���������Null����������
			menu.setBooleans(true);//���Ӳ˵�����Ϊtrue
			for (Menu menu1 : list1) {//ѭ������
				list.add(menu1);
				recursion(list, menu1);//���ж��Ƿ����Ӳ˵�
			}
		}
	}
	
	/**
	 * ��ѯ����ͷ��
	 * @return List<Picture>
	 */
 	public List<Picture> queryPic(){
		return menuDao.queryPic();
	}
 	
 	/**
	 * ��Ӳ˵���Ϣ
	 */
	public void add(Menu menu){
		menu.setPic(menuDao.queryPicById(Integer.valueOf(menu.getPic())).getUrl());//��ȡͷ����е�ֵ
		Menu menus = queryById(menu.getPid());//��ȡ�����Ķ���
		menu.setLevel(menus.getLevel()+1);//��ӵĲ˵������Ǹ�������+1
		menuDao.add(menu);
	}
	
	/**
	 * ���ظ�id�Ķ���
	 * @param id
	 * @return Menu
	 */
	public Menu queryById(Integer id){
		return menuDao.queryById(id);
	}
	
	/**
	 * ����pic��ȡͼ����id
	 * @param pic
	 * @return String
	 */
	public Integer getId(String pic){
		return menuDao.getId(pic);
	}
	
	/**
	 * ����id�޸����е�����
	 * @param menu
	 */
	public void update(Menu menu){
		menu.setPic(menuDao.queryPicById(Integer.valueOf(menu.getPic())).getUrl());
		Menu menus = queryById(menu.getPid());//��ȡ�����Ķ���
		menu.setLevel(menus.getLevel()+1);//��ӵĲ˵������Ǹ�������+1	����ı伶��
		menuDao.update(menu);
	}
	
	/**
	 * ����idɾ���˵�,���һ��id��ɾ�����������е��Ӳ˵�Ҳ��ɾ��
	 * @param id
	 */
	public void delete(Integer id){
		menuDao.delete(id);
		menuDao.deletes(id);
	}
	
	/**
	 * ��ѯ���еĲ˵� 
	 * @return List<Menu>
	 */
	public List<Menu> queryAll(){
		return menuDao.queryAll();
	}
	
	/**
	 * ����id��ѯȨ�޶���
	 * @param id
	 * @return Permission
	 */
	public List<Permission> getPermissionById(Integer id, Integer frequency){
		return menuDao.getPermissionById(id, frequency);
	}
}
