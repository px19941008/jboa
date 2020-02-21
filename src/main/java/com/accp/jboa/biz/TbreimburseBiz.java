package com.accp.jboa.biz;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.accp.jboa.dao.EmployeeMapper;
import com.accp.jboa.dao.ReimbursedetailMapper;
import com.accp.jboa.dao.TbcountMapper;
import com.accp.jboa.dao.TbreimburseMapper;
import com.accp.jboa.pojo.Employee;
import com.accp.jboa.pojo.Reimbursedetail;
import com.accp.jboa.pojo.Tbcount;
import com.accp.jboa.pojo.Tbreimburse;
import com.accp.jboa.vo.TbreimburseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("TbreimburseBiz")
public class TbreimburseBiz {

	@Resource
	private TbreimburseMapper dao;

	@Resource
	private EmployeeMapper emdao;
	
	@Resource
	private ReimbursedetailMapper redao;
	
	@Resource
	private  TbcountMapper tdao;
	

	/**
	 * 新增
	 * 
	 * @param tbreimburse
	 * @return
	 */
	public int addTbreimburse(Tbreimburse tbr) {
		// 查询设置下一个处理人业务处理逻辑
		if(tbr.getStatusid()==2) {
			//提交启动
			return dao.addTbreimburse(queryNextdealman(tbr));
		}	
		//新建状态
		tbr.setNextdealman(10000);
		return dao.addTbreimburse(tbr);
	}
	
	/**
	 * 查询单个
	 * @param tbId
	 * @return
	 */
	public   TbreimburseVo queryByTbId(String tbId) {
		return dao.queryByTbId(tbId);
	}
	
	/**
	 * 刪除 查询原图片并删除
	 * @param tbId
	 * @return
	 */
	public  int removeById(String tbId) {
		//删除原图片
		redao.queryByMid(tbId).forEach(temp->{
			new File("D:\\projectData\\images\\"+temp.getPicturepath()).delete();
		});
		return dao.removeById(tbId);
	}
	
	
	/**
	 * 修改状态
	 * @param tbr
	 * @return
	 */
	public  int modifyStatus(Tbreimburse tbr) {
		//修改下一个处理人
		Integer[] numbers= new Integer[] {1,7,8};
		if(Arrays.asList(numbers).contains(tbr.getStatusid())) {
			tbr.setNextdealman(10000);
		}else if(tbr.getStatusid()==6){
			//打回状态
			tbr.setNextdealman(dao.queryByID(tbr.getReimburseid()).getCreateman());
		}else {
			queryNextdealman(tbr);
		}
		return dao.updateByPrimaryKeySelective(tbr);
	}

	//个人修改 提交查询下一个处理人
	public  int modifyAll(Tbreimburse tbr) {
		if(tbr.getStatusid()!=null) {
			return dao.updateByPrimaryKeySelective(queryNextdealman(tbr));
		}
		return dao.updateByPrimaryKeySelective(tbr);
	}
	
	//审核
	public  int  modifyChildren(Tbreimburse tbr) {
		if(tbr.getStatusid()==2||tbr.getStatusid()==3) {
			//System.out.println(tbr.getCreateman());
			tbr.setNextdealman(dao.queryByID(tbr.getReimburseid()).getCreateman());
			modifyTbStatus(tbr);
			return dao.updateByPrimaryKeySelective(tbr);
		}else {
			Integer status=tbr.getStatusid();
			tbr=dao.queryByID(tbr.getReimburseid());
			tbr.setStatusid(status);
			modifyTbStatus(tbr);
			return dao.updateByPrimaryKeySelective(queryNextdealman(tbr));
		}
	}
	
	/**
	 * 条件分页查询//业务逻辑处理
	 * @param StartTime
	 * @param endTime
	 * @param currentPage
	 * @param statusId
	 * @return
	 */
	public   PageInfo<TbreimburseVo> queryPageByTerm(Employee user, String StartTime,String endTime,Integer currentPage,Integer statusId,Integer showId){
		PageHelper.startPage(currentPage, 5);
		Integer departmentid= null;
		Integer userid= null;
		Integer positionId=null;
		if(user.getPositionid()==0||user.getPositionid()==3||user.getPositionid()==5) {
			positionId=user.getPositionid();
		}else if(user.getPositionid()==1){
			departmentid=user.getDepartmentid();
			positionId=user.getPositionid();
		}
			userid=user.getEmployeeid();
		
		return new PageInfo<>(dao.queryPageByTerm(departmentid,userid,StartTime, endTime, statusId, positionId,showId));	
	}

