package com.yr.entity;

import java.util.List;

public class Role extends BaseEntity {//½ÇÉ«±í
	private Integer id;
	private String name;
	private String desc;
	private List<Permission> permission;

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Permission> getPermission() {
		return permission;
	}

	public void setPermission(List<Permission> permission) {
		this.permission = permission;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", desc=" + desc + ", permission=" + permission + "]";
	}

}
