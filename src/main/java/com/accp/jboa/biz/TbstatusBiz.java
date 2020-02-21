package com.accp.jboa.biz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.accp.jboa.dao.TbstatusMapper;
import com.accp.jboa.pojo.Tbcount;
import com.accp.jboa.pojo.Tbstatus;
import com.accp.jboa.vo.EmployeeVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("TbstatusBiz")
public class TbstatusBiz {
	
	@Resource
	private TbstatusMapper dao;
	
	public  List<Tbstatus>  queryAll(){
		return dao.queryAll();
	}
	/**
	 * 新增报销表
	 */
	
	public int addStatistics(String currTime) {
		return dao.saveStatistics(currTime);
	};
	
	/**
	 * 月统计表
	 * @param emp
	 * @param year
	 * @param startMonth
	 * @param endMonth
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Tbcount> findStatisticsByMonth(EmployeeVo emp, Integer year, Integer startMonth,
			Integer endMonth, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<Tbcount>(dao.queryStatisticsByMonth(emp, year, startMonth, endMonth));
	};
	
	/**
	 * 年统计表
	 */
	public PageInfo<Tbstatus> findStatisticsByYear(EmployeeVo emp, Integer startYear, Integer endYear,
			Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<Tbstatus>(dao.queryStatisticsByYear(emp, startYear, endYear));
	};
}
