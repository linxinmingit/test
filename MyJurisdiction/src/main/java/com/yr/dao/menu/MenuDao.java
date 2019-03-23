package com.yr.dao.menu;

import java.util.List;

import com.yr.entity.Menu;
import com.yr.entity.Permission;
import com.yr.entity.Picture;

public interface MenuDao {
	
	/**
	 * ��ѯ�˵������Ĺ�����
	 * @return	List<Menu>
	 */
	List<Menu> query();
	
	/**
	 * ����id��ѯ�ò˵��µ������Ӳ˵�
	 * @return List<Menu>
	 */
	List<Menu> queryChildrenById(Integer id);
	
	/**
	 * ��ѯ����ͷ��
	 * @return List<Picture>
	 */
	List<Picture> queryPic();
	
	/**
	 * ����id��ѯͷ��url
	 * @param id
	 * @return Picture
	 */
	Picture queryPicById(Integer id);
	
	/**
	 * ��Ӳ˵���Ϣ
	 */
	void add(Menu menu);
	
	/**
	 * ���ظ�id�Ĳ˵�����
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
	 * ���һ��id��ɾ�����������е��Ӳ˵�Ҳ��ɾ��
	 * @param pid
	 */
	void deletes(Integer pid);
	
	/**
	 * ����id��ѯȨ�޶���
	 * @param id
	 * @return Permission
	 */
	List<Permission> getPermissionById(Integer id, Integer frequency);
}
