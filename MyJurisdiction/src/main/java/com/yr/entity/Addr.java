package com.yr.entity;

public class Addr extends BaseEntity {
	private Integer id;
	private String province;
	private String city;
	private String area;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "Addr [id=" + id + ", province=" + province + ", city=" + city + ", area=" + area + "]";
	}
}
