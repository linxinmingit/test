package com.yr.dao.user;

import java.util.List;

import com.yr.entity.Addr;
import com.yr.entity.Page;
import com.yr.entity.Role;
import com.yr.entity.User;

public interface UserDao {
	
	/**
	 * �����û����ֲ�ѯ�û���������
	 * @return	User
	 */
	User queryByName(String name);
	
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
	 * ����id��ѯ��ַ����
	 * @param id
	 * @return Addr
	 */
	Addr getAddr(Integer id);
	
	/**
	 * ����user��addr���Ե�id�޸ĵ�ַ��
	 * @param user
	 */
	void updateForAddr(User user);
	
	/**
	 * �Է�ҳ��ѯ��������
	 * @return List<User>
	 */
	Page<User> query(Page<User> page);
	
	/**
	 * ����û���Ϣ
	 * @param user
	 */
	Integer add(User user);
	
	/**
	 * ����û���ʱ��Ĭ����ӽ��û�id����ͨ��ɫid����
	 */
	void addUserRole(Integer id);
	
	/**
	 * ����û���ַ,���õ�����id�󶨵��û�
	 * @param user
	 * @return Integer
	 */
	Integer addByAddr(User user);
	
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
	 * �����û�id��ѯ��Ӧ�Ķ����ɫ
	 * @param id
	 * @return List<Role>
	 */
	List<Role> queryRoleByUser(Integer id);
	
	/**
	 * ɾ���������û�����Ӧ�Ľ�ɫ
	 * @param id
	 */
	void deleteUserRole(Integer id);
	
	/**
	 * �����û���id�����Ӧ��Ȩ�޵�������
	 * @param uid	�û�id
	 * @param rid	Ȩ��id
	 */
	void addRole(Integer uid, Integer rid);
}
