package com.yifengxin.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.yifengxin.dao.XinMapper;
import com.yifengxin.entity.ResultVO;

import Utils.MM3DES;
import Utils.MMKeyUtil;
import Utils.WebUtil;

/**
 * @author huangpeiyuan
 * 2018年3月3日
 */
@RestController
@RequestMapping("/xin")
public class HomeController {
	
   @Autowired
   XinMapper xinMapper;
	
   /**
    * 保存信内容,返回钥匙
    * @param request
    * @return
    */
   @RequestMapping("/save")
   public ResultVO insert(HttpServletRequest request){
	  ResultVO resultVO=null;
	  
   	  HashMap<String, Object> paramMap;
		try {
			paramMap = WebUtil.getParam(request);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			 return WebUtil.getError("内容编码出错！");
		}
   	  
  	  //把信的内容加密
   	  String content=paramMap.containsKey("content")?paramMap.get("content").toString():"";
   	  if(StringUtils.isEmpty(content)) {
   		  return WebUtil.getError("内容不能为空！");
   	  }else if(content.length()>2000){
   		  return WebUtil.getError("内容太长了！");
   	  }
   		  
   	  String ip=WebUtil.getIpAddr(request);
   	  String time=new Date().getTime()+"";
   	  String random=WebUtil.getR9(); 
   	  String uuid=WebUtil.getUUID();
   	  String key=new StringBuffer().append(ip).append(random).append(uuid).append(time).toString();
   	  String md5Key=MMKeyUtil.getMD5(key);
   	  String enContent="";
   	  try {
   		   enContent=MM3DES.encode(content);
   	  }catch (Exception e) {
   		   return WebUtil.getError("内容加密失败！");
	  }
   	  paramMap.put("xnr", enContent);
	  paramMap.put("zkey",md5Key);

   	  //执行保存
   	  try {
   		  xinMapper.insertOne(paramMap);
   		  JSONObject o=new JSONObject();
   		  o.put("key", md5Key);
   		  resultVO=WebUtil.getSuccess(o);
   	  }catch(Exception e) {
   		  e.printStackTrace();
   		  md5Key="";
   		  resultVO=WebUtil.getError("服务器保存失败！");
   	  }
   	  return resultVO;
   }
   
   
    /**
     * 根据key数据
     * @param request
     * @return
     */
    @RequestMapping("/get")
    public ResultVO hello(HttpServletRequest request){
    	HashMap<String, Object> paramMap=new HashMap();
    	HashMap<String, String> resultMap=null;
    	
    	//取出信的内容解密
    	String key=request.getParameter("key");
    	if(StringUtils.isEmpty(key)){
    		return WebUtil.getError("传入key不能为空！");
    	}
    	paramMap.put("zkey", key);
    	
    	List<HashMap<String, String>> list=xinMapper.selectByKey(paramMap);
    	
    	String enContent="";
    	if(list.size()>0) {
    		enContent=list.get(0).get("xnr");
    	}else {
    		return WebUtil.getError("解密失败！");
    	}
    	
    	String deContent="";
    	try {
			deContent=MM3DES.decode(enContent);
		} catch (Exception e) {
			e.printStackTrace();
			return WebUtil.getError("解密失败！");
		}
    	
    	/**
    	 * 并执行销毁
    	 * 1.后期增加 可以供多少人看,人数到了,则自动销毁。
    	 * 2.增加到redis中
    	 */
    	JSONObject j= new JSONObject();
    	j.put("xnr", deContent);
    	ResultVO resultVO=WebUtil.getSuccess(j);
        return resultVO;
    }
    

}
