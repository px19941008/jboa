package com.accp.jboa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.accp.jboa.pojo.Tbcount;
import com.accp.jboa.pojo.Tbstatus;
import com.accp.jboa.vo.EmployeeVo;

public interface TbstatusMapper {
  
	/**
	 * 查询所有
	 * @return
	 */
	List<Tbstatus> queryAll();
	/**
	 * 新增报销表
	 * @param currTime
	 * @return
	 */
	
	public int saveStatistics(@Param("currTime")String currTime);
	
	/**
	 * @Title: queryStatisticsByYear
	 * @Description: 查看年统计报表
	 * @param emp
	 * @param startYear
	 * @param endYear
	 * @return
	 * List<Statistics>
	 */
	public List<Tbstatus> queryStatisticsByYear(@Param("emp") EmployeeVo emp, @Param("startYear") Integer startYear,
			@Param("endYear") Integer endYear);
	
	/**
	 * @Title: queryStatisticsByMonth
	 * @Description: 查看月统计报表
	 * @param year
	 * @param startMonth
	 * @param endMonth
	 * @return
	 * List<Statistics>
	 */
	public List<Tbcount> queryStatisticsByMonth(@Param("emp") EmployeeVo emp, @Param("year") Integer year,
			@Param("startMonth") Integer startMonth, @Param("endMonth") Integer endMonth);
}