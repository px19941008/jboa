package com.accp.jboa.vo;

import com.accp.jboa.pojo.Employee;

public class EmployeeVo extends Employee {
	private String departmentName;
	private String positionName;
	private Integer positionid;
	private Integer departmentId;
	
	

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getPositionid() {
		return positionid;
	}

	public void setPositionid(Integer positionid) {
		this.positionid = positionid;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public EmployeeVo(String departmentName, String positionName, Integer positionid, Integer departmentId) {
		super();
		this.departmentName = departmentName;
		this.positionName = positionName;
		this.positionid = positionid;
		this.departmentId = departmentId;
	}

	public EmployeeVo() {
		super();
	}
  
	
	

}
