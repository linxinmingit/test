package com.yr.entity;

public class Permission extends BaseEntity {//权限表
	private Integer id;
	private String name;
	private String url;
	private String method;
	private Integer frequency;//需要迭代加空格数
	//private Menu menu;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/*public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}*/

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	@Override
	public String toString() {
		return "Permission [id=" + id + ", name=" + name + ", url=" + url + ", method=" + method + ", frequency="
				+ frequency + "]";
	}

}
