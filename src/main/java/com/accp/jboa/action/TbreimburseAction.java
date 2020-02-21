package com.accp.jboa.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.accp.jboa.biz.ReimbursedetailBiz;
import com.accp.jboa.biz.TbcheckBiz;
import com.accp.jboa.biz.TbreimburseBiz;
import com.accp.jboa.pojo.Employee;
import com.accp.jboa.pojo.Reimbursedetail;
import com.accp.jboa.pojo.Tbcheck;
import com.accp.jboa.pojo.Tbreimburse;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/c/Tbreimburse")
public class TbreimburseAction {
	
	@Resource
	private TbreimburseBiz tb;
	
	@Resource
	private ReimbursedetailBiz rb;
	
	@Resource
	private TbcheckBiz tbcheb;
	
	/**
	 * 新增报销单
	 * @return
	 */
	@PostMapping("addTb")
	public  String  addTb(HttpSession session,Tbreimburse tbr,Double[] subTotal,String[] desc,MultipartFile[] imgFile) {
		Employee user =(Employee)session.getAttribute("user");
		SimpleDateFormat df= new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String reimburseid=df.format(date);
		List<Reimbursedetail> list= new ArrayList<Reimbursedetail>();
		/**
		 * 后台处理文件空值
		 * 储存文件
		 */
		if(imgFile!=null) {
			for (int i = 0; i < imgFile.length; i++) {
				String fileName=imgFile[i].getOriginalFilename();
				if(!"".equals(fileName)) {
					System.out.println(fileName);
					String path=UUID.randomUUID().toString()+fileName.substring(fileName.lastIndexOf("."));
					try {
						imgFile[i].transferTo(new  File("e:\\image\\"+path));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					list.add(new Reimbursedetail(reimburseid, subTotal[i], desc[i], fileName, path));
				}
			}	
		}
		tbr.setReimburseid(reimburseid);
		tbr.setCreateman(user.getEmployeeid());
		tbr.setCreatetime(date);
		tbr.setList(list);
		tbr.setDepartmentid(user.getDepartmentid());
		tb.addTbreimburse(tbr);
		return "redirect:/ui/showReimburse.jsp";
	}
	
	//修改内容
	@PostMapping(value = "modifyAll",produces = "text/html;utf-8")
	@ResponseBody
	public  String modifyAll(HttpSession session,Tbreimburse tbr,Double[] subTotal,String[] desc,MultipartFile[] imgFile, Integer[] removeId) {
		Employee user =(Employee)session.getAttribute("user");
		List<Reimbursedetail> list= new ArrayList<Reimbursedetail>();
		/**
		 * 后台处理文件空值
		 * 储存文件
		 * 保存新数据
		 */
		if(imgFile!=null) {
			for (int i = 0; i < imgFile.length; i++) {
				String fileName=imgFile[i].getOriginalFilename();
				if(!"".equals(fileName)) {
					//System.out.println(fileName);
					String path=UUID.randomUUID().toString()+fileName.substring(fileName.lastIndexOf("."));
					try {
						imgFile[i].transferTo(new  File("e:\\image\\"+path));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					list.add(new Reimbursedetail(tbr.getReimburseid(), subTotal[i], desc[i], fileName, path));
				}
			}
		}
		//删除源文件
		if(removeId!=null) {
			//删除文件
			//System.out.println("1234564");
			rb.queryById(Arrays.asList(removeId)).forEach(temp->{
				new File("e:\\image\\"+temp.getPicturepath()).delete();
			});
			//移除数据库
			rb.removeReMoney(Arrays.asList(removeId));
		}
		System.out.println(tbr.getNextdealman());
		//rb.addReMoney(list);
		tbr.setCreateman(user.getEmployeeid());
		tb.modifyAll(tbr);
		return "<script>location.href='/jboa/ui/showReimburse.jsp'</script>";
	}
	
	
	/**
	 * 审核
	 * @param sessio
	 * @param tbche
	 * @return
	 */
	@PostMapping("modifyStatusByNext")
	@ResponseBody
	public   String modifyStatusByNext(HttpSession session,Tbcheck tbche,Tbreimburse tbr) {
		Employee user =(Employee)session.getAttribute("user");
		tbr.setStatusid(tbche.getCheckresult());
		//修改主表状态
		//System.out.println(tbr.getReimburseid());
		tb.modifyChildren(tbr);
		//增加子表数据
		tbche.setBizid(tbr.getReimburseid());
		tbche.setCheckman(user.getEmployeeid());
		//System.out.println(tbche.getBizid());
		tbche.setChecktime(new Date());
		tbche.setTypeid(2);
		tbcheb.addTb(tbche);
		return "ok";
	}
	
	/**
	 * 条件分页查询
	 * @param StartTime
	 * @param endTime
	 * @param currentPage
	 * @param statusId
	 * @return
	 */
	@PostMapping(value="queryPageByTerm",produces = "text/html; charset=utf-8")
	@ResponseBody
	public  String queryPageByTerm(HttpSession session,String StartTime,String endTime,Integer currentPage,Integer statusId,Integer showId) {
		//空值处理
		//System.out.println(StartTime+","+endTime+","+statusId);
		if("".equals(StartTime)) {
			StartTime=null;
		}
		if("".equals(endTime)) {
			endTime=null;
		}
		if(statusId==0) {
			statusId=null;
		}
		return JSON.toJSONString(tb.queryPageByTerm((Employee)session.getAttribute("user"), StartTime, endTime, currentPage, statusId,showId));
	}
	
	/**
	 * 查询单个
	 * @param model
	 * @param tbId
	 * @return
	 */
	@GetMapping("queryByTbId")
	public  String queryByTbId(Model model,String tbId,String pageName,Integer Status) {
		model.addAttribute("tbr", tb.queryByTbId(tbId));
		//确定返回页面地址
		model.addAttribute("Status", Status);
		return "/ui/"+pageName+".jsp";
	}
	
	@PostMapping("removeById")
	@ResponseBody
	public  String removeById(String tbId) {
		//System.out.println(tbId);
		if(tb.removeById(tbId)>0) {
			return "yes";
		}else {
			return "no";
		}
	}

	//个人提交
	@PostMapping("modifyStatus")
	@ResponseBody
	public  String modifyStatus(HttpSession session,Tbreimburse tbr) {
		Employee user =(Employee)session.getAttribute("user");
		tbr.setCreateman(user.getEmployeeid());
		tbr.setStatusid(2);
		if(tb.modifyStatus(tbr)>0) {
			return "yes";
		}else {
			return "no";
		}
	}
}
