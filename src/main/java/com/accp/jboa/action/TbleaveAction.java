package com.accp.jboa.action;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accp.jboa.biz.TbleaveBiz;
import com.accp.jboa.pojo.Employee;
import com.accp.jboa.pojo.Tbcheck;
import com.accp.jboa.pojo.Tbleave;
import com.accp.jboa.vo.TbleaveVo;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/api/leave")
public class TbleaveAction {
	
	@Resource
	private TbleaveBiz tb;
	
	/**
	 * 查询全部分页
	 * @return
	 */
	@GetMapping("query")
	@ResponseBody
	public PageInfo<TbleaveVo> queryPage(HttpSession session,Integer currentPage,Integer pageSize,String startTime,String endTime) {
		Employee user =(Employee)session.getAttribute("user");
		if("".equals(startTime)) {
			startTime=null;
		}
		if("".equals(endTime)) {
			endTime=null;
		}
		return tb.queryByPage(user.getEmployeeid(), user.getPositionid(), user.getDepartmentid(), currentPage, startTime, endTime);
	}
	
	/**
	 * 添加
	 * @param session
	 * @param tbleve
	 * @return
	 */
	@PostMapping("add")
	@ResponseBody
	public  String addLeave(HttpSession session,Tbleave tbleve) {
		
		Employee user =(Employee)session.getAttribute("user");
		tb.addTbLeave(tbleve,user);
		return "yes";
	}
	
	/**
	 * 根据id查询
	 * @param tbId
	 * @return
	 */
	@GetMapping("queryById")
	public String  queryById(Model model, Integer tbId,Integer status) {
		model.addAttribute("tb", tb.queryById(tbId));
		model.addAttribute("status", status);
		System.out.println(tbId);
		return  "/ui/seeLeave.jsp";
	}
	
	/* 审核 */
	@PostMapping("modifyStatus")
	@ResponseBody
	public   String modifyStatus(HttpSession session,Integer tbId,Integer status,String event) {
		Employee user =(Employee)session.getAttribute("user");
		System.out.println(user);
		Tbcheck tbs= new Tbcheck(tbId.toString(), 1, new Date(), user.getEmployeeid(), status, event);
		System.out.println(tbs);
		tb.modifyStatus(tbId, status,tbs);
		return "yes";
	}
}
