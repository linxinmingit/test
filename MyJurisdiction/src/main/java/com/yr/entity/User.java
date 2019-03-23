package com.yr.entity;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class User extends BaseEntity {
	private Integer id;
	@NotEmpty
	@Size(min = 0, max = 15, message = "{Size.user.name}") // 长度应该在0-15之间
	private String name;
	// @Pattern(regexp="/^[x]/")//以x开头
	private String empno;
	// @NotEmpty
	// @Pattern(regexp="^1[34578]\\d{9}$")//必须为手机号码
	private String phone;
	private Integer status;
	// @NotEmpty
	// @Email
	private String email;
	private Integer sex;
	private String headUrl;
	@NotEmpty
	@Size(min = 0, max = 15)
	private String passwd;
	// @Size(min=0, max=20)
	private Addr addr;
	private List<Role> role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Addr getAddr() {
		return addr;
	}

	public void setAddr(Addr addr) {
		this.addr = addr;
	}

	public List<Role> getRole() {
		return role;
	}

	public void setRole(List<Role> role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", empno=" + empno + ", phone=" + phone + ", status=" + status
				+ ", email=" + email + ", sex=" + sex + ", headUrl=" + headUrl + ", passwd=" + passwd + ", addr=" + addr
				+ ", role=" + role + "]";
	}

}
