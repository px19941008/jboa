package com.accp.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.accp.jboa.biz.Tb_leaveBiz;
import com.accp.jboa.pojo.tb_leave;

import com.github.pagehelper.PageInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:spring-ctx.xml"})
public class TestBiz {
	@Autowired
	private Tb_leaveBiz biz;
	
	@Test
	public void testselect() {
		PageInfo<tb_leave> info=biz.queryAll(null, "2020-01-05 00:00:00", "2020-01-11 00:00:00", 1);
		System.out.println(info);
	}
	
}
