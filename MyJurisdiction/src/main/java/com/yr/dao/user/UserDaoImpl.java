package com.yr.dao.user;


import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.yr.dao.common.PageDao;
import com.yr.entity.Addr;
import com.yr.entity.Page;
import com.yr.entity.Role;
import com.yr.entity.User;
import com.yr.util.DesUtil;

@Repository("userDaoImpl")
public class UserDaoImpl extends PageDao implements UserDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * �����û����ֲ�ѯ�û���������
	 * @return	User
	 */
	public User queryByName(String name){
		User user = null;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String sql = "select * from user where name = ?";
			con = jdbcTemplate.getDataSource().getConnection();
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			user = new User();
			user.setId(resultSet.getInt("id"));
			user.setName(resultSet.getString("name"));
			user.setEmpno(resultSet.getString("empno"));
			user.setPhone(resultSet.getString("phone"));
			user.setStatus(resultSet.getInt("status"));
			user.setEmail(resultSet.getString("email"));
			user.setSex(resultSet.getInt("sex"));
			user.setHeadUrl(resultSet.getString("headUrl"));
			user.setPasswd(resultSet.getString("passwd"));
			user.setAddr(getAddr(resultSet.getInt("addr_id")));
			user.setCreateTime(resultSet.getTimestamp("createTime"));
			user.setCreateEmp(resultSet.getString("createEmp"));
			user.setUpdateTime(resultSet.getTimestamp("updateTime"));
			user.setUpdateEmp(resultSet.getString("updateEmp"));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				resultSet.close();
				preparedStatement.close();
				con.close();//�رո�����
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	
	/**
	 * ����id��ѯ�û�����������
	 * @return	User
	 */
	public User queryById(Integer id){
		User user = null;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String sql = "select * from user where id = ?";
			con = jdbcTemplate.getDataSource().getConnection();
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			user = new User();
			user.setId(resultSet.getInt("id"));
			user.setName(resultSet.getString("name"));
			user.setEmpno(resultSet.getString("empno"));
			user.setPhone(resultSet.getString("phone"));
			user.setStatus(resultSet.getInt("status"));
			user.setEmail(resultSet.getString("email"));
			user.setSex(resultSet.getInt("sex"));
			user.setHeadUrl(resultSet.getString("headUrl"));
			user.setPasswd(resultSet.getString("passwd"));
			user.setAddr(getAddr(resultSet.getInt("addr_id")));
			user.setCreateTime(resultSet.getTimestamp("createTime"));
			user.setCreateEmp(resultSet.getString("createEmp"));
			user.setUpdateTime(resultSet.getTimestamp("updateTime"));
			user.setUpdateEmp(resultSet.getString("updateEmp"));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				resultSet.close();
				preparedStatement.close();
				con.close();//�رո�����
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	
	/**
	 * ��ȡ��ҳ�����Ϣ�����޸�
	 * @param user
	 */
	public void update(User user){
		String sql = "update user set name=?,empno=?,phone=?,status=?,email=?,sex=?,headUrl=?,passwd=?,addr_id=?,createTime=?,createEmp=?,updateTime=?,updateEmp=? where id = ?";
		jdbcTemplate.update(sql,user.getName(),user.getEmpno(),user.getPhone(),user.getStatus(),user.getEmail(),user.getSex(),user.getHeadUrl(),user.getPasswd(),user.getAddr().getId(),user.getCreateTime(),user.getCreateEmp(),user.getUpdateTime(),user.getUpdateEmp(),user.getId());
		updateForAddr(user);//�޸ĵ�ַ��ֵ
	}
	
	/**
	 * ����user��addr���Ե�id�޸ĵ�ַ��
	 * @param user
	 */
	public void updateForAddr(User user){
		String sql = "update addr set province=?,city=?,area=?,createTime=?,createEmp=?,updateTime=?,updateEmp=? where id = ?";
		jdbcTemplate.update(sql, user.getAddr().getProvince(), user.getAddr().getCity(), user.getAddr().getArea(), user.getCreateTime(), user.getCreateEmp(), user.getUpdateTime(), user.getUpdateEmp(), user.getAddr().getId());
	}
	
	/**
	 * ����id��ѯ��ַ����
	 * @param id
	 * @return Addr
	 */
	public Addr getAddr(Integer id){
		String sql = "select * from addr where id = ?";
		RowMapper<Addr> mapper = new BeanPropertyRowMapper<>(Addr.class);
		Addr addr = jdbcTemplate.queryForObject(sql, mapper, id);
		return addr;
	}
	
	/**
	 * �Է�ҳ����ʽ��ѯ����
	 * @param page
	 * @return Page<Deposit>
	 */
	public Page<User> query(Page<User> page) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			String sql = "select * from user where 1 = 1 ";
			if(page.getSearch() != null && !page.getSearch().equals("")){//���������ֵ�����ʾΪģ����ѯ
				sql += " and name like '%" + page.getSearch() + "%' ";
			}
			setTotalCount(page, sql, con);//��ѯ����������ֵ������page
			sql += " limit "+page.getLimitNum()+","+page.getPageSize();//��limitʵ�ַ�ҳЧ��
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			List<User> list = new ArrayList<>();//ʵ����һ������,����ѯ��������װ�뼯��,Ȼ�����page
			while(resultSet.next()){
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setName(resultSet.getString("name"));
				user.setEmpno(resultSet.getString("empno"));
				user.setPhone(resultSet.getString("phone"));
				user.setStatus(resultSet.getInt("status"));
				user.setEmail(resultSet.getString("email"));
				user.setSex(resultSet.getInt("sex"));
				user.setHeadUrl(resultSet.getString("headUrl"));
				user.setPasswd(resultSet.getString("passwd"));
				user.setAddr(getAddr(resultSet.getInt("addr_id")));
				user.setCreateTime(resultSet.getTimestamp("createTime"));
				user.setCreateEmp(resultSet.getString("createEmp"));
				user.setUpdateTime(resultSet.getTimestamp("updateTime"));
				user.setUpdateEmp(resultSet.getString("updateEmp"));
				list.add(user);
			}
			page.setDataList(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				resultSet.close();
				preparedStatement.close();
				con.close();//�رո�����
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return page;//��󷵻�page
	}
	
	/**
	 * ����û�
	 * @param user
	 */
	public Integer add(User user){
		String sql = "insert into user(name,empno,phone,status,email,sex,headUrl,passwd,addr_id)values(?,?,?,?,?,?,?,?,?);";
		Integer addrId = addByAddr(user);//��õ�ַ��id
		//jdbcTemplate.update(sql,user.getName(),user.getEmpno(),user.getPhone(),1,user.getEmail(),user.getSex(),user.getHeadUrl(),user.getPasswd(),addrId);
		KeyHolder der = new GeneratedKeyHolder();//�����������ڵĶ���
		jdbcTemplate.update(new PreparedStatementCreator() {//����updata����,��һ������ҪPreparedStatementCreator����,
			//��дcreatePreparedStatement����,�ڶ���ΪGeneratedKeyHolder����(��������)
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,new String[] {"id"});
				ps.setString(1, user.getName());
				ps.setString(2, user.getEmpno());
				ps.setString(3, user.getPhone());
				ps.setInt(4, 1);
				ps.setString(5, user.getEmail());
				ps.setInt(6, user.getSex());
				ps.setString(7, user.getHeadUrl());
				ps.setString(8, user.getPasswd());
				ps.setInt(9, addrId);
				return ps;//�����󷵻س�ȥ
			}
		}, der);
		return der.getKey().intValue();//�����������󷵻�key,�ٷ��س�ֵ
	}
	
	/**
	 * ����û���ʱ��Ĭ����ӽ��û�id����ͨ��ɫid����
	 */
	public void addUserRole(Integer id){
		String sql = "insert into user_role(uid,rid)values(?,3);";
		jdbcTemplate.update(sql, id);
	}
	
	/**
	 * ����û���ַ,���õ�����id�󶨵��û�
	 * @param user
	 * @return Integer
	 */
	public Integer addByAddr(User user){
		String sql = "insert into addr(province,city,area)values(?,?,?)";
		KeyHolder der = new GeneratedKeyHolder();//�����������ڵĶ���
		jdbcTemplate.update(new PreparedStatementCreator() {//����updata����,��һ������ҪPreparedStatementCreator����,
			//��дcreatePreparedStatement����,�ڶ���ΪGeneratedKeyHolder����(��������)
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,new String[] {"id"});
				ps.setString(1, user.getAddr().getProvince());
				ps.setString(2, user.getAddr().getCity());
				ps.setString(3, user.getAddr().getArea());
				return ps;//�����󷵻س�ȥ
			}
		}, der);
		return der.getKey().intValue();//�����������󷵻�key,�ٷ��س�ֵ
	}
	
	/**
	 * �޸��û���״̬,ͣ�ø�Ϊ����,���ø�Ϊͣ��
	 * @param id
	 */
	public void changeState(Integer id, Integer status){
		String sql = "update user set status = ? where id = ?";
		jdbcTemplate.update(sql, status, id);
	}
	
	/**
	 * ɾ���û�
	 * @param id
	 */
	public void delete(Integer id){
		String sql = "delete from user where id = ?";
		jdbcTemplate.update(sql, id);
	}
	
	/**
	 * ��ʼ���û�����Ϊ0000
	 */
	public void recovery(Integer id){
		String ing = DesUtil.encrypt("0000", Charset.forName("gb2312"), "password");
		String sql = "update user set passwd = ? where id = ?";
		jdbcTemplate.update(sql, ing, id);
	}
	
	/**
	 * �����û�id��ѯ��Ӧ�Ķ����ɫ
	 * @param id
	 * @return List<Role>
	 */
	public List<Role> queryRoleByUser(Integer id){
		String sql = "select c.* from user a join user_role b on a.id = b.uid join role c on b.rid = c.id where a.id = ?";
		RowMapper<Role> mapper = new BeanPropertyRowMapper<>(Role.class);
		return jdbcTemplate.query(sql, mapper, id);
	}
	
	/**
	 * ɾ���������û�����Ӧ�Ľ�ɫ
	 * @param id
	 */
	public void deleteUserRole(Integer id){
		String sql = "delete from user_role where uid = ?";
		jdbcTemplate.update(sql, id);
	}
	
	/**
	 * �����û���id�����Ӧ��Ȩ�޵�������
	 * @param uid	�û�id
	 * @param rid	Ȩ��id
	 */
 	public void addRole(Integer uid, Integer rid){
		String sql = "insert into user_role(uid,rid)values(?,?)";
		jdbcTemplate.update(sql, uid, rid);
	}
}
