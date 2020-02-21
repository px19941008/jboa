package com.accp.jboa.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accp.jboa.biz.EmployeeBiz;
import com.accp.jboa.vo.EmployeeVo;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/c/Employee")
public class EmployeeAction {
	
	@Resource
	private EmployeeBiz eb;
	
	
	@PostMapping(value = "login",produces = "text/html;charset=utf-8")
	@ResponseBody
	public  Object login(HttpSession session,String employeeId,String password) {
		EmployeeVo ev=eb.queryByLogin(employeeId, password);
		System.out.println("第一次提交");
		if(ev==null){
			return "no";
		}else {
			session.setAttribute("user", ev);
			session.setAttribute("emp", ev);
			return JSON.toJSONString(ev);
		}
	}
	
	@GetMapping("extis")
	public  String extis(HttpSession session) {
		session.removeAttribute("user");
		return "/ui/login.jsp";
	}
}
