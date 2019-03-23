package com.yr.service.role;

import java.util.List;

import com.yr.entity.Page;
import com.yr.entity.Role;
import com.yr.entity.RolePermission;

public interface RoleService {
	
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
	  * ����ɫ��Ȩ��
	  * @param id
	  */
	 void rolePermission(Integer rid, Integer[] pid);
	 
	 /**
	  * ����id��ѯ��ɫȨ�޹���������
	  */
	 List<RolePermission> queryRolePer(Integer id);
}
