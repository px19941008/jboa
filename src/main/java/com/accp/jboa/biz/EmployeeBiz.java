package com.accp.jboa.biz;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.accp.jboa.dao.EmployeeMapper;
import com.accp.jboa.vo.EmployeeVo;

@Service("EmployeeBiz")
public class EmployeeBiz {
	
	@Resource
	private EmployeeMapper dao;
	
	
	public  EmployeeVo  queryByLogin(String employeeId,String password) {
		return dao.queryByLogin(employeeId, password);
	}
}
