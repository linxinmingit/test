package com.yr.dao.role;

import java.util.List;

import com.yr.entity.Page;
import com.yr.entity.Permission;
import com.yr.entity.Role;
import com.yr.entity.RolePermission;

public interface RoleDao {
	
	/**
	 * ���ݽ�ɫid�����û�Ȩ��
	 * @return Permission
	 */
	 Permission queryPer(Integer id);
	
	/**
	 * ���ݽ�ɫ��id������Ȩ��id
	 * @param List<RolePermission>
	 */
	 List<RolePermission> getPermissionId(Integer id);
	 
	 /**
	  * �Է�ҳ����ʽ��ѯ����
	  * @param page
	  * @return Page<Role>
	  */
	 Page<Role> query(Page<Role> page);
	 
	 /**
	  * ������ݵ���ɫ��
	  * @param role
	  */
	 void add(Role role);
	 
	 /**
	  * ����id��ѯRole(Ȩ��)����
	  * @param id
	  * @return Role
	  */
	 Role queryById(Integer id);
	 
	 /**
	  * �޸Ľ�ɫ����
	  * @param role
	  */
	 void update(Role role);
	 
	 /**
	  * ɾ����ɫ
	  * @param role
	  */
	 void delete(Integer id);
	 
	 /**
	  * ��ѯ��ɫ���������Ϣ
	  * @return List<Role>
	  */
	 List<Role> queryRole();
	 
	 /**
	  * ɾ��������ָ����ɫ����
	  */
	 void deleteRolePer(Integer id);
	 
	 /**
	  * ����ɫȨ�޹��������ָ������
	  * @param rid
	  * @param pid
	  */
	 void add(Integer rid, Integer pid);
	 
	 /**
	  * ����id��ѯ��ɫȨ�޹���������
	  */
	 List<RolePermission> queryRolePer(Integer id);
}
