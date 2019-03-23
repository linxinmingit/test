package com.yr.service.user;

import java.util.List;

import com.yr.entity.Page;
import com.yr.entity.Role;
import com.yr.entity.User;

public interface UserService {
	
	/**
	 * ����id��ѯ�û�����������
	 * @return
	 */
	User queryById(Integer id);
	
	/**
	 * ��ȡ��ҳ�����Ϣ�����޸�
	 * @param user
	 */
	void update(User user);
	
	/**
	 * �Է�ҳ����ʽ��ѯ���е�����
	 * @return Page<User>
	 */
	Page<User> query(Page<User> page);
	
	/**
	 * ����û���Ϣ
	 * @param user
	 */
	void add(User user);
	
	/**
	 * �޸��û���״̬,ͣ�ø�Ϊ����,���ø�Ϊͣ��
	 * @param id
	 */
	void changeState(Integer id, Integer status);
	
	/**
	 * ɾ���û�
	 * @param id
	 */
	void delete(Integer id);
	
	/**
	 * ��ʼ���û�����
	 */
	void recovery(Integer id);
	
	/**
	  * ��ѯ��ɫ���������Ϣ
	  * @return List<Role>
	  */
	List<Role> queryRole();
	
	/**
	 * �����û�id��ѯ��Ӧ�Ķ����ɫ
	 * @param id
	 * @return List<Role>
	 */
	List<Role> queryRoleByUser(Integer id);
	
	/**
	 * �޸��û��Ľ�ɫ
	 */
	void updateRole(Integer uid, Integer[] rid);
}
