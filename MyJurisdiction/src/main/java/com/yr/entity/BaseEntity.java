package com.yr.entity;

import java.sql.Timestamp;
import java.util.Date;

public class BaseEntity {
	private Timestamp createTime;//对应数据库的dateTime类型
	private String createEmp;
	private Timestamp updateTime;
	private String updateEmp;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateEmp() {
		return createEmp;
	}

	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateEmp() {
		return updateEmp;
	}

	public void setUpdateEmp(String updateEmp) {
		this.updateEmp = updateEmp;
	}

	@Override
	public String toString() {
		return "BaseEntry [createTime=" + createTime + ", createEmp=" + createEmp + ", updateTime=" + updateTime
				+ ", updateEmp=" + updateEmp + "]";
	}

}