	/**
	 * 查询并设置下一个处理人 报销流程业务逻辑处理
	 * @param bool
	 * @return
	 */
	private Tbreimburse queryNextdealman(Tbreimburse tbr) {
		// 查询报销人信息
		Employee emp = emdao.selectByPrimaryKey(tbr.getCreateman());
		// 根据职位确定的业务流程
		if(tbr.getNextdealman()!=null&&tbr.getNextdealman()==1002) {
			tbr.setNextdealman(10000);
			return tbr;
		}
		if (emp.getPositionid() == 2) {		
				//是否为修改
			if (tbr.getNextdealman()!=null){
				//金额小于5000
				if(tbr.getTotalcount()<5000) {
					/*直接财务出纳*/
					emp=emdao.selectByPrimaryKey(tbr.getNextdealman());
					//System.out.println(emp.getPositionid()+2);
					System.out.println(emp.getPositionid()+2);
					tbr.setNextdealman(emdao.queryNextdealman(null,emp.getPositionid()+2).getEmployeeid());
				}else {
					/*是否经过部门经理*/
					if(tbr.getNextdealman().equals(emdao.queryNextdealman(emp.getDepartmentid(), 1).getEmployeeid())) {
						tbr.setNextdealman(emdao.queryNextdealman(null, 0).getEmployeeid());
					}else if(tbr.getNextdealman().equals(emdao.queryNextdealman(null, 0).getEmployeeid())){
						tbr.setNextdealman(emdao.queryNextdealman(null,3).getEmployeeid());
					}else {
						tbr.setNextdealman(emdao.queryNextdealman(null,5).getEmployeeid());
					}
				}
			}else {
				tbr.setNextdealman(emdao.queryNextdealman( emp.getDepartmentid(),emp.getPositionid() - 1).getEmployeeid());
			}
		} else {
			if(tbr.getNextdealman()!=null) {
				/**
				 * 是否经过财务
				 */
				//System.out.println(tbr.getNextdealman()==emdao.queryNextdealman(null, 0).getEmployeeid());
				if(tbr.getNextdealman().equals(emdao.queryNextdealman(null, 0).getEmployeeid())){
					tbr.setNextdealman(emdao.queryNextdealman(null,3).getEmployeeid());
				}else {
					tbr.setNextdealman(emdao.queryNextdealman(null,5).getEmployeeid());
				}
			}else {
				tbr.setNextdealman(emdao.queryNextdealman(null,0).getEmployeeid());
			}
		}
		//System.out.println(tbr.getNextdealman());
		return tbr;
	}
	
	/**
	 * 主表状态的处理
	 * @param tbr
	 * @return
	 */
	private Tbreimburse modifyTbStatus(Tbreimburse tbr) {
		/*通过*/
		if(tbr.getStatusid()==1) {
			//根据职位判定处理状态
			//普通员工
			Employee emp = emdao.selectByPrimaryKey(tbr.getCreateman());
			if(emp.getPositionid()==2) {
				if(tbr.getNextdealman()==1001) {
					tbr.setStatusid(5);
				}else if(tbr.getNextdealman()==1002) {
					tbr.setStatusid(7);
				}else if(tbr.getNextdealman()==1000){
					tbr.setStatusid(4);
				}else {
					tbr.setStatusid(3);
				}
			}else {
				if(tbr.getNextdealman()==1001) {
					tbr.setStatusid(5);
				}else if(tbr.getNextdealman()==1002) {
					tbr.setStatusid(7);
				}else{
					tbr.setStatusid(4);
				}
			}
		}else if(tbr.getStatusid()==2) {
			tbr.setStatusid(8);
		}else if(tbr.getStatusid()==3) {
			tbr.setStatusid(6);
		}
		return tbr;
	}
	
	public  int insertTbcount(Tbcount record) {
		return tdao.insertTbcount(record);
	}
}
