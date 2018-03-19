package com.example.demo;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yifengxin.DemoApplication;
import com.yifengxin.dao.XinMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class XinMapperTest {
	
	@Autowired
	XinMapper xin;
	
	@Test
	public void test() {
		List list=xin.selectAll();
		System.out.println(list.toString());
		System.out.println("开始前："+list.size());
		
		HashMap paramMap=new HashMap();
		paramMap.put("zkey","7497123b2bbc92dc986e11a75c6a570e");
		paramMap.put("xnr","xxxx");
		/*	xin.insertOne(paramMap);*/
		
		List zlist=xin.selectByKey(paramMap);
		System.out.println(JSONObject.toJSON(zlist).toString());
		System.out.println("插入后："+zlist.size());
	}
}
