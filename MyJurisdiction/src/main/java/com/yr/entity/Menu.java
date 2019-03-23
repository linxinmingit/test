package com.yr.entity;

public class Menu extends BaseEntity {
	private Integer id;
	private String name;
	private String pic;
	private Integer pid;
	private String url;
	private Integer level;//改成Integer类型
	private boolean booleans;//判断是否有子菜单
	private String nameUs;

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

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public boolean isBooleans() {
		return booleans;
	}

	public void setBooleans(boolean booleans) {
		this.booleans = booleans;
	}

	public String getNameUs() {
		return nameUs;
	}

	public void setNameUs(String nameUs) {
		this.nameUs = nameUs;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", pic=" + pic + ", pid=" + pid + ", url=" + url + ", level="
				+ level + ", booleans=" + booleans + ", nameUs=" + nameUs + "]";
	}
	
}
