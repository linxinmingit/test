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
	  * 以分页的形式查询数据
	  * @param page
	  * @return Page<Role>
	  */
	public Page<Role> query(Page<Role> page){
		page = roleDao.query(page);//获取page的数据
		List<Role> list = page.getDataList();//获得list
		String json = JSONArray.fromObject(list).toString(); 
		json = "{\"code\": 0,\"msg\": \"\",\"count\": "+page.getTotalCount()+",\"data\":"+json+"}";
		page.setJson(json);//转成业务需要的json
		return page;
	}
	
	/**
	  * 添加数据到角色表
	  * @param role
	  */
	 public void add(Role role){
		 roleDao.add(role);
	 }
	 
	 /**
	  * 根据id查询Role(权限)对象
	  * @param id
	  * @return Role
	  */
	 public Role queryById(Integer id){
		return roleDao.queryById(id);
	 }
	 
	 /**
	  * 修改角色数据
	  * @param role
	  */
	 public void update(Role role){
		 roleDao.update(role);
	 }
	 
	 /**
	  * 删除角色
	  * @param role
	  */
	 public void delete(Integer id){
		 roleDao.delete(id);
	 }
	 
	 /**
	  * 查询角色表的所有信息
	  * @return List<Role>
	  */
	 public List<Role> queryRole(){
		 return roleDao.queryRole();
	 }
	 
	 /**
	  * 给角色赋权限
	  * @param id
	  */
	 public void rolePermission(Integer rid, Integer[] pid){
		 roleDao.deleteRolePer(rid);//删除rid关联表数据
		 for (Integer integer : pid) {//循环权限id数组
			 roleDao.add(rid, integer);
		 }
	 }
	 
	 /**
	  * 根据id查询角色权限关联表数据
	  */
	 public List<RolePermission> queryRolePer(Integer id){
		 return roleDao.queryRolePer(id);
	 }
}
