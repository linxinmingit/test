package com.yr.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yr.dao.user.UserDao;
import com.yr.entity.Page;
import com.yr.entity.Role;
import com.yr.entity.User;
import com.yr.service.role.RoleService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
	@Autowired
	@Qualifier("userDaoImpl")
	private UserDao userDao;
	
	@Autowired
	@Qualifier("roleServiceImpl")
	private RoleService roleService;
	
	/**
	 * 根据id查询用户的所有数据
	 * @return
	 */
	public User queryById(Integer id){
		return userDao.queryById(id);
	}
	
	/**
	 * 获取到页面的信息进行修改
	 * @param user
	 */
	public void update(User user){
		userDao.update(user);
	}
	
	/**
	 * 以分页的形式查询所有数据,将数据进行json格式化
	 * @param Page<User>
	 */
	public Page<User> query(Page<User> page){
		page = userDao.query(page);//获取page的数据
		List<User> list = page.getDataList();//获得list
		String json = "{\"code\": 0,\"msg\": \"\",\"count\": "+page.getTotalCount()+",\"data\": [";
		for (int i = 0; i < list.size(); i++) {
			User user = list.get(i);
			if(i == 0){//第一次进入
				json += "{\"id\": \""+user.getId()+"\",\"name\": \""+user.getName()+"\",\"email\": \""+user.getEmail()+"\",\"sex\": \""+user.getSex()+"\",\"status\": \""+user.getStatus()+"\",\"empno\" : \""+user.getEmpno()+"\""
						+ ",\"phone\": \""+user.getPhone()+"\",\"headUrl\": \""+user.getId()+"\",\"addr\": \""+user.getAddr().getProvince()+"-"+user.getAddr().getCity()+"-"+user.getAddr().getArea()+"\"}";
			}else{
				json += ",{\"id\": \""+user.getId()+"\",\"name\": \""+user.getName()+"\",\"email\": \""+user.getEmail()+"\",\"sex\": \""+user.getSex()+"\",\"status\": \""+user.getStatus()+"\",\"empno\" : \""+user.getEmpno()+"\""
						+ ",\"phone\": \""+user.getPhone()+"\",\"headUrl\": \""+user.getId()+"\",\"addr\": \""+user.getAddr().getProvince()+"-"+user.getAddr().getCity()+"-"+user.getAddr().getArea()+"\"}";
			}
			if(i == list.size()-1){//表示为最后一行
				json += "]}";
			}
		}
		page.setJson(json);//转成业务需要的json
		return page;
	}
	
	/**
	 * 添加用户信息
	 * @param user
	 */
	public void add(User user){
		Integer id = userDao.add(user);//获得添加的id,然后返回到关联表关联默认角色
		userDao.addUserRole(id);
	}
	
	/**
	 * 修改用户的状态,停用改为启用,启用改为停用
	 * @param id
	 */
	public void changeState(Integer id, Integer status){
		if(status == 0){//如果等于0,设置为启用改为1
			userDao.changeState(id, 1);
		}else{//反之,等于1改为0,设置为停用
			userDao.changeState(id, 0);
		}
	}
	
	/**
	 * 删除用户
	 * @param id
	 */
	public void delete(Integer id){
		userDao.delete(id);
	}
	
	/**
	 * 初始化用户密码
	 */
 	public void recovery(Integer id){
		userDao.recovery(id);
	}
 	
 	/**
	  * 查询角色表的所有信息
	  * @return List<Role>
	  */
	public List<Role> queryRole(){
		return roleService.queryRole();
	}
	
	/**
	 * 根据用户id查询对应的多个角色
	 * @param id
	 * @return List<Role>
	 */
 	public List<Role> queryRoleByUser(Integer id){
		return userDao.queryRoleByUser(id);
	}
	
 	/**
	 * 修改用户的角色,先删除后添加
	 */
	public void updateRole(Integer uid, Integer[] rid){
		userDao.deleteUserRole(uid);//先删除用户关联表中的id
		for (Integer integer : rid) {//循环角色的id
			userDao.addRole(uid, integer);//给用户添加对应的角色到关联表中
		}
	}
}
