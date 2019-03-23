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
	 * ����id��ѯ�û�����������
	 * @return
	 */
	public User queryById(Integer id){
		return userDao.queryById(id);
	}
	
	/**
	 * ��ȡ��ҳ�����Ϣ�����޸�
	 * @param user
	 */
	public void update(User user){
		userDao.update(user);
	}
	
	/**
	 * �Է�ҳ����ʽ��ѯ��������,�����ݽ���json��ʽ��
	 * @param Page<User>
	 */
	public Page<User> query(Page<User> page){
		page = userDao.query(page);//��ȡpage������
		List<User> list = page.getDataList();//���list
		String json = "{\"code\": 0,\"msg\": \"\",\"count\": "+page.getTotalCount()+",\"data\": [";
		for (int i = 0; i < list.size(); i++) {
			User user = list.get(i);
			if(i == 0){//��һ�ν���
				json += "{\"id\": \""+user.getId()+"\",\"name\": \""+user.getName()+"\",\"email\": \""+user.getEmail()+"\",\"sex\": \""+user.getSex()+"\",\"status\": \""+user.getStatus()+"\",\"empno\" : \""+user.getEmpno()+"\""
						+ ",\"phone\": \""+user.getPhone()+"\",\"headUrl\": \""+user.getId()+"\",\"addr\": \""+user.getAddr().getProvince()+"-"+user.getAddr().getCity()+"-"+user.getAddr().getArea()+"\"}";
			}else{
				json += ",{\"id\": \""+user.getId()+"\",\"name\": \""+user.getName()+"\",\"email\": \""+user.getEmail()+"\",\"sex\": \""+user.getSex()+"\",\"status\": \""+user.getStatus()+"\",\"empno\" : \""+user.getEmpno()+"\""
						+ ",\"phone\": \""+user.getPhone()+"\",\"headUrl\": \""+user.getId()+"\",\"addr\": \""+user.getAddr().getProvince()+"-"+user.getAddr().getCity()+"-"+user.getAddr().getArea()+"\"}";
			}
			if(i == list.size()-1){//��ʾΪ���һ��
				json += "]}";
			}
		}
		page.setJson(json);//ת��ҵ����Ҫ��json
		return page;
	}
	
	/**
	 * ����û���Ϣ
	 * @param user
	 */
	public void add(User user){
		Integer id = userDao.add(user);//�����ӵ�id,Ȼ�󷵻ص����������Ĭ�Ͻ�ɫ
		userDao.addUserRole(id);
	}
	
	/**
	 * �޸��û���״̬,ͣ�ø�Ϊ����,���ø�Ϊͣ��
	 * @param id
	 */
	public void changeState(Integer id, Integer status){
		if(status == 0){//�������0,����Ϊ���ø�Ϊ1
			userDao.changeState(id, 1);
		}else{//��֮,����1��Ϊ0,����Ϊͣ��
			userDao.changeState(id, 0);
		}
	}
	
	/**
	 * ɾ���û�
	 * @param id
	 */
	public void delete(Integer id){
		userDao.delete(id);
	}
	
	/**
	 * ��ʼ���û�����
	 */
 	public void recovery(Integer id){
		userDao.recovery(id);
	}
 	
 	/**
	  * ��ѯ��ɫ���������Ϣ
	  * @return List<Role>
	  */
	public List<Role> queryRole(){
		return roleService.queryRole();
	}
	
	/**
	 * �����û�id��ѯ��Ӧ�Ķ����ɫ
	 * @param id
	 * @return List<Role>
	 */
 	public List<Role> queryRoleByUser(Integer id){
		return userDao.queryRoleByUser(id);
	}
	
 	/**
	 * �޸��û��Ľ�ɫ,��ɾ�������
	 */
	public void updateRole(Integer uid, Integer[] rid){
		userDao.deleteUserRole(uid);//��ɾ���û��������е�id
		for (Integer integer : rid) {//ѭ����ɫ��id
			userDao.addRole(uid, integer);//���û���Ӷ�Ӧ�Ľ�ɫ����������
		}
	}
}
