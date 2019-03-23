package com.yr.service.menu;

import java.util.List;
import java.util.Locale;

import com.yr.entity.Menu;
import com.yr.entity.Permission;
import com.yr.entity.Picture;

public interface MenuService {
	
	/**
	 * ��ѯ���еĸ����˵��������ҵ���Ӧ���Ӽ��˵�������װ��һ��json��ʽ���ص�ҳ��
	 * @return	String
	 */
	String query(String locale);
	
	/**
	 * ��ѯ���ص�jspչʾ
	 * @return String
	 */
	List<Menu> queryList();
	
	/**
	 * ���ݲ˵�id��������ֱ���Ӳ˵�
	 * @return List<Menu>
	 */
	List<Menu> queryChildrenById(Integer id);
	
	/**
	 * ���ݲ˵�id������е�����������Ӳ˵�
	 * @return List<Menu>
	 */
	List<Menu> queryChildrenByIds(Integer id);
	
	/**
	 * ��ѯ����ͷ��
	 * @return List<Picture>
	 */
	List<Picture> queryPic();
	
	/**
	 * ��Ӳ˵���Ϣ
	 */
	void add(Menu menu);
	
	/**
	 * ���ظ�id�Ķ���
	 * @param id
	 * @return Menu
	 */
	Menu queryById(Integer id);
	
	/**
	 * ����pic��ȡͼ����id
	 * @param pic
	 * @return String
	 */
	Integer getId(String pic);
	
	/**
	 * ����id�޸����е�����
	 * @param menu
	 */
	void update(Menu menu);
	
	/**
	 * ����idɾ���˵�
	 * @param id
	 */
	void delete(Integer id);
	
	/**
	 * ��ѯ���еĲ˵� 
	 * @return List<Menu>
	 */
	List<Menu> queryAll();
	
	/**
	 * ����id��ѯȨ�޶���
	 * @param id
	 * @return Permission
	 */
	List<Permission> getPermissionById(Integer id, Integer frequency);
}
