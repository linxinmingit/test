package com.yr.service.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yr.dao.role.RoleDao;
import com.yr.entity.Page;
import com.yr.entity.Role;
import com.yr.entity.RolePermission;

import net.sf.json.JSONArray;

@Service("roleServiceImpl")
public class RoleServiceImpl implements RoleService{
	@Autowired
	@Qualifier("roleDaoImpl")
	private RoleDao roleDao;
	
	 /**
	  * �Է�ҳ����ʽ��ѯ����
	  * @param page
	  * @return Page<Role>
	  */
	public Page<Role> query(Page<Role> page){
		page = roleDao.query(page);//��ȡpage������
		List<Role> list = page.getDataList();//���list
		String json = JSONArray.fromObject(list).toString(); 
		json = "{\"code\": 0,\"msg\": \"\",\"count\": "+page.getTotalCount()+",\"data\":"+json+"}";
		page.setJson(json);//ת��ҵ����Ҫ��json
		return page;
	}
	
	/**
	  * ������ݵ���ɫ��
	  * @param role
	  */
	 public void add(Role role){
		 roleDao.add(role);
	 }
	 
	 /**
	  * ����id��ѯRole(Ȩ��)����
	  * @param id
	  * @return Role
	  */
	 public Role queryById(Integer id){
		return roleDao.queryById(id);
	 }
	 
	 /**
	  * �޸Ľ�ɫ����
	  * @param role
	  */
	 public void update(Role role){
		 roleDao.update(role);
	 }
	 
	 /**
	  * ɾ����ɫ
	  * @param role
	  */
	 public void delete(Integer id){
		 roleDao.delete(id);
	 }
	 
	 /**
	  * ��ѯ��ɫ���������Ϣ
	  * @return List<Role>
	  */
	 public List<Role> queryRole(){
		 return roleDao.queryRole();
	 }
	 
	 /**
	  * ����ɫ��Ȩ��
	  * @param id
	  */
	 public void rolePermission(Integer rid, Integer[] pid){
		 roleDao.deleteRolePer(rid);//ɾ��rid����������
		 for (Integer integer : pid) {//ѭ��Ȩ��id����
			 roleDao.add(rid, integer);
		 }
	 }
	 
	 /**
	  * ����id��ѯ��ɫȨ�޹���������
	  */
	 public List<RolePermission> queryRolePer(Integer id){
		 return roleDao.queryRolePer(id);
	 }
}
