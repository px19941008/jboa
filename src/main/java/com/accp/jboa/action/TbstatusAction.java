package com.accp.jboa.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accp.jboa.biz.TbstatusBiz;
import com.accp.jboa.pojo.Tbcount;
import com.accp.jboa.pojo.Tbstatus;
import com.accp.jboa.vo.EmployeeVo;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/c/Tbstatus")
public class TbstatusAction {
	@Resource
	private TbstatusBiz tb;
	
	
	/**
	 * 新增报销表
	 */
	
	
    /**
     * 用统计表
     * @return
     */
	@PostMapping(value="queryAll",produces = "text/html; charset=utf-8")
	@ResponseBody
	public  String queryAll() {
		return JSON.toJSONString(tb.queryAll());
	}
   
	/**
	 * 
	 * @Title: monthList
	 * @Description: 查看月度报表
	 * @param session
	 * @param pageNum
	 * @param pageSize
	 * @param year
	 * @param startMonth
	 * @param endMonth
	 * @param model
	 * @return
	 * String
	 */
	@RequestMapping(value = "monthList", method = RequestMethod.GET)
	public String monthList(HttpSession session, Integer pageNum, Integer pageSize, Integer year, Integer startMonth,
			Integer endMonth, Model model) {
		if (pageNum == null) {
			pageNum = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		if (year != null && year == 0) {
			year = null;
		}
		if (startMonth != null && startMonth == 0) {
			startMonth = null;
		}
		if (endMonth != null && endMonth == 0) {
			endMonth = null;
		}
		EmployeeVo emp = (EmployeeVo) session.getAttribute("emp");
		PageInfo<Tbcount> page = tb.findStatisticsByMonth(emp, year, startMonth, endMonth, pageNum, pageSize);
		model.addAttribute("PAGE", page);
		model.addAttribute("year", year);
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		return "/ui/reimburseMonth.jsp";
	}
	
	
	
}
