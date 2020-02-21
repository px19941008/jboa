package com.accp.jboa.pojo;

public class Tbcount {
    private Integer countId;

    private Float money;

    private Integer year;

    private Integer month;

    private Integer departmentId;

    private Integer employeeId;
   
    private String departmentName;

    private String employeeName;

    public Integer getCountid() {
        return countId;
    }

    public void setCountid(Integer countid) {
        this.countId = countid;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDepartmentid() {
        return departmentId;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentId = departmentid;
    }

    public Integer getEmployeeid() {
        return employeeId;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeId = employeeid;
    }

    public String getDepartmentname() {
        return departmentName;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentName = departmentname == null ? null : departmentname.trim();
    }

    public String getEmployeename() {
        return employeeName;
    }

    public void setEmployeename(String employeename) {
        this.employeeName = employeename == null ? null : employeename.trim();
    }

	public Tbcount(Integer countId, Float money, Integer year, Integer month, Integer departmentId, Integer employeeId,
			String departmentName, String employeeName) {
		super();
		this.countId = countId;
		this.money = money;
		this.year = year;
		this.month = month;
		this.departmentId = departmentId;
		this.employeeId = employeeId;
		this.departmentName = departmentName;
		this.employeeName = employeeName;
	}

	public Tbcount() {
		super();
	}
    
}