package com.yifengxin.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.alibaba.fastjson.JSONArray;
/**
 * @author huangpeiyuan
 * 2018年3月3日
 */


public interface XinMapper {

	/**
	 * 查看所有信息
	 */
	public List<HashMap> selectAll();
	
    /**
     * 插入对象
     * @return
     */
    public int insertOne(HashMap<String,Object> paramMap);
    
    
    /**
     * 查看指定信息
     * @return
     */
    public List<HashMap<String,String>> selectByKey(HashMap<String,Object> paramMap);
    
}
